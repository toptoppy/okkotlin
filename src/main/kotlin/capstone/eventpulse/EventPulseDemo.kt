package toptoppy.kotlin.training.capstone.eventpulse

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import toptoppy.kotlin.training.capstone.eventpulse.dsl.eventPipeline
import toptoppy.kotlin.training.capstone.eventpulse.model.AlertSeverity
import toptoppy.kotlin.training.capstone.eventpulse.model.Event
import toptoppy.kotlin.training.capstone.eventpulse.model.EventId
import toptoppy.kotlin.training.capstone.eventpulse.model.EventPayload
import toptoppy.kotlin.training.capstone.eventpulse.model.ProducerId

fun main() =
    runBlocking {
        println("==================================================")
        println("⚡ EVENTPULSE: Reactive Asynchronous Pipeline Demo")
        println("==================================================")

        val deadLetterVault = mutableListOf<Event>()

        // 1. Build pipeline via Type-Safe DSL
        val pipeline =
            eventPipeline {
                name = "HighThroughputTelemetryPipeline"
                bufferCapacity = 20
                rateLimitPerSecond = 20

                // Filter: discard low-priority telemetry readings below threshold
                filter { event ->
                    when (val payload = event.payload) {
                        is EventPayload.Telemetry -> payload.metricValue > 10.0
                        is EventPayload.SystemAlert -> payload.severity != AlertSeverity.INFO
                        is EventPayload.UserAction -> true
                    }
                }

                onDeadLetter { deadLetter ->
                    println("[DEAD-LETTER] Routing failed event ${deadLetter.eventId.value} to archive")
                    deadLetterVault.add(deadLetter.rawEvent)
                }
            }

        // 2. Start worker consumer
        val workerJob =
            pipeline.startWorker(this) { result ->
                println("  -> [Pipeline Output] ${result::class.simpleName} on ${result.eventId.value}")
            }

        // 3. Monitor metrics in background
        val metricsJob =
            launch {
                pipeline.metrics.collect { metrics ->
                    if (metrics.totalReceived > 0) {
                        val rateStr = "%.1f%%".format(metrics.successRate)
                        println(
                            "     [METRICS] Total: ${metrics.totalReceived} | Success: ${metrics.successCount} | " +
                                "Filtered: ${metrics.filteredCount} | SuccessRate: $rateStr",
                        )
                    }
                }
            }

        // 4. Simulate concurrent event submission
        println("\nSubmitting simulated events...")
        val producer = ProducerId("prod_sensor_alpha")

        val sampleEvents =
            listOf(
                Event(EventId.random(), producer, EventPayload.Telemetry("cpu_temp", 45.2)),
                // Should be filtered out (value <= 10.0)
                Event(EventId.random(), producer, EventPayload.Telemetry("idle_temp", 5.0)),
                Event(EventId.random(), producer, EventPayload.UserAction("usr_99", "checkout")),
                Event(EventId.random(), producer, EventPayload.SystemAlert(AlertSeverity.CRITICAL, "Disk 95% full")),
                // Should be filtered out (severity INFO)
                Event(
                    EventId.random(),
                    producer,
                    EventPayload.SystemAlert(AlertSeverity.INFO, "Routine ping"),
                ),
            )

        sampleEvents.forEach { event ->
            pipeline.submit(event)
        }

        delay(300L)
        pipeline.close()
        workerJob.join()

        delay(100L)
        metricsJob.cancel()

        println("\nPipeline completed successfully!")
    }

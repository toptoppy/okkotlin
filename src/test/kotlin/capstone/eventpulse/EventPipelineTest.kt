package toptoppy.kotlin.training.capstone.eventpulse

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import toptoppy.kotlin.training.capstone.eventpulse.dsl.eventPipeline
import toptoppy.kotlin.training.capstone.eventpulse.model.AlertSeverity
import toptoppy.kotlin.training.capstone.eventpulse.model.Event
import toptoppy.kotlin.training.capstone.eventpulse.model.EventId
import toptoppy.kotlin.training.capstone.eventpulse.model.EventPayload
import toptoppy.kotlin.training.capstone.eventpulse.model.ProcessingResult
import toptoppy.kotlin.training.capstone.eventpulse.model.ProducerId
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EventPipelineTest {

    @Test
    fun `test pipeline filtering and success dispatch`() = runTest {
        val pipeline = eventPipeline {
            name = "TestPipeline"
            bufferCapacity = 10
            rateLimitPerSecond = 1000

            filter { event ->
                when (val p = event.payload) {
                    is EventPayload.Telemetry -> p.metricValue >= 10.0
                    else -> true
                }
            }
        }

        val producer = ProducerId("test_prod")
        val e1 = Event(EventId("e1"), producer, EventPayload.Telemetry("cpu", 15.0))
        val e2 = Event(EventId("e2"), producer, EventPayload.Telemetry("cpu", 5.0)) // should filter

        val results = mutableListOf<ProcessingResult>()
        val job = launch {
            pipeline.processStream().take(2).toList(results)
        }

        pipeline.submit(e1)
        pipeline.submit(e2)
        job.join()

        results.size shouldBe 2
        (results[0] is ProcessingResult.Success) shouldBe true
        (results[1] is ProcessingResult.Filtered) shouldBe true

        val metrics = pipeline.metrics.value
        metrics.totalReceived shouldBe 2
        metrics.successCount shouldBe 1
        metrics.filteredCount shouldBe 1
        metrics.successRate shouldBe 50.0
    }

    @Test
    fun `test dead letter handling when unhandled exception occurs`() = runTest {
        var deadLetterCaptured: ProcessingResult.DeadLetter? = null

        val pipeline = eventPipeline {
            name = "FaultyPipeline"
            rateLimitPerSecond = 1000
            onDeadLetter { dl -> deadLetterCaptured = dl }
        }

        val producer = ProducerId("test_prod")
        val validEvent = Event(EventId("e_valid"), producer, EventPayload.SystemAlert(AlertSeverity.WARN, "Warning"))

        val results = mutableListOf<ProcessingResult>()
        val job = launch {
            pipeline.processStream().take(1).toList(results)
        }

        pipeline.submit(validEvent)
        job.join()

        results.size shouldBe 1
        (results[0] is ProcessingResult.Success) shouldBe true
    }
}

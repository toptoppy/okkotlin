package toptoppy.kotlin.training.capstone.eventpulse.model

import java.util.UUID

@JvmInline
value class EventId(val value: String) {
    init {
        require(value.isNotBlank()) { "EventId must not be blank" }
    }
    companion object {
        fun random(): EventId = EventId("evt_${UUID.randomUUID().toString().take(8)}")
    }
}

@JvmInline
value class ProducerId(val value: String) {
    init {
        require(value.isNotBlank()) { "ProducerId must not be blank" }
    }
}

enum class AlertSeverity { INFO, WARN, ERROR, CRITICAL }

sealed interface EventPayload {
    data class UserAction(val userId: String, val action: String) : EventPayload
    data class SystemAlert(val severity: AlertSeverity, val message: String) : EventPayload
    data class Telemetry(val metricName: String, val metricValue: Double) : EventPayload
}

data class Event(
    val id: EventId = EventId.random(),
    val producerId: ProducerId,
    val payload: EventPayload,
    val timestamp: Long = System.currentTimeMillis()
)

sealed interface ProcessingResult {
    val eventId: EventId

    data class Success(
        override val eventId: EventId,
        val details: String,
        val processedAt: Long = System.currentTimeMillis()
    ) : ProcessingResult

    data class Filtered(
        override val eventId: EventId,
        val reason: String
    ) : ProcessingResult

    data class DeadLetter(
        override val eventId: EventId,
        val error: String,
        val rawEvent: Event
    ) : ProcessingResult
}

data class PipelineMetrics(
    val totalReceived: Long = 0,
    val successCount: Long = 0,
    val filteredCount: Long = 0,
    val deadLetterCount: Long = 0
) {
    val successRate: Double
        get() = if (totalReceived == 0L) 0.0 else (successCount.toDouble() / totalReceived) * 100.0
}

package toptoppy.kotlin.training.capstone.eventpulse.engine

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import toptoppy.kotlin.training.capstone.eventpulse.model.PipelineMetrics
import toptoppy.kotlin.training.capstone.eventpulse.model.ProcessingResult

/**
 * Thread-safe real-time metrics collector backed by StateFlow.
 */
class MetricsCollector {
    private val _metrics = MutableStateFlow(PipelineMetrics())
    val metrics: StateFlow<PipelineMetrics> = _metrics.asStateFlow()

    fun record(result: ProcessingResult) {
        _metrics.update { current ->
            when (result) {
                is ProcessingResult.Success -> current.copy(
                    totalReceived = current.totalReceived + 1,
                    successCount = current.successCount + 1
                )
                is ProcessingResult.Filtered -> current.copy(
                    totalReceived = current.totalReceived + 1,
                    filteredCount = current.filteredCount + 1
                )
                is ProcessingResult.DeadLetter -> current.copy(
                    totalReceived = current.totalReceived + 1,
                    deadLetterCount = current.deadLetterCount + 1
                )
            }
        }
    }

    fun snapshot(): PipelineMetrics = _metrics.value
}

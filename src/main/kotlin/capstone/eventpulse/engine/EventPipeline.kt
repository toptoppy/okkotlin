package toptoppy.kotlin.training.capstone.eventpulse.engine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import toptoppy.kotlin.training.capstone.eventpulse.model.Event
import toptoppy.kotlin.training.capstone.eventpulse.model.ProcessingResult

/**
 * EventPipeline: Asynchronous reactive pipeline processing events with backpressure and rate limiting.
 */
class EventPipeline(
    val name: String,
    val bufferCapacity: Int,
    val rateLimitPerSecond: Int,
    private val filters: List<(Event) -> Boolean>,
    private val deadLetterHandler: ((ProcessingResult.DeadLetter) -> Unit)?
) {
    private val rateLimiter = RateLimiter(maxTokens = rateLimitPerSecond)
    private val metricsCollector = MetricsCollector()
    private val incomingChannel = Channel<Event>(capacity = bufferCapacity)

    val metrics = metricsCollector.metrics

    suspend fun submit(event: Event) {
        incomingChannel.send(event)
    }

    fun close() {
        incomingChannel.close()
    }

    /**
     * Consumes from the incoming channel and returns a Flow of processing results.
     */
    fun processStream(): Flow<ProcessingResult> = flow {
        for (event in incomingChannel) {
            rateLimiter.acquire()
            val result = processEvent(event)
            metricsCollector.record(result)
            emit(result)
        }
    }.flowOn(Dispatchers.Default)

    private fun processEvent(event: Event): ProcessingResult {
        val passesFilters = filters.all { filter ->
            runCatching { filter(event) }.getOrDefault(false)
        }

        if (!passesFilters) {
            return ProcessingResult.Filtered(event.id, "Rejected by pipeline filters")
        }

        return try {
            val details = "Dispatched payload: ${event.payload::class.simpleName}"
            ProcessingResult.Success(event.id, details)
        } catch (e: Exception) {
            val deadLetter = ProcessingResult.DeadLetter(event.id, e.message ?: "Unknown error", event)
            deadLetterHandler?.invoke(deadLetter)
            deadLetter
        }
    }

    fun startWorker(scope: CoroutineScope, onResult: (ProcessingResult) -> Unit) = scope.launch {
        processStream().collect { result ->
            onResult(result)
        }
    }
}

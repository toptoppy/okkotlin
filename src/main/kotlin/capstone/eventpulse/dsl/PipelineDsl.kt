package toptoppy.kotlin.training.capstone.eventpulse.dsl

import toptoppy.kotlin.training.capstone.eventpulse.engine.EventPipeline
import toptoppy.kotlin.training.capstone.eventpulse.model.Event
import toptoppy.kotlin.training.capstone.eventpulse.model.ProcessingResult

@DslMarker
annotation class EventPipelineDsl

@EventPipelineDsl
class EventPipelineBuilder {
    var name: String = "DefaultPipeline"
    var bufferCapacity: Int = 100
    var rateLimitPerSecond: Int = 50

    private val filters = mutableListOf<(Event) -> Boolean>()
    private var deadLetterHandler: ((ProcessingResult.DeadLetter) -> Unit)? = null

    fun filter(predicate: (Event) -> Boolean) {
        filters.add(predicate)
    }

    fun onDeadLetter(handler: (ProcessingResult.DeadLetter) -> Unit) {
        deadLetterHandler = handler
    }

    fun build(): EventPipeline {
        return EventPipeline(
            name = name,
            bufferCapacity = bufferCapacity,
            rateLimitPerSecond = rateLimitPerSecond,
            filters = filters.toList(),
            deadLetterHandler = deadLetterHandler,
        )
    }
}

fun eventPipeline(block: EventPipelineBuilder.() -> Unit): EventPipeline {
    return EventPipelineBuilder().apply(block).build()
}

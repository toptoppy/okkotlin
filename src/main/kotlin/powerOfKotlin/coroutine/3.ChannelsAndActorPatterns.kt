package toptoppy.kotlin.training.powerOfKotlin.coroutine

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Modern Kotlin 2.x Coroutines: Channels & Producer-Consumer Pipelines
 *
 * Demonstrates:
 * 1. Buffered `Channel<T>` for cross-coroutine message passing.
 * 2. Backpressure management between producers and consumers.
 * 3. Graceful channel closing and safe consumption.
 */

data class WorkTask(val id: Int, val payload: String)

fun main() =
    runBlocking {
        // Channel with capacity buffer of 3 items
        val taskChannel = Channel<WorkTask>(capacity = 3)

        // Producer Coroutine
        val producerJob =
            launch {
                for (i in 1..5) {
                    val task = WorkTask(id = i, payload = "Payload-$i")
                    println("[Producer] Sending $task...")
                    taskChannel.send(task) // Suspends if the buffer is full (backpressure)
                    println("[Producer] Sent $task successfully")
                    delay(100L)
                }
                taskChannel.close() // Signal that no more items will be sent
                println("[Producer] Channel closed")
            }

        // Consumer Coroutine
        val consumerJob =
            launch {
                delay(250L) // Consumer starts slightly slower to illustrate buffer filling
                taskChannel.consumeEach { task ->
                    println("   -> [Consumer] Received & processing $task")
                    delay(150L) // Simulating work
                }
                println("   -> [Consumer] All tasks consumed")
            }

        producerJob.join()
        consumerJob.join()
    }

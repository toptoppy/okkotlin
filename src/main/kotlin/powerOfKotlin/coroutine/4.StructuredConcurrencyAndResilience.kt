package toptoppy.kotlin.training.powerOfKotlin.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.coroutineContext

/*
 * Modern Kotlin 2.x Coroutines: Structured Concurrency & Resilience
 *
 * Demonstrates:
 * 1. `supervisorScope` for failure isolation (preventing one failing child from canceling siblings).
 * 2. `CoroutineExceptionHandler` for uncaught background coroutine errors.
 * 3. Cooperative cancellation using `ensureActive()`.
 * 4. Resilient exponential backoff retry pattern.
 */

// Resilient retry utility with exponential backoff
suspend fun <T> retryWithBackoff(
    times: Int = 3,
    initialDelayMs: Long = 100L,
    maxDelayMs: Long = 1000L,
    factor: Double = 2.0,
    block: suspend (attempt: Int) -> T,
): T {
    var currentDelay = initialDelayMs
    repeat(times - 1) { attempt ->
        try {
            return block(attempt + 1)
        } catch (e: Exception) {
            println("[Retry] Attempt ${attempt + 1} failed: ${e.message}. Retrying in ${currentDelay}ms...")
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMs)
        }
    }
    return block(times) // Last attempt throws if it fails
}

fun main() =
    runBlocking {
        println("=== 1. Failure Isolation with supervisorScope ===")

        val handler =
            CoroutineExceptionHandler { _, exception ->
                println("[SupervisorHandler] Caught uncaught exception: ${exception.message}")
            }

        supervisorScope {
            // Child 1 fails
            launch(handler) {
                println("[Task 1] Starting...")
                delay(100L)
                throw IllegalStateException("Database connection dropped in Task 1!")
            }

            // Child 2 survives because supervisorScope isolates failures
            launch {
                println("[Task 2] Starting...")
                delay(250L)
                println("[Task 2] Completed successfully despite Task 1 failure!")
            }
        }

        println("\n=== 2. Cooperative Cancellation ===")
        val cancellableJob =
            launch {
                var count = 0
                while (count < 10) {
                    // Checks whether the coroutine is still active; throws CancellationException if cancelled
                    coroutineContext.ensureActive()
                    println("Working step $count...")
                    delay(50L)
                    count++
                }
            }
        delay(120L)
        println("Cancelling job...")
        cancellableJob.cancel()
        cancellableJob.join()
        println("Job cancelled cooperatively.")

        println("\n=== 3. Exponential Backoff Retry Demo ===")
        var failCount = 0
        val result =
            retryWithBackoff(times = 3) { attempt ->
                if (failCount < 2) {
                    failCount++
                    throw RuntimeException("Network timeout")
                }
                "Remote Data Acquired on attempt $attempt"
            }
        println("Final result: $result")
    }

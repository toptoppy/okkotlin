package toptoppy.kotlin.training.capstone.eventpulse.engine

import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Coroutine-friendly Token Bucket Rate Limiter
 *
 * Ensures a maximum rate of operations per second without blocking threads.
 */
class RateLimiter(
    private val maxTokens: Int,
    private val refillPeriodMs: Long = 1000L
) {
    private var availableTokens: Double = maxTokens.toDouble()
    private var lastRefillTime: Long = System.currentTimeMillis()
    private val mutex = Mutex()

    suspend fun acquire() {
        while (true) {
            val waitTime = mutex.withLock {
                refillTokens()
                if (availableTokens >= 1.0) {
                    availableTokens -= 1.0
                    return
                }
                val missingTokens = 1.0 - availableTokens
                (missingTokens * (refillPeriodMs.toDouble() / maxTokens)).toLong().coerceAtLeast(10L)
            }
            delay(waitTime)
        }
    }

    private fun refillTokens() {
        val now = System.currentTimeMillis()
        val elapsed = now - lastRefillTime
        if (elapsed > 0) {
            val tokensToAdd = (elapsed.toDouble() / refillPeriodMs) * maxTokens
            availableTokens = (availableTokens + tokensToAdd).coerceAtMost(maxTokens.toDouble())
            lastRefillTime = now
        }
    }
}

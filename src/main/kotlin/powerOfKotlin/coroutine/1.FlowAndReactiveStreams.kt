package toptoppy.kotlin.training.powerOfKotlin.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Modern Kotlin 2.x Coroutines: Cold Flow & Asynchronous Reactive Streams
 *
 * Demonstrates:
 * 1. Declarative cold streams using `flow { ... }`.
 * 2. Intermediate operators (`filter`, `map`, `catch`).
 * 3. Flow context preservation with `flowOn` for thread-safe offloading.
 * 4. Terminal collection with exception handling.
 */

data class SensorReading(val sensorId: String, val celsius: Double)

fun emitSensorStream(): Flow<SensorReading> =
    flow {
        val readings =
            listOf(
                SensorReading("sensor-a", 22.4),
                SensorReading("sensor-b", 45.1),
                SensorReading("sensor-c", 19.8),
                SensorReading("sensor-d", 52.0),
                SensorReading("sensor-e", 28.3),
            )

        for (reading in readings) {
            delay(150L) // Simulates asynchronous hardware polling
            emit(reading)
        }
    }.flowOn(Dispatchers.Default) // Offloads upstream emission to background thread pool

fun main() =
    runBlocking {
        println("--- Starting Cold Flow Demo ---")

        emitSensorStream()
            .filter { it.celsius > 25.0 } // Keep only high temperature alerts
            .map { reading ->
                val fahrenheit = (reading.celsius * 9 / 5) + 32
                "ALERT: ${reading.sensorId} recorded high temperature: %.1f°C (%.1f°F)".format(
                    reading.celsius,
                    fahrenheit,
                )
            }
            .catch { e -> println("Caught exception in stream: ${e.message}") }
            .collect { alertMessage ->
                println(alertMessage)
            }

        println("--- Flow collection finished ---")
    }

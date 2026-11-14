package toptoppy.kotlin.training.powerOfKotlin.coroutine

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineAndFlowTest {
    @Test
    fun `test cold flow transformations and emissions`() =
        runTest {
            val stream = emitSensorStream()

            val results =
                stream
                    .filter { it.celsius > 25.0 }
                    .map { it.sensorId }
                    .toList()

            // Based on emitSensorStream readings (45.1, 52.0, 28.3)
            results shouldBe listOf("sensor-b", "sensor-d", "sensor-e")
        }

    @Test
    fun `test StateFlow and SharedFlow updates`() =
        runTest {
            val viewModel = DashboardViewModel()

            viewModel.state.value.count shouldBe 0
            viewModel.increment()
            viewModel.state.value.count shouldBe 1

            viewModel.increment()
            viewModel.increment()
            viewModel.state.value.count shouldBe 3
        }

    @Test
    fun `test exponential backoff retry on transient errors`() =
        runTest {
            var attempts = 0

            val output =
                retryWithBackoff(times = 4, initialDelayMs = 10L) { attempt ->
                    attempts = attempt
                    if (attempt < 3) {
                        throw RuntimeException("Temporary glitch")
                    }
                    "SuccessOn$attempt"
                }

            output shouldBe "SuccessOn3"
            attempts shouldBe 3
        }
}

package toptoppy.kotlin.training.powerOfKotlin.coroutine

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Modern Kotlin 2.x Coroutines: Hot Streams with StateFlow & SharedFlow
 *
 * Demonstrates:
 * 1. `StateFlow`: State holder observable that always maintains a current state (conflated).
 * 2. `SharedFlow`: Broadcast stream for one-off events (snackbars, navigation, alerts).
 * 3. Buffer overflow strategies and multiple concurrent collectors.
 */

data class ViewState(val isLoading: Boolean = false, val count: Int = 0, val error: String? = null)

sealed interface UIEvent {
    data class ShowToast(val message: String) : UIEvent

    data class NavigateTo(val route: String) : UIEvent
}

class DashboardViewModel {
    // StateFlow for persistent UI state
    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    // SharedFlow for ephemeral events
    private val _events =
        MutableSharedFlow<UIEvent>(
            replay = 0,
            extraBufferCapacity = 10,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )
    val events = _events.asSharedFlow()

    fun increment() {
        val newCount = _state.value.count + 1
        _state.value = _state.value.copy(count = newCount)

        if (newCount % 3 == 0) {
            _events.tryEmit(UIEvent.ShowToast("Milestone reached: $newCount!"))
        }
    }

    fun triggerNavigation() {
        _events.tryEmit(UIEvent.NavigateTo("/checkout"))
    }
}

fun main() =
    runBlocking {
        val viewModel = DashboardViewModel()

        // Collector 1: Monitors View State
        val stateJob =
            launch {
                viewModel.state.collect { state ->
                    println("[UI STATE] Count: ${state.count}, Loading: ${state.isLoading}")
                }
            }

        // Collector 2: Monitors Ephemeral Events
        val eventJob =
            launch {
                viewModel.events.collect { event ->
                    when (event) {
                        is UIEvent.ShowToast -> println("[TOAST EVENT] ${event.message}")
                        is UIEvent.NavigateTo -> println("[NAVIGATION EVENT] Moving to ${event.route}")
                    }
                }
            }

        delay(100L)
        viewModel.increment()
        viewModel.increment()
        viewModel.increment() // Triggers toast
        viewModel.triggerNavigation()

        delay(200L)
        stateJob.cancel()
        eventJob.cancel()
    }

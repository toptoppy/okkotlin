package toptoppy.kotlin.training.powerOfKotlin.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    demoRunBlocking()
    demoLaunch()
    demoAsyncAwait()
    demoConcurrencyAndCancellation()
    demoDispatchers()
}

fun demoRunBlocking() = runBlocking {
    println("Start")
    delay(1000L) // Suspends the coroutine for 1 second without blocking the thread
    println("Hello after 1 second")
}

fun demoLaunch() = runBlocking {
    launch {  // Starts a new coroutine inside the runBlocking scope.
        delay(500L)
        println("Task from launch")
    }
    println("Hello from runBlocking")
}

fun demoAsyncAwait() = runBlocking {
    val deferred = async {  // Starts a coroutine that computes a result.
        delay(1000L)
        "Result from async"
    }
    println("Waiting for result...")
    println("Got: ${deferred.await()}") // Suspends until the coroutine completes and returns its result.
}

fun demoConcurrencyAndCancellation() = runBlocking {
    val job = launch {
        repeat(10) { i ->
            println("Job: $i")
            delay(500L)
        }
    }
    delay(1300L) // Let the coroutine run for a while
    println("Cancelling job")
    job.cancel()  // Cancel the coroutine, which stops further execution.
    job.join()    // Wait for its cancellation to complete, Ensures the cancellation process is complete before moving on.
    println("Job cancelled")
}

/*
    Dispatchers in Kotlin coroutines determine the thread or thread pool on which your coroutine code runs. They are a key part of the coroutine context and act as schedulers for managing where and how your coroutines execute. Here’s a breakdown:

    Dispatchers.Default:

    Used for CPU-intensive or computational tasks.
    It leverages a shared pool of background threads optimized for parallel work.
    Dispatchers.IO:

    Designed for IO-bound tasks, such as reading from or writing to files, network operations, or database interactions.
    It uses a larger pool of threads because IO operations often wait (are blocking) and can benefit from having more threads available.
    Dispatchers.Main:

    Typically used in Android applications for updating the UI.
    It confines execution to the main (UI) thread, ensuring that UI operations remain responsive.
    Dispatchers.Unconfined:

    Starts execution in the current thread but only until the first suspension point.
    After that, it may resume in a different thread, so it’s not confined to any specific thread.
    It’s mainly useful for certain advanced scenarios or testing.
 */
fun demoDispatchers() = runBlocking {
    launch(Dispatchers.Default) {
        println("Running on Default dispatcher: ${Thread.currentThread().name}")
    }
    launch(Dispatchers.IO) {
        println("Running on IO dispatcher: ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) {
        println("Running on Unconfined dispatcher: ${Thread.currentThread().name}")
    }
}
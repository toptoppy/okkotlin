# Technical Interview & Portfolio Pitch Guide

Use this guide when discussing **OkKotlin** with recruiters, hiring managers, and tech leads during screens and architectural interviews.

---

## 🎙️ The 30-Second Elevator Pitch
> *"I built OkKotlin as a showcase of modern Kotlin 2.x and reactive JVM architecture. It demonstrates inline value classes, sealed interface state models, and structured concurrency with Coroutines and Flow. It includes an event pipeline capstone with rate limiting, and its automated tests run in GitHub Actions on JDK 21."*

---

## 🏛️ The 2-Minute Architectural Walkthrough
> *"When designing modern backend microservices, three critical challenges are type safety, asynchronous concurrency, and maintainability.
>
> In OkKotlin, I addressed these through three distinct layers:
>
> 1. **Domain Modeling:** Rather than suffering from primitive obsession or ambiguous string IDs, I used `@JvmInline value class` and sealed interface hierarchies. The Kotlin compiler inlines these at runtime to avoid heap allocations while enforcing strict compile-time type safety.
>
> 2. **Concurrency & Stream Processing:** Instead of complex reactive stream frameworks like RxJava or Project Reactor, I used Kotlin Coroutines and Cold `Flow`. This gives us synchronous-style imperative code with non-blocking asynchronous execution, cooperative cancellation, and backpressure management.
>
> 3. **The EventPulse Capstone:** To demonstrate these patterns in action, I engineered 'EventPulse'—an asynchronous event processing engine featuring a token-bucket rate limiter, StateFlow-backed real-time metrics, dead-letter routing, and a Type-Safe Builder DSL (`@DslMarker`).
>
> The repository includes 31 automated tests using `kotlin.test`, `kotlinx-coroutines-test`, and Kotest assertions. GitHub Actions runs them on JDK 21."*

---

## 💡 Key Architectural Q&A for Technical Interviews

### Q1: Why use Kotlin Coroutines over Java 21 Virtual Threads (Loom)?
**Answer:**
- Virtual Threads simplify thread blocking for traditional imperative code (e.g. standard JDBC or Servlet APIs).
- However, **Kotlin Coroutines** provide **Structured Concurrency**: lifetime management, cooperative cancellation propagation, and parent-child hierarchy scoping.
- In addition, Kotlin Coroutines provide declarative stream abstractions like **`Flow`**, **`StateFlow`**, and **`Channel`** with built-in backpressure, which Virtual Threads alone do not provide.

### Q2: How does `runTest` in `kotlinx-coroutines-test` enable deterministic testing?
**Answer:**
- Testing asynchronous code with `Thread.sleep()` or real delays leads to flaky, slow test suites.
- `runTest` uses a `StandardTestDispatcher` with a **virtual time scheduler**.
- Calls to `delay(1000L)` inside `runTest` automatically advance virtual time instantaneously without waiting 1 real second, making asynchronous tests deterministic and blazing fast (milliseconds instead of seconds).

### Q3: What is the architectural advantage of Sealed Interfaces over Enums or standard Interfaces?
**Answer:**
- Enums can only hold constant values; all instances share the same fields.
- Sealed Interfaces allow **Algebraic Data Types (ADTs)**: each subtype can have distinct properties, payloads, and state representations (e.g. `Success(receiptNumber: String)` vs `Failed(errorCode: String, rawEvent: Event)`).
- The Kotlin compiler enforces **exhaustiveness** in `when` expressions, meaning if a new state is added, all consumer code fails to compile until explicitly updated, preventing unhandled runtime branch bugs.

### Q4: How does the `@DslMarker` annotation work in the EventPulse pipeline?
**Answer:**
- When building nested builder DSLs (such as `pipeline { filter { ... } }`), an inner block could accidentally call methods of an outer builder, leading to scope leakage and subtle bugs.
- Annotating builders with `@DslMarker` instructs the Kotlin compiler to prevent calling outer receiver methods within inner nested lambdas unless explicitly qualified with `@label`.

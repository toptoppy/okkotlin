# LinkedIn Carousel Slide Blueprint (PDF Format)

> **Format Tip:** Carousels on LinkedIn get 3-5x higher engagement than text-only posts. You can export these slides into a PDF (via Canva, Keynote, or Figma) and upload it as a "Document" on LinkedIn.

**Theme:** Dark mode background (`#0D1117`), Kotlin Purple accent (`#7F52FF`), Emerald Green (`#2EA44F`) for highlights.

---

### Slide 1: Cover (The Hook)
- **Header Badge:** `KOTLIN 2.x ARCHITECTURE GUIDE`
- **Main Headline:** 5 Modern Kotlin Patterns That Elevate Your Code Quality
- **Sub-headline:** From zero-cost value classes to reactive asynchronous flows.
- **Author Badge:** By TopToppy • ODDS Team
- **Visual:** Minimal Kotlin logo + code terminal graphic.
- **Swipe Indicator:** `Swipe for code examples ➔`

---

### Slide 2: Pattern 1 — Zero-Cost Value Classes
- **Title:** 1. Eliminate Primitive Obsession with `@JvmInline`
- **Problem:** Passing `userId: String` and `orderId: String` in the wrong order compiles fine in Java, causing silent runtime bugs.
- **Solution:** Wrap them in `@JvmInline value class`. At runtime, the JVM treats it as a primitive string with zero heap allocation!
- **Code Box:**
  ```kotlin
  @JvmInline
  value class OrderId(val value: String) {
      init { require(value.startsWith("ord_")) }
  }

  @JvmInline
  value class CustomerId(val value: String)

  // Compile error if you swap parameters!
  fun ship(order: OrderId, customer: CustomerId)
  ```
- **Key Takeaway:** Type safety without performance penalty.

---

### Slide 3: Pattern 2 — Sealed Interfaces & Exhaustive Matching
- **Title:** 2. Domain Modeling with Sealed Hierarchies
- **Problem:** Boolean flags (`isSuccess`, `hasError`, `isPending`) cause contradictory states like `isSuccess = true` AND `hasError = true`.
- **Solution:** Model mutually exclusive states using a `sealed interface`.
- **Code Box:**
  ```kotlin
  sealed interface PaymentStatus {
      data class Pending(val txId: String) : PaymentStatus
      data class Completed(val receipt: String) : PaymentStatus
      data class Failed(val error: String) : PaymentStatus
  }

  fun handle(s: PaymentStatus) = when (s) {
      is PaymentStatus.Pending -> "Processing..."
      is PaymentStatus.Completed -> "Receipt: ${s.receipt}"
      is PaymentStatus.Failed -> "Alert: ${s.error}"
  } // Compiler enforces all states are handled!
  ```
- **Key Takeaway:** Impossible states become unrepresentable.

---

### Slide 4: Pattern 3 — Cold Flow vs. Hot StateFlow
- **Title:** 3. Reactive Asynchronous Streams
- **Concept:**
  - **Cold `Flow`:** Executes on demand whenever collected (ideal for database queries or API requests).
  - **Hot `StateFlow`:** Always holds current state and notifies multiple active observers.
- **Code Box:**
  ```kotlin
  // Cold stream: Only runs when collected
  fun sensorStream(): Flow<Reading> = flow {
      emit(pollSensor())
  }.flowOn(Dispatchers.Default)

  // Hot state holder for reactive UI or services
  val state = MutableStateFlow(InitialState)
  state.update { it.copy(counter = it.counter + 1) }
  ```
- **Key Takeaway:** Separation of computation pipelines from persistent state holders.

---

### Slide 5: Pattern 4 — Type-Safe DSL Builders
- **Title:** 4. Expressive APIs with `@DslMarker`
- **Problem:** Verbose builder patterns and chained setters clutter business logic.
- **Solution:** Create custom declarative DSLs using lambdas with receiver.
- **Code Box:**
  ```kotlin
  val pipeline = eventPipeline {
      name = "TelemetryPipeline"
      bufferCapacity = 100
      rateLimitPerSecond = 50

      filter { it.metricValue > 10.0 }
      onDeadLetter { dl -> archive(dl) }
  }
  ```
- **Key Takeaway:** Your APIs read like clean, natural domain specifications.

---

### Slide 6: Pattern 5 — Structured Concurrency & Resilience
- **Title:** 5. Failure Isolation with `supervisorScope`
- **Problem:** In standard `coroutineScope`, if Child Task A crashes, it cancels Child Task B and the whole parent scope!
- **Solution:** Use `supervisorScope` so one background task failure does not tear down independent parallel work.
- **Code Box:**
  ```kotlin
  supervisorScope {
      launch(handler) {
          // If this throws an error...
          throw DatabaseException("DB down")
      }
      launch {
          // This task continues running safely!
          sendAuditLog()
      }
  }
  ```
- **Key Takeaway:** Production microservices stay resilient against isolated failures.

---

### Slide 7: Outro / Call to Action
- **Headline:** Want to explore the complete runnable code?
- **Sub-headline:** All patterns, automated test suites, and the EventPulse Capstone engine are open-source on GitHub:
- **Repository Box:**
  - ⚡ **okkotlin** by TopToppy
  - 🔗 `github.com/toptoppy/okkotlin`
  - Features: Kotlin 2.1.0 • Coroutines 1.10.1 • Java 21 • 31 Automated Tests
- **Footer:**
  - 💬 Let me know which pattern you use most!
  - 🔄 Repost to share with your developer network.

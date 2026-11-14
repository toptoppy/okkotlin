package toptoppy.kotlin.training.programmingBasic

/**
 * Lesson 13: Null Safety in Kotlin
 *
 * In many programming languages (including Java), accessing a null reference causes
 * the infamous NullPointerException (NPE) — known as the "Billion-Dollar Mistake".
 *
 * Kotlin eliminates NPEs at compile time by separating types into:
 * 1. Non-nullable types: Cannot hold null (e.g., `String`).
 * 2. Nullable types: Can hold null, marked with a question mark (e.g., `String?`).
 */

fun main() {
    demonstrateNonNullableVsNullable()
    demonstrateSafeCallOperator()
    demonstrateElvisOperator()
    demonstrateSafeLet()
    demonstrateSafeCast()
}

// 1. Non-Nullable vs Nullable Types
fun demonstrateNonNullableVsNullable() {
    println("--- 1. Non-Nullable vs Nullable Types ---")

    var regularString: String = "Hello"
    // regularString = null // ❌ Compile Error! Non-nullable type cannot be null.
    println("Non-nullable length: ${regularString.length}")

    var nullableString: String? = "Hello World"
    nullableString = null // ✅ Perfectly valid!
    println("Nullable string is currently: $nullableString")

    // nullableString.length // ❌ Compile Error! Kotlin prevents direct access on nullable types.
}

// 2. Safe Call Operator (?.)
fun demonstrateSafeCallOperator() {
    println("\n--- 2. Safe Call Operator (?.) ---")

    val validName: String? = "Alice"
    val nullName: String? = null

    // If the variable is null, the expression returns null instead of throwing an NPE!
    val length1: Int? = validName?.length
    val length2: Int? = nullName?.length

    println("Length of '$validName': $length1") // 5
    println("Length of nullName: $length2") // null
}

// 3. Elvis Operator (?:) for Default Fallbacks
fun demonstrateElvisOperator() {
    println("\n--- 3. Elvis Operator (?:) ---")

    // The Elvis operator (named after Elvis Presley's hair curl ?:) returns the left-hand
    // expression if it is not null, otherwise it returns the right-hand fallback!
    val inputUsername: String? = null
    val displayName: String = inputUsername ?: "Guest_User"
    println("Welcome, $displayName!")

    val providedAge: Int? = null
    val age = providedAge ?: 18
    println("Registered Age: $age")
}

// 4. Safe Calls with `let`
fun demonstrateSafeLet() {
    println("\n--- 4. Safe Calls with let ---")

    val userEmail: String? = "student@example.com"
    val missingEmail: String? = null

    // `let` executes the lambda ONLY if the variable is not null:
    userEmail?.let { email ->
        println("Sending confirmation email to: $email")
    }

    missingEmail?.let { email ->
        println("This line will NEVER print because missingEmail is null!")
    }
}

// 5. Safe Cast Operator (as?)
fun demonstrateSafeCast() {
    println("\n--- 5. Safe Cast Operator (as?) ---")

    val anyValue: Any = 42

    // Unsafe cast: `anyValue as String` would throw ClassCastException!
    // Safe cast `as?`: Returns null if casting fails, avoiding runtime crashes.
    val textOrNull: String? = anyValue as? String
    println("Safe cast result of integer to String?: $textOrNull") // null

    val actualText: Any = "Kotlin is fun"
    val safeText: String? = actualText as? String
    println("Safe cast result of String to String?: $safeText") // "Kotlin is fun"
}

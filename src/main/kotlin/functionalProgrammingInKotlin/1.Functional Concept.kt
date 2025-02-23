package toptoppy.kotlin.training.functionalProgrammingInKotlin

fun main() {
    println("--- 1. Pure Function Example ---")
    pureFunctionExample()

    println("\n--- 2. Immutability Example ---")
    immutabilityExample()

    println("\n--- 3. Higher-Order Function Example ---")
    higherOrderFunctionExample()

    println("\n--- 4. Chaining with Map and FlatMap ---")
    mapFlatMapExample()

    println("\n--- 5. Error Handling with Result ---")
    resultHandlingExample()
}


// 1. Pure Function Example
fun pureFunctionExample() {
    // Imperative Style
    var total = 0
    fun addImperative(a: Int, b: Int) {
        total = a + b // Side effect (modifying state)
        println("Imperative Result: $total")
    }

    addImperative(5, 3)

    // Functional Style
    fun addFunctional(a: Int, b: Int): Int = a + b // Pure: no side effects
    println("Functional Result: ${addFunctional(5, 3)}")
}

// 2. Immutability Example
fun immutabilityExample() {
    // Imperative: Mutable List
    val mutableList = mutableListOf("Apple", "Banana")
    mutableList.add("Cherry")
    println("Imperative Mutable List: $mutableList")

    // Functional: Immutable List
    val immutableList = listOf("Apple", "Banana")
    val newList = immutableList + "Cherry"
    println("Functional Immutable List: $newList")
}


// 3. Higher-Order Function Example
fun higherOrderFunctionExample() {
    // Function that accepts another function as an argument
    fun operate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
        return operation(a, b)
    }

    val sum = operate(10, 5) { x, y -> x + y }
    val product = operate(10, 5) { x, y -> x * y }

    println("Sum using Higher-Order Function: $sum")
    println("Product using Higher-Order Function: $product")
}

// 4. Chaining with Map and FlatMap
fun mapFlatMapExample() {
    // Imperative Style
    val numbers = listOf(1, 2, 3)
    val doubledImperative = mutableListOf<Int>()
    for (num in numbers) {
        doubledImperative.add(num * 2)
    }
    println("Imperative Doubled List: $doubledImperative")

    // Functional Style
    val doubledFunctional = numbers.map { it * 2 }
    println("Functional Doubled List: $doubledFunctional")

    // FlatMap Example (Nested transformation)
    val nestedList = listOf(listOf(1, 2), listOf(3, 4))
    val flattenedList = nestedList.flatMap { it.map { num -> num * 2 } }
    println("Flattened List with FlatMap: $flattenedList")
}

// 5. Error Handling with Result
fun resultHandlingExample() {
    // Imperative Style
    fun divideImperative(a: Int, b: Int): Double? {
        return try {
            a.toDouble() / b
        } catch (e: Exception) {
            null
        }
    }
    println("Imperative Division Result: ${divideImperative(10, 2)}")

    // Functional Style
    fun divideFunctional(a: Int, b: Int): Result<Double> {
        return runCatching { a.toDouble() / b }
    }

    val result = divideFunctional(10, 0)
    result.fold(
        onSuccess = { println("Functional Division Success: $it") },
        onFailure = { println("Functional Division Error: ${it.message}") }
    )
}

/*
 * Summary of Functional Programming Concepts:
 * 1. Pure Functions: No side effects, consistent output for given input.
 * 2. Immutability: Data structures that cannot be changed.
 * 3. Higher-Order Functions: Functions that take or return other functions.
 * 4. Map and FlatMap: Transforming and flattening collections.
 * 5. Error Handling: Using Result for safe exception handling.
 */

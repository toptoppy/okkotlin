package toptoppy.kotlin.training.modernIdioms

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/*
 * Modern Kotlin 2.x: Delegation Patterns
 *
 * Demonstrates:
 * 1. Standard Property Delegates: `lazy`, `observable`, `vetoable`.
 * 2. Custom Property Delegates with operator overloading (`getValue`, `setValue`).
 * 3. Class Delegation (`by`) implementing the Decorator pattern cleanly.
 */

// 1. Custom Trimmed String Delegate
class TrimmedString(private var value: String = "") {
    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): String = value

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        newValue: String,
    ) {
        value = newValue.trim()
    }
}

// 2. Class using property delegates
class UserProfile(initialAge: Int) {
    // Lazy evaluation (thread-safe by default)
    val heavyCalculation: String by lazy {
        println("Computing expensive configuration...")
        "Config-Loaded-v2"
    }

    // Observable delegate: fires on change
    var displayName: String by Delegates.observable("Anonymous") { prop, old, new ->
        println("Property '${prop.name}' changed from '$old' to '$new'")
    }

    // Vetoable delegate: validates and rejects invalid state changes
    var age: Int by Delegates.vetoable(initialAge) { prop, old, new ->
        val isValid = new >= 0
        if (!isValid) println("Rejected change to '${prop.name}': age $new cannot be negative")
        isValid
    }

    // Custom delegate
    var bio: String by TrimmedString()
}

// 3. Class Delegation (Decorator Pattern)
interface Repository<T> {
    fun save(item: T)

    fun findById(id: String): T?
}

class InMemoryRepository<T> : Repository<T> {
    private val storage = mutableMapOf<String, T>()

    override fun save(item: T) {
        storage[item.hashCode().toString()] = item
    }

    override fun findById(id: String): T? = storage[id]
}

// LoggingRepository delegates implementation to underlying repository
class LoggingRepository<T>(
    private val delegate: Repository<T>,
) : Repository<T> by delegate {
    override fun save(item: T) {
        println("[LOG] Before save: $item")
        delegate.save(item)
        println("[LOG] After save complete")
    }
}

fun main() {
    val profile = UserProfile(initialAge = 25)
    profile.displayName = "Alex"
    profile.age = 26
    profile.age = -5 // will be rejected
    println("Current age: ${profile.age}")

    profile.bio = "   Software Engineer & Kotlin enthusiast    "
    println("Trimmed bio: '${profile.bio}'")
    println("Lazy value: ${profile.heavyCalculation}")

    println("\n--- Testing Class Delegation ---")
    val inMem = InMemoryRepository<String>()
    val loggingRepo = LoggingRepository(inMem)
    loggingRepo.save("UserRecord-42")
}

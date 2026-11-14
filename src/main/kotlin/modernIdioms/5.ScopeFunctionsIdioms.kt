package toptoppy.kotlin.training.modernIdioms

/**
 * Modern Kotlin 2.x: Scope Functions Cheat Sheet & Best Practices
 *
 * Scope functions execute a block of code within the context of an object.
 * Key distinctions:
 * - Context Object reference: `this` vs `it`
 * - Return value: Context object vs Lambda result
 *
 * | Function | Context Reference | Return Value   | Primary Use Case                              |
 * |----------|-------------------|----------------|-----------------------------------------------|
 * | `let`    | `it` (or named)   | Lambda result  | Null-checks (`?.let`), local transformation   |
 * | `apply`  | `this`            | Context object | Object configuration / initialization         |
 * | `run`    | `this`            | Lambda result  | Object config + computing a result            |
 * | `also`   | `it`              | Context object | Additional side effects (logging, debugging)  |
 * | `with`   | `this`            | Lambda result  | Grouping multiple calls on non-nullable target|
 */

data class ServiceConfig(
    var host: String = "",
    var port: Int = 8080,
    var timeoutMs: Long = 3000L,
    var isSecure: Boolean = true,
)

fun main() {
    // 1. `apply`: Configure an object and return it
    val config =
        ServiceConfig().apply {
            host = "gateway.internal"
            port = 8443
            isSecure = true
        }
    println("Configured via apply: $config")

    // 2. `also`: Perform side effects without altering the chain
    val readyConfig =
        config.also {
            println("[DEBUG] Validating port range: ${it.port}")
            require(it.port in 1..65535) { "Port out of range" }
        }

    // 3. `let`: Safe-call transformation
    val nullableUrl: String? = "https://kotlinlang.org"
    val uriScheme =
        nullableUrl?.let { url ->
            url.substringBefore("://")
        } ?: "unknown"
    println("Extracted scheme via let: $uriScheme")

    // 4. `run`: Object configuration + returning computed output
    val summary =
        config.run {
            "Connected to $host:$port (TLS: $isSecure)"
        }
    println("Summary via run: $summary")

    // 5. `with`: Non-extension grouping on an existing target
    val endpointDescription =
        with(config) {
            val protocol = if (isSecure) "https" else "http"
            "$protocol://$host:$port/healthz"
        }
    println("Health URL via with: $endpointDescription")

    // 6. `takeIf` and `takeUnless`: Conditional filtering
    val validPort = config.port.takeIf { it > 1024 }
    println("Privileged check with takeIf: port $validPort is valid")
}

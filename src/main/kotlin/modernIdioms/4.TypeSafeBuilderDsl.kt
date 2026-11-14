package toptoppy.kotlin.training.modernIdioms

/**
 * Modern Kotlin 2.x: Type-Safe Builder DSL
 *
 * Demonstrates:
 * 1. `@DslMarker` annotations to prevent scope leakage in nested builders.
 * 2. Lambdas with receiver (`Receiver.() -> Unit`) for declarative syntax.
 * 3. Building an expressive HTTP Request definition DSL.
 */

@DslMarker
annotation class HttpDslMarker

enum class HttpMethod { GET, POST, PUT, DELETE }

data class HttpRequest(
    val method: HttpMethod,
    val url: String,
    val headers: Map<String, String>,
    val queryParams: Map<String, String>,
    val body: String?,
)

@HttpDslMarker
class HeadersBuilder {
    private val headers = mutableMapOf<String, String>()

    infix fun String.to(value: String) {
        headers[this] = value
    }

    fun build(): Map<String, String> = headers.toMap()
}

@HttpDslMarker
class QueryParamsBuilder {
    private val params = mutableMapOf<String, String>()

    infix fun String.to(value: Any) {
        params[this] = value.toString()
    }

    fun build(): Map<String, String> = params.toMap()
}

@HttpDslMarker
class HttpRequestBuilder {
    var method: HttpMethod = HttpMethod.GET
    var url: String = ""
    var body: String? = null

    private var headers: Map<String, String> = emptyMap()
    private var queryParams: Map<String, String> = emptyMap()

    fun headers(block: HeadersBuilder.() -> Unit) {
        headers = HeadersBuilder().apply(block).build()
    }

    fun query(block: QueryParamsBuilder.() -> Unit) {
        queryParams = QueryParamsBuilder().apply(block).build()
    }

    fun build(): HttpRequest {
        require(url.isNotBlank()) { "URL must not be blank" }
        return HttpRequest(
            method = method,
            url = url,
            headers = headers,
            queryParams = queryParams,
            body = body,
        )
    }
}

// Top-level entry point function
fun httpRequest(block: HttpRequestBuilder.() -> Unit): HttpRequest {
    return HttpRequestBuilder().apply(block).build()
}

fun main() {
    val request =
        httpRequest {
            method = HttpMethod.POST
            url = "https://api.example.com/v1/orders"

            headers {
                "Authorization" to "Bearer token_secret_123"
                "Content-Type" to "application/json"
                "Accept" to "application/json"
            }

            query {
                "notify" to true
                "locale" to "en_US"
            }

            body = """{"orderId": "ord_99", "amount": 125.50}"""
        }

    println("Constructed Request via Type-Safe DSL:")
    println("Method: ${request.method}")
    println("URL: ${request.url}")
    println("Headers: ${request.headers}")
    println("Query: ${request.queryParams}")
    println("Body: ${request.body}")
}

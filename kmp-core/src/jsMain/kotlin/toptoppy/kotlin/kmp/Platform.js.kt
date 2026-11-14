package toptoppy.kotlin.kmp

actual fun getPlatformName(): String = "JavaScript (Node.js)"

actual fun getCurrentEpochMillis(): Long {
    val millis = js("Date.now()").unsafeCast<Double>()
    return millis.toLong()
}

actual fun generatePlatformUUID(): String {
    val chars = "0123456789abcdef"
    val template = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx"
    val randomDouble = { js("Math.random()").unsafeCast<Double>() }
    return template.map { char ->
        when (char) {
            'x' -> chars[(randomDouble() * 16).toInt()]
            'y' -> chars[(randomDouble() * 4).toInt() + 8]
            else -> char
        }
    }.joinToString("")
}

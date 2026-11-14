package toptoppy.kotlin.kmp

import java.util.UUID

actual fun getPlatformName(): String {
    val javaVersion = System.getProperty("java.version") ?: "unknown"
    return "JVM ($javaVersion)"
}

actual fun getCurrentEpochMillis(): Long = System.currentTimeMillis()

actual fun generatePlatformUUID(): String = UUID.randomUUID().toString()

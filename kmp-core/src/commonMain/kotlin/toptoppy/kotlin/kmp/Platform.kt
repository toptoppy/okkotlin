package toptoppy.kotlin.kmp

/**
 * Expected platform-specific declarations to be fulfilled by each target.
 */
expect fun getPlatformName(): String

expect fun getCurrentEpochMillis(): Long

expect fun generatePlatformUUID(): String

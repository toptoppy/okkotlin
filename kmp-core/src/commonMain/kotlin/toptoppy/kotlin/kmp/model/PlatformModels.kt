package toptoppy.kotlin.kmp.model

data class PlatformInfo(
    val platformName: String,
    val timestampMillis: Long,
    val deviceId: String,
)

data class TelemetryReport(
    val serviceName: String,
    val platform: PlatformInfo,
    val metricCount: Int,
    val isHealthy: Boolean,
)

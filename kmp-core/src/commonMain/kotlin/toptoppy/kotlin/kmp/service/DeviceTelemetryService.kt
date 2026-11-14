package toptoppy.kotlin.kmp.service

import toptoppy.kotlin.kmp.generatePlatformUUID
import toptoppy.kotlin.kmp.getCurrentEpochMillis
import toptoppy.kotlin.kmp.getPlatformName
import toptoppy.kotlin.kmp.model.PlatformInfo
import toptoppy.kotlin.kmp.model.TelemetryReport

class DeviceTelemetryService {
    fun currentPlatformInfo(): PlatformInfo =
        PlatformInfo(
            platformName = getPlatformName(),
            timestampMillis = getCurrentEpochMillis(),
            deviceId = generatePlatformUUID(),
        )

    fun createReport(
        serviceName: String,
        metricCount: Int,
        isHealthy: Boolean,
    ): TelemetryReport =
        TelemetryReport(
            serviceName = serviceName,
            platform = currentPlatformInfo(),
            metricCount = metricCount,
            isHealthy = isHealthy,
        )

    fun formatReportSummary(report: TelemetryReport): String {
        val status = if (report.isHealthy) "HEALTHY" else "DEGRADED"
        return "[${report.platform.platformName}] ${report.serviceName} ($status): ${report.metricCount} metrics processed"
    }
}

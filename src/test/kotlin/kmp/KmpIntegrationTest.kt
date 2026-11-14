package toptoppy.kotlin.training.kmp

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import toptoppy.kotlin.kmp.getPlatformName
import toptoppy.kotlin.kmp.service.DeviceTelemetryService
import kotlin.test.Test
import kotlin.test.assertTrue

class KmpIntegrationTest {
    @Test
    fun testConsumeKmpSharedModuleFromJvmApplication() {
        val service = DeviceTelemetryService()
        val report = service.createReport("EventPulse-JVM", 100, true)

        report.serviceName shouldBe "EventPulse-JVM"
        report.metricCount shouldBe 100
        report.isHealthy shouldBe true
        report.platform.platformName shouldContain "JVM"

        val summary = service.formatReportSummary(report)
        summary shouldContain "EventPulse-JVM (HEALTHY): 100 metrics processed"
        assertTrue(report.platform.deviceId.isNotEmpty())
    }

    @Test
    fun testDirectExpectActualResolutionOnJvm() {
        val platform = getPlatformName()
        platform shouldContain "JVM"
    }
}

package toptoppy.kotlin.kmp

import toptoppy.kotlin.kmp.service.DeviceTelemetryService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlatformTest {
    @Test
    fun testPlatformNameIsNotBlank() {
        val platform = getPlatformName()
        assertTrue(platform.isNotBlank(), "Platform name should not be blank")
    }

    @Test
    fun testCurrentEpochMillisIsPositive() {
        val millis = getCurrentEpochMillis()
        assertTrue(millis > 0L, "Current epoch millis should be positive")
    }

    @Test
    fun testPlatformUUIDIsNotEmptyAndUnique() {
        val uuid1 = generatePlatformUUID()
        val uuid2 = generatePlatformUUID()
        assertTrue(uuid1.isNotBlank())
        assertTrue(uuid2.isNotBlank())
        assertTrue(uuid1 != uuid2, "Generated UUIDs must be unique")
        assertEquals(36, uuid1.length, "UUID should be 36 characters long")
    }

    @Test
    fun testDeviceTelemetryServiceReport() {
        val service = DeviceTelemetryService()
        val report = service.createReport("EventCollector", 42, true)

        assertEquals("EventCollector", report.serviceName)
        assertEquals(42, report.metricCount)
        assertTrue(report.isHealthy)
        assertTrue(report.platform.platformName.isNotBlank())
        assertTrue(service.formatReportSummary(report).contains("HEALTHY"))
        assertTrue(service.formatReportSummary(report).contains("42 metrics processed"))
    }
}

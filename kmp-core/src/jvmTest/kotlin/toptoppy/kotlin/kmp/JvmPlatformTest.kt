package toptoppy.kotlin.kmp

import kotlin.test.Test
import kotlin.test.assertTrue

class JvmPlatformTest {
    @Test
    fun testPlatformIsJvm() {
        val platform = getPlatformName()
        assertTrue(platform.startsWith("JVM"), "Expected JVM platform but got: $platform")
    }
}

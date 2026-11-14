package toptoppy.kotlin.kmp

import kotlin.test.Test
import kotlin.test.assertTrue

class JsPlatformTest {
    @Test
    fun testPlatformIsJavaScript() {
        val platform = getPlatformName()
        assertTrue(platform.startsWith("JavaScript"), "Expected JavaScript platform but got: $platform")
    }
}

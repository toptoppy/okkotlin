package toptoppy.kotlin.training.programmingBasic

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BasicConceptsTest {

    @Test
    fun `test string template interpolation`() {
        val language = "Kotlin"
        val version = 2.1
        val message = "Welcome to $language $version"

        message shouldBe "Welcome to Kotlin 2.1"
    }

    @Test
    fun `test range progression calculation`() {
        val numbers = (1..10 step 2).toList()

        numbers shouldBe listOf(1, 3, 5, 7, 9)
    }

    @Test
    fun `test expression oriented if-else`() {
        val score = 85
        val grade = if (score >= 90) "A" else if (score >= 80) "B" else "C"

        assertEquals("B", grade)
    }

    @Test
    fun `test in keyword containment`() {
        val validRange = 'a'..'z'
        val testChar = 'k'

        assertTrue(testChar in validRange)
    }
}

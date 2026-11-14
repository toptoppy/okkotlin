package toptoppy.kotlin.training.programmingBasic

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
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
        val grade =
            if (score >= 90) {
                "A"
            } else if (score >= 80) {
                "B"
            } else {
                "C"
            }

        assertEquals("B", grade)
    }

    @Test
    fun `test in keyword containment`() {
        val validRange = 'a'..'z'
        val testChar = 'k'

        assertTrue(testChar in validRange)
    }

    // --- Newly Added Beginner Tests ---

    @Test
    fun `test while loop summation`() {
        var sum = 0
        var i = 1
        while (i <= 5) {
            sum += i
            i++
        }
        sum shouldBe 15
    }

    @Test
    fun `test number conversion without auto-widening`() {
        val intVal = 42
        val longVal: Long = intVal.toLong()
        val doubleVal: Double = intVal.toDouble()

        longVal shouldBe 42L
        doubleVal shouldBe 42.0
    }

    @Test
    fun `test safe call operator with null and non-null values`() {
        val presentName: String? = "Kotlin"
        val missingName: String? = null

        presentName?.length shouldBe 6
        missingName?.length.shouldBeNull()
    }

    @Test
    fun `test Elvis operator providing fallback values`() {
        val input: String? = null
        val result = input ?: "Default_Value"

        result shouldBe "Default_Value"

        val nonNullInput: String? = "Custom_Value"
        val nonNullResult = nonNullInput ?: "Default_Value"

        nonNullResult shouldBe "Custom_Value"
    }

    @Test
    fun `test safe cast operator as-safe preventing ClassCastException`() {
        val mixedList: List<Any> = listOf("Text", 123, true)

        val firstAsString: String? = mixedList[0] as? String
        val secondAsString: String? = mixedList[1] as? String

        firstAsString shouldBe "Text"
        secondAsString.shouldBeNull()
    }

    @Test
    fun `test beginner practice challenge - compute discounted price`() {
        // Challenge for learners: compute final price with optional discount
        fun calculateFinalPrice(
            basePrice: Double,
            discountPercentage: Double?,
        ): Double {
            val discount = discountPercentage ?: 0.0
            return basePrice * (1.0 - (discount / 100.0))
        }

        // Without discount -> returns full price
        calculateFinalPrice(100.0, null) shouldBe 100.0

        // With 20% discount -> returns 80.0
        calculateFinalPrice(100.0, 20.0) shouldBe 80.0
    }
}

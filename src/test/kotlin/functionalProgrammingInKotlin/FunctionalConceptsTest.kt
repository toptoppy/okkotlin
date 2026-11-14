package toptoppy.kotlin.training.functionalProgrammingInKotlin

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class FunctionalConceptsTest {

    @Test
    fun `test immutable list preservation`() {
        val original = listOf("Apple", "Banana")
        val extended = original + "Cherry"

        original shouldContainExactly listOf("Apple", "Banana")
        extended shouldContainExactly listOf("Apple", "Banana", "Cherry")
    }

    @Test
    fun `test higher order function execution`() {
        fun transform(values: List<Int>, op: (Int) -> Int): List<Int> = values.map(op)

        val doubled = transform(listOf(1, 2, 3)) { it * 2 }
        doubled shouldBe listOf(2, 4, 6)
    }

    @Test
    fun `test Result monad safe failure recovery`() {
        fun safeDivide(numerator: Int, denominator: Int): Result<Int> = runCatching {
            numerator / denominator
        }

        val success = safeDivide(10, 2)
        success.isSuccess shouldBe true
        success.getOrNull() shouldBe 5

        val failure = safeDivide(10, 0)
        failure.isFailure shouldBe true
        failure.getOrDefault(defaultValue = -1) shouldBe -1
    }
}

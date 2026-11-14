package toptoppy.kotlin.training.modernIdioms

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

class ModernIdiomsTest {

    @Test
    fun `test sealed payment status pattern matching`() {
        val pending = PaymentStatus.Pending("tx-1", attempt = 1)
        val completed = PaymentStatus.Completed("tx-1", "REC-100", 5000)

        isTerminalState(pending) shouldBe false
        isTerminalState(completed) shouldBe true
        formatPaymentMessage(completed) shouldContain "REC-100"
    }

    @Test
    fun `test value class zero-cost type safety and validation`() {
        val cust = CustomerId("cust_12345")
        cust.value shouldBe "cust_12345"

        shouldThrow<IllegalArgumentException> {
            CustomerId("invalid_prefix")
        }

        val price1 = UsdAmount(2500) // $25.00
        val price2 = UsdAmount(1050) // $10.50
        (price1 + price2).cents shouldBe 3550
        (price1 + price2).dollars shouldBe 35.50
    }

    @Test
    fun `test property delegation with vetoable and trimmed string`() {
        val profile = UserProfile(initialAge = 30)

        profile.age = 35
        profile.age shouldBe 35

        profile.age = -10 // should be rejected by vetoable
        profile.age shouldBe 35

        profile.bio = "   Cloud Engineer   "
        profile.bio shouldBe "Cloud Engineer"
    }

    @Test
    fun `test type-safe HTTP builder DSL`() {
        val request = httpRequest {
            method = HttpMethod.POST
            url = "https://api.test.com/v1/auth"
            headers {
                "Authorization" to "Bearer abc"
            }
            query {
                "redirect" to true
            }
        }

        request.method shouldBe HttpMethod.POST
        request.url shouldBe "https://api.test.com/v1/auth"
        request.headers["Authorization"] shouldBe "Bearer abc"
        request.queryParams["redirect"] shouldBe "true"
    }
}

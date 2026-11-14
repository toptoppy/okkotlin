package toptoppy.kotlin.training.introductionToObject

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

class ObjectModelingTest {

    @Test
    fun `test Cup initial state and capacity cap`() {
        val cup = Cup()
        cup.percentFull shouldBe 0
        cup.max shouldBe 100

        cup.add(40)
        cup.percentFull shouldBe 40

        // Adding beyond max should cap at max (100)
        cup.add(80)
        cup.percentFull shouldBe 100
    }

    @Test
    fun `test AlienSpecies constructor properties and description`() {
        val predator = AlienSpecies(name = "Predator", eyes = 2, hands = 2, legs = 2)

        predator.name shouldBe "Predator"
        predator.eyes shouldBe 2
        predator.describe() shouldContain "name: Predator"
        predator.describe() shouldContain "eyes: 2"
    }

    @Test
    fun `test Cookie public interaction and eating`() {
        val cookie = Cookie(isReady = false)

        // cookie.bite() is public and callable
        cookie.bite()

        // cookie.eat() triggers internal state transition and helper methods
        cookie.eat()
    }

    @Test
    fun `test beginner practice challenge - model a BankAccount class`() {
        // Beginner exercise: Create a simple BankAccount class with deposit and withdraw
        class BankAccount(val accountNumber: String, initialBalance: Double = 0.0) {
            var balance: Double = initialBalance
                private set

            fun deposit(amount: Double): Boolean {
                if (amount <= 0.0) return false
                balance += amount
                return true
            }

            fun withdraw(amount: Double): Boolean {
                if (amount <= 0.0 || amount > balance) return false
                balance -= amount
                return true
            }
        }

        val account = BankAccount("acc_1234", initialBalance = 100.0)
        account.deposit(50.0) shouldBe true
        account.balance shouldBe 150.0

        // Attempting to withdraw more than balance should fail
        account.withdraw(200.0) shouldBe false
        account.balance shouldBe 150.0

        // Valid withdrawal
        account.withdraw(40.0) shouldBe true
        account.balance shouldBe 110.0
    }
}

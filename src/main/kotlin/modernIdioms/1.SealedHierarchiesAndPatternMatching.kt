package toptoppy.kotlin.training.modernIdioms

import java.time.Instant

/**
 * Modern Kotlin 2.x: Sealed Interfaces & Pattern Matching
 *
 * Demonstrates:
 * 1. Sealed interface hierarchies for domain state representation (Algebraic Data Types).
 * 2. Exhaustive `when` expressions with automated smart casting.
 * 3. Modeling state machines with type safety and zero runtime exceptions.
 */

sealed interface PaymentStatus {
    val transactionId: String
    val timestamp: Instant

    data class Pending(
        override val transactionId: String,
        override val timestamp: Instant = Instant.now(),
        val attempt: Int = 1
    ) : PaymentStatus

    data class Authorized(
        override val transactionId: String,
        val authCode: String,
        override val timestamp: Instant = Instant.now()
    ) : PaymentStatus

    data class Completed(
        override val transactionId: String,
        val receiptNumber: String,
        val amountInCents: Long,
        override val timestamp: Instant = Instant.now()
    ) : PaymentStatus

    data class Failed(
        override val transactionId: String,
        val errorCode: String,
        val reason: String,
        override val timestamp: Instant = Instant.now()
    ) : PaymentStatus
}

fun formatPaymentMessage(status: PaymentStatus): String = when (status) {
    is PaymentStatus.Pending ->
        "Payment ${status.transactionId} is currently pending (attempt ${status.attempt})"
    is PaymentStatus.Authorized ->
        "Payment ${status.transactionId} authorized with code ${status.authCode}"
    is PaymentStatus.Completed ->
        "Payment ${status.transactionId} completed! Receipt: ${status.receiptNumber}, Total: $${status.amountInCents / 100.0}"
    is PaymentStatus.Failed ->
        "Payment ${status.transactionId} failed [${status.errorCode}]: ${status.reason}"
}

fun isTerminalState(status: PaymentStatus): Boolean = when (status) {
    is PaymentStatus.Completed, is PaymentStatus.Failed -> true
    is PaymentStatus.Pending, is PaymentStatus.Authorized -> false
}

fun main() {
    val pending = PaymentStatus.Pending(transactionId = "tx-1001", attempt = 2)
    val authorized = PaymentStatus.Authorized(transactionId = "tx-1001", authCode = "AUTH-8899")
    val completed = PaymentStatus.Completed(transactionId = "tx-1001", receiptNumber = "REC-4501", amountInCents = 9999)
    val failed = PaymentStatus.Failed(transactionId = "tx-1002", errorCode = "INSUFFICIENT_FUNDS", reason = "Declined by issuing bank")

    listOf(pending, authorized, completed, failed).forEach { status ->
        println(formatPaymentMessage(status))
        println("Is Terminal: ${isTerminalState(status)}\n")
    }
}

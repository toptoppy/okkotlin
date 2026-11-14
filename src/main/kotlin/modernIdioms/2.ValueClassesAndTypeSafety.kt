package toptoppy.kotlin.training.modernIdioms

/**
 * Modern Kotlin 2.x: Value Classes & Type Safety
 *
 * Demonstrates:
 * 1. `@JvmInline value class` for zero-cost type abstractions.
 * 2. Preventing "Primitive Obsession" bugs (swapping CustomerId vs OrderId).
 * 3. Inlined validation and computed properties without heap allocation penalty.
 */

@JvmInline
value class CustomerId(val value: String) {
    init {
        require(value.isNotBlank()) { "CustomerId cannot be blank" }
        require(value.startsWith("cust_")) { "CustomerId must start with 'cust_'" }
    }
}

@JvmInline
value class OrderId(val value: String) {
    init {
        require(value.isNotBlank()) { "OrderId cannot be blank" }
        require(value.startsWith("ord_")) { "OrderId must start with 'ord_'" }
    }
}

@JvmInline
value class UsdAmount(val cents: Long) {
    init {
        require(cents >= 0) { "Amount cannot be negative" }
    }

    val dollars: Double get() = cents / 100.0

    operator fun plus(other: UsdAmount): UsdAmount = UsdAmount(this.cents + other.cents)

    operator fun minus(other: UsdAmount): UsdAmount = UsdAmount(this.cents - other.cents)

    override fun toString(): String = "$${String.format("%.2f", dollars)}"
}

data class Order(
    val orderId: OrderId,
    val customerId: CustomerId,
    val total: UsdAmount,
)

fun processOrder(order: Order): String {
    // Notice that orderId and customerId cannot be accidentally swapped at compile-time!
    return "Processing order ${order.orderId.value} for customer ${order.customerId.value} with total ${order.total}"
}

fun main() {
    val cust = CustomerId("cust_98234")
    val order = OrderId("ord_55102")
    val itemPrice1 = UsdAmount(4500) // $45.00
    val itemPrice2 = UsdAmount(1550) // $15.50
    val total = itemPrice1 + itemPrice2

    val myOrder = Order(orderId = order, customerId = cust, total = total)
    println(processOrder(myOrder))
}

package toptoppy.kotlin.training.programmingBasic

fun main() {
    inference()
    stringPlusNumber()
    types()
}

private fun inference() {
    val n = 1 + 1.2
    println(n)
    println(n.javaClass.kotlin.simpleName)
    println(n::class.simpleName)
    println(n is Double)
}

private fun stringPlusNumber() {
    println("Sally" + 5.9)
}

private fun types() {
    val whole = 11
    val fractional = 1.4
    val trueOrFalse = true
    val words = "A value"
    val character = 'z'
    val lines =
        """
        Triple quotes let
        you have many lines
        in your string
        """.trimIndent()

    println(whole)
    println(fractional)
    println(trueOrFalse)
    println(words)
    println(character)
    println(lines)
}

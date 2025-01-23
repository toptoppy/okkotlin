package toptoppy.kotlin.training.programmingBasic

fun main() {
    stringTemplates()

    tripleQuotes()

    stringConcatenation()

    expressionInTemplate()
}

private fun stringTemplates() {
    val answer = 42
    println("Found $answer!")
}

private fun stringConcatenation() {
    val s = "hi\n"
    val n = 11
    val d = 3.14
    println(
        "first: " + s + "second: " +
            n + ", third: " + d,
    )
}

private fun tripleQuotes() {
    val s = "value"
    println("s = \"$s\".")
    println("""s = "$s".""")
}

private fun expressionInTemplate() {
    val condition = true
    println(
        "${if (condition) 'a' else 'b'}",
    )
    val x = 11
    println("$x + 4 = ${x + 4}")
}

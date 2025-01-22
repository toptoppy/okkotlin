package toptoppy.kotlin.training.programmingBasic

fun main() {
    ifExpressionEx()

    expressionInInitalValue()

    ifElseElseIf()

    oneLineIfElse()
}


private fun ifExpressionEx() {
    if (1 > 0) {
        println("It's true!")
    }

    if (0 < 1)
        println("It's also true!")
}

private fun expressionInInitalValue() {
    val x = 1 >= 1

    if (x)
        println("x is true!")
}

private fun ifElseElseIf() {
    val n: Int = -1
    if (n > 0) {
        println("n is positive!")
    } else if (n == 0) {
        println("n is zero!")
    } else {
        println("n is negative!")
    }
}

private fun oneLineIfElse() {
    val y = 10
    val result = if (y > 0) 200 else 400
    println(result)
}

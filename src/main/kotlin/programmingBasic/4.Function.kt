package toptoppy.kotlin.training.programmingBasic

fun main() {
    oneLineFunction()
    functionWithNoParameters()
    functionWithParameters(1,2)
    val plus = functionWithParametersWithReturnType(1.0, 2.0)
    println(plus)
}

fun oneLineFunction() = println("Hello, world!")

fun functionWithNoParameters() {
    println("Hello, world!")
}
// Default return type is Unit -> (Void in JAVA)

fun functionWithParameters(p1: Int, p2: Int) {
    println(p1 + p2)
}

fun functionWithParametersWithReturnType(p1: Double, p2: Double): Double {
    return p1 + p2
}
// or fun functionWithParametersWithReturnType(p1: Double, p2: Double) = p1 + p2
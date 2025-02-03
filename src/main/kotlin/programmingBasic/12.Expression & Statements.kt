package toptoppy.kotlin.training.programmingBasic

fun main() {
    val result1 = if (11 > 42) 9 else 5

    val result2 = if (1 < 2) {
        val a = 11
        a + 42
    } else 42

    val result3 =
        if ('x' < 'y')
            println("x < y")
        else
            println("x > y")

    println(result1)
    println(result2)
    println(result3)

    var i = 1
    println(i++ + ++i)

  // Can't do this:
  // val f = for(i in 1..10) {}
  // Compiler error message:
  // for is not an expression, and
  // only expressions are allowed here

}
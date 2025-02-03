package toptoppy.kotlin.training.programmingBasic

fun main() {
    val percent = 40
    println( percent in 0..100 )

    println( 't' in "kotlin" )

    for (i in 0..5) {
        println(i)
    }

    if (percent in 0..100) {
        println("$percent is a member of 0 to 100")
    }
}
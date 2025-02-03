package toptoppy.kotlin.training.introductionToObject

fun main(){
    val r1 = IntRange(0, 3)
    val r2 = IntRange(0, 3)

    println(r1.sum())
    println(r1 == r2)

    val beer1 = Beer()
    val beer2 = Beer()

    println(beer1 == beer2)
    println(beer1)
    println(beer2)
}

class Beer


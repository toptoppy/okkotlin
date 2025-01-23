package toptoppy.kotlin.training.programmingBasic

fun main() {
    repetitionWithWhile()
    repetitionWithDoWhile()
}

private fun repetitionWithWhile() {
    var i = 0
    while (i < 10) {
        println("😒".repeat(i))
        i += 1
    }
}

private fun repetitionWithDoWhile() {
    var i = 0
    do {
        println("😒".repeat(i))
        i++
    } while (i < 10)
}


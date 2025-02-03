package toptoppy.kotlin.training.programmingBasic

fun main() {
    loop()
    ranges()
    loopInRange()

    for (ch in "Jnskhm") {
        print(ch + 1)
    }
}

private fun loop() {
    for (i in 0..10) {
        println("🙏".repeat(i))
    }
}

private fun ranges() {
    // Int range
    val range1 = 1..5
    val range2 = 0 until 5
    val range3 = 5 downTo 1
    val range4 = 0..9 step 2
    val range5 = 0 until 10 step 3
    val range6 = 9 downTo 3 step 3

    val range9 = 0.."abc".lastIndex

    // Char range
    val range7 = 'a'..'z'
    val range8 = 'A'..'Z'

    showRange(range1)
    showRange(range2)
    showRange(range3)
    showRange(range4)
    showRange(range5)
    showRange(range6)
}

private fun showRange(r: IntProgression) {
    for (i in r) {
        print("$i ")
    }
    println("    // $r")
}

private fun loopInRange() {
    var sum = 0
    for (i in 5..10) {
        sum += i
    }
    println(sum)
}
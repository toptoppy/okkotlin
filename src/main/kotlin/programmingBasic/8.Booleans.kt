package toptoppy.kotlin.training.programmingBasic

fun main() = isOpen(10)

private fun isOpen(hour: Int) {
    val open = 9
    val close = 20
    println("Operating hours: $open - $close")
    val status = if (hour >= open && hour < close) {
        true
    } else {
        false
    }

    println(status)
}

private fun isOpen2(hour: Int) {
    val open = 9
    val close = 20
    println("Operating hours: $open - $close")
    val status = hour >= open && hour < close
    println(status)
}

private fun isHappy() {
    val sunny = true
    val hoursSleep = 6
    val exercise = false
    val temp = 55

    val happy = sunny && temp > 50 || exercise && hoursSleep > 7
    val sameHappy = (sunny && temp > 50) || (exercise && hoursSleep > 7)
    val notSame = (sunny && temp > 50 || exercise) && hoursSleep > 7

    println(happy)
    println(sameHappy)
    println(notSame)
}
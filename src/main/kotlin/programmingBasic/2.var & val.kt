package toptoppy.kotlin.training.programmingBasic

fun main() {
    vars()
    vals()
}

// Variable: Can reassign
private fun vars() {
    var whole = 11
    // var is mutable
    whole = 15
    whole = whole + 5
    whole += 5
    var fractional = 1.4
    var words = "Twas Brillig"
    println("var")
    println(whole)
    println(fractional)
    println(words)
}

// Value: only initialize it & Can not reassign
private fun vals() {
    val whole = 11
    // whole = 15 // Error
    val fractional = 1.4
    val words = "Twas Brillig"
    println("val")
    println(whole)
    println(fractional)
    println(words)
}
package com.jago.BasicProgramming

fun main() {
    vals()
//    valIsMutable()
//    vars()
}

private fun vals() {
    val whole = 11
    // whole = 15 // Error   // [1]
    val fractional = 1.4
    val words = "Twas Brillig"
    println(whole)
    println(fractional)
    println(words)
}
/* Output:
11
1.4
Twas Brillig
*/

private fun valIsMutable() {
    var sum = 1
    sum = sum + 2
    sum += 3
    println(sum)
}
/* Output:
6
*/

private fun vars() {
    var whole = 11              // [1]
    var fractional = 1.4        // [2]
    var words = "Twas Brillig"  // [3]
    println(whole)
    println(fractional)
    println(words)
}
/* Output:
11
1.4
Twas Brillig
*/
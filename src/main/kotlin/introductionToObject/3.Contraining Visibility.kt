package toptoppy.kotlin.training.introductionToObject

/*
[1] A Private property, not accessible outside the containing class
[2] A private member function
[3] A Public member function, accessible to anyone
[4] No access modifier means public
[5] Only member of the same class can access private members
 */

class Cookie(
    private var isReady: Boolean,               // [1]
) {
    private fun crumble() = println("crumble")  // [2]

    public fun bite() = println("bite")         // [3]

    fun eat() {                                 // [4]
        isReady = true                          // [5]
        crumble()
        bite()
    }
}

fun main(){
    val c = Cookie(false)
//    c.isReady = true
//    c.crumble()
    c.bite()
    c.eat()
}
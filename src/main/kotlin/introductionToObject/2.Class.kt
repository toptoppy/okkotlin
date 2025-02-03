package toptoppy.kotlin.training.introductionToObject

fun main() {
    classAndProperty()
    classAndConstructor()
}

private fun classAndProperty() {
    val c1 = Cup()
    val c2 = Cup()

    c1.add(50)
    c2.add(c1.percentFull)
    c2.add(49)

    println(c1.percentFull)
    println(c2.percentFull)

    c2.add(10)

    println(c2.percentFull)

    println(c1.max)

//    c1.max = 150
}


class Cup {
    var percentFull = 0
    val max = 100

    fun add(increase: Int): Int {
        percentFull += increase
        if (percentFull > max) {
            percentFull = max
        }
        return percentFull
    }
}


private fun classAndConstructor() {
    val predator = AlienSpecies("predator", 2,2,2)
    val zerg = AlienSpecies("zerg", 4,2,0)

    println(predator.describe())

    println(zerg.describe())
}

private class AlienSpecies(
    val name: String,
    val eyes: Int,
    val hands: Int,
    val legs: Int,
){
    fun describe() = "name: $name, eyes: $eyes, hands: $hands, legs: $legs"
}
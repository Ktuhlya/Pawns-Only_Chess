package chess

import kotlin.math.E
import kotlin.system.exitProcess

val ranksMap = mapOf(8 to 0, 7 to 1, 6 to 2, 5 to 3, 4 to 4, 3 to 5,
    2 to 6, 1 to 7)
val filesMap = mapOf("a" to 0, "b" to 1, "c" to 2, "d" to 3, "e" to 4,
                    "f" to 5, "g" to 6, "h" to 7)
val field = MutableList(8){MutableList<String>(8){"|   "}}

const val EXIT = "exit"
val name = mutableMapOf<Int, String>()

val mainRegex = Regex("[a-h][1-8][a-h][1-8]")


fun main() {

    println("Pawns-Only Chess")
    print("First Player's name:\n> ")
    name[1] = readLine()!!
    print("Second Player's name:\n>")
    name[-1] = readLine()!!

    for (i in 0..7) field[1][i] = "| B "
    for (i in 0..7) field[6][i] = "| W "

    printField()
    turn(1)


}

fun turn(nameKey: Int = 1) {

    print("${name[nameKey]}'s turn:\n> ")
    val str = readln()
    if (str.matches(mainRegex)) {
        turn(nameKey * (-1))
    } else {
        if (str == EXIT) {
            println("Bye!")
            exitProcess(0)
        }
        else {
            println("Invalid Input")
            turn(nameKey)
        }
    }
}

    fun printField() {

        val chertochki = listOf(
            " +---", "+---", "+---", "+---", "+---", "+---",
            "+---", "+---", "+"
        )

        println(chertochki.joinToString(""))
        for (i in 8 downTo 1) {
            print("$i ")
            print(field[ranksMap[i]!!].joinToString(""))
            println("|")
            print(" ")
            println(chertochki.joinToString(""))
        }
        print(" ")
        println("   a   b   c   d   e   f   g   h")
        println()

    }




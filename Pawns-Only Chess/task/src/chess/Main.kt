package chess

import kotlin.system.exitProcess

val ranksMap = mapOf(8 to 0, 7 to 1, 6 to 2, 5 to 3, 4 to 4, 3 to 5,
    2 to 6, 1 to 7)
val filesMap = mapOf("a" to 0, "b" to 1, "c" to 2, "d" to 3, "e" to 4,
                    "f" to 5, "g" to 6, "h" to 7)
var field = MutableList(8){MutableList<String>(8){"|   "}}

const val EXIT = "exit"
val name = mutableMapOf<Int, String>()
var nameKey = 1

val mainRegex = Regex("[a-h][1-8][a-h][1-8]")


fun main() {

    println("Pawns-Only Chess")
    print("First Player's name:\n> ")
    name[1] = readLine()!!
    print("Second Player's name:\n>")
    name[-1] = readLine()!!

    for (i in 0..7) field[1][i] = "| B "
    for (i in 0..7) field[6][i] = "| W "

    printField(field)
    turn(1)


}

fun turn(nameKey: Int = 1) {

    print("${name[nameKey]}'s turn:\n> ")
    val str = readln().trim()
    if (str.matches(mainRegex)) {
        val startPair = Pair(str.substring(0, 1), str.substring(1,2))
        val endPair = Pair(str.substring(2,3), str.substring(3))
        checkTurn(startPair, endPair, nameKey)
       // turn(nameKey * (-1))
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

fun checkTurn(startPair: Pair<String, String>, endPair: Pair<String, String>,
              nameKey: Int) {

    when (nameKey) {
        // White
        1 -> {
            if (field[ranksMap[startPair.second.toInt()]!!][filesMap[startPair.first]!!] == "| W ") {
                when {
                    ((startPair.second.toInt() == 2) && (endPair.second.toInt() == 4)
                            && (startPair.first == endPair.first)
                            && (field[ranksMap[endPair.second.toInt()]!!][filesMap[endPair.first]!!] == "|   ")) -> {
                        field[ranksMap[startPair.second.toInt()]!!][filesMap[startPair.first]!!] = "|   "
                        field[ranksMap[endPair.second.toInt()]!!][filesMap[endPair.first]!!] = "| W "
                        printField(field)
                        turn(nameKey*(-1))
                    }
                    (endPair.second.toInt() - startPair.second.toInt() == 1)
                            && (startPair.first == endPair.first)
                            && (field[ranksMap[endPair.second.toInt()]!!][filesMap[endPair.first]!!] == "|   ")-> {
                        field[ranksMap[startPair.second.toInt()]!!][filesMap[startPair.first]!!] = "|   "
                        field[ranksMap[endPair.second.toInt()]!!][filesMap[endPair.first]!!] = "| W "
                        printField(field)
                        turn(nameKey*(-1))
                    }
                    else -> {
                        println("Invalid Input")
                        turn(nameKey)
                    }
                }
            }else{
                println("No white pawn at ${startPair.toList().joinToString("")}")
                turn(nameKey)
            }
        }
        // Black
         -1 -> {
            if (field[ranksMap[startPair.second.toInt()]!!][filesMap[startPair.first]!!] == "| B ") {
                when {
                    ((startPair.second.toInt() == 7) && (endPair.second.toInt() == 5)
                            && (startPair.first == endPair.first)
                            && (field[ranksMap[endPair.second.toInt()]!!][filesMap[endPair.first]!!] == "|   ")) -> {
                        field[ranksMap[startPair.second.toInt()]!!][filesMap[startPair.first]!!] = "|   "
                        field[ranksMap[endPair.second.toInt()]!!][filesMap[endPair.first]!!] = "| B "
                        printField(field)
                        turn(nameKey*(-1))
                    }
                    (startPair.second.toInt() - endPair.second.toInt() == 1)
                            && (startPair.first == endPair.first)
                            && (field[ranksMap[endPair.second.toInt()]!!][filesMap[endPair.first]!!] == "|   ")-> {
                        field[ranksMap[startPair.second.toInt()]!!][filesMap[startPair.first]!!] = "|   "
                        field[ranksMap[endPair.second.toInt()]!!][filesMap[endPair.first]!!] = "| B "
                        printField(field)
                        turn(nameKey*(-1))
                    }
                    else -> {
                        println("Invalid Input")
                        turn(nameKey)
                    }
                }
            }else{
                println("No black pawn at ${startPair.toList().joinToString("")}")
                turn(nameKey)
            }
        }


    }
}



fun printField(field: MutableList<MutableList<String>>) {

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





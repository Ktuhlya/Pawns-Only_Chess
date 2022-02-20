package chess

import kotlin.system.exitProcess
import chess.ranksMap as ranksMap

val ranksMap = mapOf(8 to 0, 7 to 1, 6 to 2, 5 to 3, 4 to 4, 3 to 5,
    2 to 6, 1 to 7)
val filesMap = mapOf("a" to 0, "b" to 1, "c" to 2, "d" to 3, "e" to 4,
                    "f" to 5, "g" to 6, "h" to 7)
var field = MutableList(8){MutableList<String>(8){"|   "}}

const val EXIT = "exit"
val name = mutableMapOf<Int, String>()
//var nameKey = 1

val mainRegex = Regex("[a-h][1-8][a-h][1-8]")

val whiteList = mutableListOf<String>("a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2")
val blackList = mutableListOf<String>("a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7")

val letList = listOf<String>("a", "b", "c", "d", "e", "f", "g", "h")



fun main() {

    println("Pawns-Only Chess")
    print("First Player's name:\n> ")
    name[1] = readLine()!!
    print("Second Player's name:\n>")
    name[-1] = readLine()!!

 //   for (i in 0..7) field[1][i] = "| B "
 //   for (i in 0..7) field[6][i] = "| W "

    printField(field)
    turn(1)


}

fun turn(nameKey: Int = 1) {

    print("${name[nameKey]}'s turn:\n> ")
    val str = readln().trim()
    if (str.matches(mainRegex)) {
        val startPair = Pair(str.substring(0, 1), str.substring(1,2))
        val endPair = Pair(str.substring(2,3), str.substring(3))
        //checkTurn(startPair, endPair, nameKey)
            moveValid(splitter(str), nameKey)
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

fun fieldBuilder(field: MutableList<MutableList<String>>) {
    //var (col, row) =Pair()
    for (i in whiteList.indices){
        field[whiteList.splitVal(i).second!!][whiteList.splitVal(i).first!!] = "| W "
    }
    for (i in blackList.indices){
        field[blackList.splitVal(i).second!!][blackList.splitVal(i).first!!] = "| B "
    }
    println(whiteList)
    println(blackList)
    printField(field)

}

fun MutableList<String>.splitVal(indexD: Int): Pair<Int?, Int?> {
  //  var str: String = this[]
    val row = ranksMap[this[indexD].substring(1).toInt()]
    val col = filesMap[this[indexD].substring(0,1)]
    return Pair(col, row)
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

fun splitter(str: String): MutableList<String> {
   // val startPair = Pair(str.substring(0, 1), str.substring(1,2))
   // val endPair = Pair(str.substring(2,3), str.substring(3))
    val splitList = mutableListOf<String>()
    for (i in str.indices) splitList.add(str[i].toString())
    return splitList

}
fun moveValid(moveList: MutableList<String>, nameKey: Int, flag: Boolean = false) {
    when(nameKey) {
        1 -> {
            if (whiteList.contains(moveList[0]+moveList[1])) {
                when {
                    (moveList[0]==moveList[2]) && (moveList[1] == "2") && (moveList[3]=="4")
                            && cellContains(moveList[2]+moveList[3])=="empty"-> {
                        whiteList.remove(moveList[0]+moveList[1])
                        whiteList.add(moveList[2]+moveList[3])
                      //  println(whiteList)
                        fieldBuilder(field)
                        turn(nameKey*(-1))
                    }
                    (moveList[0]==moveList[2]) && (moveList[3].toInt()-moveList[1].toInt()==1)
                            && cellContains(moveList[2]+moveList[3])=="empty"-> {
                        whiteList.remove(moveList[0]+moveList[1])
                        whiteList.add(moveList[2]+moveList[3])
                       // println(whiteList)
                        fieldBuilder(field)
                        turn(nameKey*(-1))
                    }
                    (filesMap[moveList[0]]!! - 1 == filesMap[moveList[2]])
                            && (moveList[3].toInt()-moveList[1].toInt()==1)
                            && cellContains(moveList[2]+moveList[3])=="black"-> {
                        whiteList.remove(moveList[0]+moveList[1])
                        whiteList.add(moveList[2]+moveList[3])
                        blackList.remove(moveList[2]+moveList[3])
                        fieldBuilder(field)
                        turn(nameKey*(-1))
                    }
                    (filesMap[moveList[0]]!! + 1 == filesMap[moveList[2]])
                            && (moveList[3].toInt()-moveList[1].toInt()==1)
                            && cellContains(moveList[2]+moveList[3])=="black"-> {
                        whiteList.remove(moveList[0]+moveList[1])
                        whiteList.add(moveList[2]+moveList[3])
                        blackList.remove(moveList[2]+moveList[3])
                        fieldBuilder(field)
                        turn(nameKey*(-1))
                    }
                    flag && (moveList[3]=="6")  && (letList.indexOf(moveList[0])-1 == letList.indexOf(moveList[2]))
                                && (cellContains("${moveList[2]}${(moveList[3].toInt()-1)}") == "black") -> {
                        whiteList.remove(moveList[0]+moveList[1])
                        whiteList.add(moveList[2]+moveList[3])
                        blackList.remove("${moveList[2]}${(moveList[3].toInt()-1)}")
                        fieldBuilder(field)
                        turn(nameKey*(-1))
                        }
                    }
                }
            }
        }
    }


fun cellContains(cell: String): String {
    val cellContent: String = when(cell) {
        in whiteList -> "white"
        in blackList -> "black"
        else -> "empty"
    }
    return cellContent
}





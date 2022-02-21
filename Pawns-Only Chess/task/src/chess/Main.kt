package chess

import javax.swing.text.StyledEditorKit
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

var flag: Boolean = false


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

fun fieldBuilder(field: MutableList<MutableList<String>>) {

    for (i in field.indices){
        for (j in 0..7){
            field[i][j]= "|   "
        }
    }
    for (i in whiteList.indices){
        field[whiteList.splitVal(i).second!!][whiteList.splitVal(i).first!!] = "| W "
    }
    for (i in blackList.indices){
        field[blackList.splitVal(i).second!!][blackList.splitVal(i).first!!] = "| B "
    }
    printField(field)
}

fun MutableList<String>.splitVal(indexD: Int): Pair<Int?, Int?> {
    val row = ranksMap[this[indexD].substring(1).toInt()]
    val col = filesMap[this[indexD].substring(0,1)]
    return Pair(col, row)
}


fun printField(field: MutableList<MutableList<String>>) {
        val chertochki = listOf(
            " +---", "+---", "+---", "+---", "+---", "+---",
            "+---", "+---", "+"
        )
        print(" ")
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
    val splitList = mutableListOf<String>()
    for (i in str.indices) splitList.add(str[i].toString())
    return splitList

}
fun moveValid(moveList: MutableList<String>, nameKey: Int) {
    when(nameKey) {
        1 -> {
            if (whiteList.contains(moveList[0]+moveList[1])) {
                when {
                    (moveList[0]==moveList[2]) && (moveList[1] == "2") && (moveList[3]=="4")
                            && cellContains(moveList[2]+moveList[3])=="empty"-> {
                        whiteList.remove(moveList[0]+moveList[1])
                        whiteList.add(moveList[2]+moveList[3])
                        flag = true
                      //  println(whiteList)
                        fieldBuilder(field)
                        checkWin()
                        checkStalemate(nameKey*(-1))
                        turn(nameKey*(-1))
                    }
                    (moveList[0]==moveList[2]) && (moveList[3].toInt()-moveList[1].toInt()==1)
                            && cellContains(moveList[2]+moveList[3])=="empty"-> {
                        whiteList.remove(moveList[0]+moveList[1])
                        whiteList.add(moveList[2]+moveList[3])
                        if (flag) flag=false
                       // println(whiteList)
                        fieldBuilder(field)
                        checkWin()
                        checkStalemate(nameKey*(-1))
                        turn(nameKey*(-1))
                    }
                    (filesMap[moveList[0]]!! - 1 == filesMap[moveList[2]])
                            && (moveList[3].toInt()-moveList[1].toInt()==1)
                            && cellContains(moveList[2]+moveList[3])=="black"-> {
                        whiteList.remove(moveList[0]+moveList[1])
                        whiteList.add(moveList[2]+moveList[3])
                        blackList.remove(moveList[2]+moveList[3])
                        if (flag) flag=false
                        fieldBuilder(field)
                        checkWin()
                        checkStalemate(nameKey*(-1))
                        turn(nameKey*(-1))
                    }
                    (filesMap[moveList[0]]!! + 1 == filesMap[moveList[2]])
                            && (moveList[3].toInt()-moveList[1].toInt()==1)
                            && cellContains(moveList[2]+moveList[3])=="black"-> {
                        whiteList.remove(moveList[0]+moveList[1])
                        whiteList.add(moveList[2]+moveList[3])
                        blackList.remove(moveList[2]+moveList[3])
                        if (flag) flag=false
                        fieldBuilder(field)
                        checkWin()
                        checkStalemate(nameKey*(-1))
                        turn(nameKey*(-1))
                    }
                    flag && (moveList[3]=="6")  && (letList.indexOf(moveList[0])-1 == letList.indexOf(moveList[2]))
                                && (cellContains("${moveList[2]}${(moveList[3].toInt()-1)}") == "black") -> {
                        whiteList.remove(moveList[0]+moveList[1])
                        whiteList.add(moveList[2]+moveList[3])
                        blackList.remove("${moveList[2]}${(moveList[3].toInt()-1)}")
                        if (flag) flag=false
                        fieldBuilder(field)
                        checkWin()
                        checkStalemate(nameKey*(-1))
                        turn(nameKey*(-1))
                        }
                    else -> {
                        println("Invalid Input")
                        turn(nameKey)
                    }
                    }

                }else{
                println("No white pawn at ${moveList[0]+moveList[1]}")
                turn(nameKey)
            }
            }

        -1 -> {
            if (blackList.contains(moveList[0]+moveList[1])) {
                when {
                    (moveList[0]==moveList[2]) && (moveList[1] == "7") && (moveList[3]=="5")
                            && cellContains(moveList[2]+moveList[3])=="empty"-> {
                        blackList.remove(moveList[0]+moveList[1])
                        blackList.add(moveList[2]+moveList[3])
                        flag = true
                      //  println(whiteList)
                        fieldBuilder(field)
                        checkWin()
                        checkStalemate(nameKey*(-1))
                        turn(nameKey*(-1))
                    }
                    (moveList[0]==moveList[2]) && (moveList[1].toInt()-moveList[3].toInt()==1)
                            && cellContains(moveList[2]+moveList[3])=="empty"-> {
                        blackList.remove(moveList[0]+moveList[1])
                        blackList.add(moveList[2]+moveList[3])
                        if (flag) flag=false
                       // println(whiteList)
                        fieldBuilder(field)
                        checkWin()
                        checkStalemate(nameKey*(-1))
                        turn(nameKey*(-1))
                    }
                    (filesMap[moveList[0]]!! - 1 == filesMap[moveList[2]])
                            && (moveList[1].toInt()-moveList[3].toInt()==1)
                            && cellContains(moveList[2]+moveList[3])=="white"-> {
                        blackList.remove(moveList[0]+moveList[1])
                        blackList.add(moveList[2]+moveList[3])
                        whiteList.remove(moveList[2]+moveList[3])
                        if (flag) flag=false
                        fieldBuilder(field)
                        checkWin()
                        checkStalemate(nameKey*(-1))
                        turn(nameKey*(-1))
                    }
                    (filesMap[moveList[0]]!! + 1 == filesMap[moveList[2]])
                            && (moveList[1].toInt()-moveList[3].toInt()==1)
                            && cellContains(moveList[2]+moveList[3])=="white"-> {
                        blackList.remove(moveList[0]+moveList[1])
                        blackList.add(moveList[2]+moveList[3])
                        whiteList.remove(moveList[2]+moveList[3])
                        if (flag) flag=false
                        fieldBuilder(field)
                        checkWin()
                        checkStalemate(nameKey*(-1))
                        turn(nameKey*(-1))
                    }
                    flag && (moveList[3]=="6")  && (letList.indexOf(moveList[0])-1 == letList.indexOf(moveList[2]))
                                && (cellContains("${moveList[2]}${(moveList[3].toInt()+1)}") == "white") -> {
                        blackList.remove(moveList[0]+moveList[1])
                        blackList.add(moveList[2]+moveList[3])
                        whiteList.remove("${moveList[2]}${(moveList[3].toInt()-1)}")
                        if (flag) flag=false
                        fieldBuilder(field)
                        checkWin()
                        checkStalemate(nameKey*(-1))
                        turn(nameKey*(-1))
                        }
                    else -> {
                        println("Invalid Input")
                        turn(nameKey)
                    }
                    }
                }else{
                println("No black pawn at ${moveList[0]+moveList[1]}")
                turn(nameKey)
            }
        }

        }
    }

fun checkStalemate(nameKey: Int) {
    val davaiPoka = "Stalemate!\nBye!"
    when (nameKey) {
        1 -> {
            if (perdakGoritW()) {
                println(davaiPoka)
                exitProcess(0)
            }
        }
        -1 -> {
            if (perdakGoritB()) {
              println(davaiPoka)
                exitProcess(0)
            }
        }
    }
}
fun checkWin() {
    val regexWhite = Regex("\\w8")
    val regexBlack = Regex("\\w1")
    if (blackList.isEmpty()) {
        println("White Wins!")
        println("Bye!")
        exitProcess(0)
    }
    if (whiteList.isEmpty()) {
        println("Black Wins!")
        println("Bye!")
        exitProcess(0)
    }
    for (i in whiteList.indices) {
        if (whiteList[i].matches(regexWhite)) {
            println("White Wins!")
            println("Bye!")
            exitProcess(0)
        }
    }
    for (i in blackList.indices) {
        if (blackList[i].matches(regexBlack)) {
            println("Black Wins!")
            println("Bye!")
            exitProcess(0)
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

fun perdakGoritW(): Boolean {
    var count: Int = 0
    for (i in whiteList.indices){
        var fw = Pair(whiteList.splitVal(i).second!! - 1, whiteList.splitVal(i).first)
        var left = Pair(whiteList.splitVal(i).second!! - 1, whiteList.splitVal(i).first!! - 1)
        var right = Pair(whiteList.splitVal(i).second!! - 1, whiteList.splitVal(i).first!! + 1)
        if (left.second < 0) left = Pair (left.first, 1)
        if (right.second > 7) right = Pair (right.first, 6)


        if (field[fw.first][fw.second!!] != "|   ") {
            if ((field[left.first][left.second] != "| B ")
                && (field[right.first][right.second] != "| B ")){
               count+=1
            }
        }
    }
    return whiteList.size == count
}

fun perdakGoritB(): Boolean {
    var count : Int = 0
    for (i in blackList.indices) {
        var fw = Pair(blackList.splitVal(i).second!! + 1, blackList.splitVal(i).first)
        var left = Pair(blackList.splitVal(i).second!! + 1, blackList.splitVal(i).first!! - 1)
        var right = Pair(blackList.splitVal(i).second!! + 1, blackList.splitVal(i).first!! + 1)
        if (left.second < 0) left = Pair (left.first, 1)
        if (right.second > 7) right = Pair (right.first, 6)
        if (field[fw.first][fw.second!!] != "|   ") {
            if ((field[left.first][left.second] != "| W ")
                && (field[right.first][right.second] != "| W ")) {
              count +=1
               // println("count $count")
               // println("size ${blackList.size}")
            }
        }
    }
    return blackList.size == count
}






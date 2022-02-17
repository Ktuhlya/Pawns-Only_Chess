package chess

val ranksMap = mapOf(8 to 0, 7 to 1, 6 to 2, 5 to 3, 4 to 4, 3 to 5,
    2 to 6, 1 to 7)
val filesMap = mapOf("a" to 0, "b" to 1, "c" to 2, "d" to 3, "e" to 4,
                    "f" to 5, "g" to 6, "h" to 7)
val field = MutableList(8){MutableList<String>(8){"|   "}}

fun main() {
    for (i in 0..7) field[1][i] = "| B "
    for (i in 0..7) field[6][i] = "| W "
printField()


}

fun printField() {

    val chertochki = listOf(" +---", "+---", "+---", "+---", "+---", "+---",
        "+---", "+---", "+")
    println("Pawns-Only Chess")
    println(chertochki.joinToString(""))
    for (i in 8 downTo 1) {
        print("$i ")
        print(field[ranksMap[i]!!].joinToString(""))
        println("|")
        print(" ")
        println(chertochki.joinToString(""))
    }
    print(" ")
    print("   a   b   c   d   e   f   g   h")

}



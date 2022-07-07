package day4

import java.io.File

fun main() {
    val input = File("data/day4/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

fun Char.shift(offset: Int) = ((((this - 'a') + offset) % 26) + 'a'.code).toChar()

data class Room(val name: String, val id: Int, val checksum: String) {

    fun valid(): Boolean {
        val letters = name.replace("-", "").groupingBy { it }
            .eachCount().toList()
            .sortedBy { it.first }.sortedByDescending { it.second }
        val code = letters.map { it.first }.take(5).joinToString("")
        return code == checksum
    }

    fun decrypt(): String =
        name.map { if (it == '-') ' ' else it.shift(id) }.joinToString("")
}

fun breakfast(input: List<String>) {
    val rooms = input.map {
        val letters = it.substringBeforeLast("-")
        val rest = it.substringAfterLast("-")
        val id = rest.substring(0, 3).toInt()
        val checksum = rest.substring(4, 9)
        Room(letters, id, checksum)
    }
    val result = rooms.filter { it.valid() }.sumOf { it.id }
    println(result)
}

fun lunch(input: List<String>) {
    val rooms = input.map {
        val letters = it.substringBeforeLast("-")
        val rest = it.substringAfterLast("-")
        val id = rest.substring(0, 3).toInt()
        val checksum = rest.substring(4, 9)
        Room(letters, id, checksum)
    }
    val result = rooms.filter { it.valid() }
        .first() { it.decrypt().contains("northpole") }
    println(result.id)
    println(result.decrypt())
}
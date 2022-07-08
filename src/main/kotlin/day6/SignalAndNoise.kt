package day6

import java.io.File

fun main() {
    val input = File("data/day6/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

fun breakfast(input: List<String>) {
    val length = input.first().length
    val message = (0 until length).map { position ->
        val chars = input.map { it[position] }.joinToString("")
        val groups = chars.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }
        groups.first().first
    }.joinToString("")
    println(message)
}

fun lunch(input: List<String>) {
    val length = input.first().length
    val message = (0 until length).map { position ->
        val chars = input.map { it[position] }.joinToString("")
        val groups = chars.groupingBy { it }.eachCount().toList().sortedBy { it.second }
        groups.first().first
    }.joinToString("")
    println(message)
}
package day3

import java.io.File

fun main() {
    val input = File("data/day3/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

fun breakfast(input: List<String>) {
    val triangles = input.map { it.trim().split("\\s+".toRegex()).map { n -> n.toInt() }.sorted() }
    val result = triangles.filter { it[0] + it[1] > it[2] }.size
    println(result)
}

fun lunch(input: List<String>) {
    val cols = input.map { it.trim().split("\\s+".toRegex()).map { n -> n.toInt() } }
    val values = cols.map { it[0] } + cols.map { it[1] } + cols.map { it[2] }
    val triangles = values.windowed(3, 3).map { it.sorted() }
    val result = triangles.filter { it[0] + it[1] > it[2] }.size
    println(result)
}
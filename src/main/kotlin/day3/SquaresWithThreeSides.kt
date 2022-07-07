package day3

import java.io.File

fun main() {
    val input = File("data/day3/input.txt").readLines()
    breakfast(input)
}

fun breakfast(input: List<String>) {
    val triangles = input.map { it.trim().split("\\s+".toRegex()).map { n -> n.toInt() }.sorted() }
    val result = triangles.filter { it[0] + it[1] > it[2] }.size
    println(result)
}
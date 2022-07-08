package day9

import java.io.File

fun main() {
    val input = File("data/day9/input.txt").readText()
    breakfast(input)
}

fun breakfast(input: String) {
    var rest = input
    var length = 0

    // since we are just interested in the length we don't need to preserve the string
    while (rest.contains("(") && rest.contains(")")) {
        length += rest.indexOf('(')
        val nextToken = rest.substringAfter("(").substringBefore(")")
        val nextCount = nextToken.substringBefore("x").toInt()
        val nextTimes = nextToken.substringAfter("x").toInt()
        length += nextCount * nextTimes
        rest = rest.substringAfter(")").substring(nextCount)
    }
    length += rest.length
    println(length)
}
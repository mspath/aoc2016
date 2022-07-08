package day9

import java.io.File

fun main() {
    val input = File("data/day9/input.txt").readText()
    breakfast(input)
    lunch(input)
}

fun breakfast(input: String) {
    var rest = input
    var length = 0

    // since we are just interested in the length we don't need to preserve the string
    while (rest.contains("(") && rest.contains(")")) {
        length += rest.indexOf('(')
        val marker = rest.substringAfter("(").substringBefore(")")
        val count = marker.substringBefore("x").toInt()
        val times = marker.substringAfter("x").toInt()
        length += count * times
        rest = rest.substringAfter(")").substring(count)
    }
    length += rest.length
    println(length)
}

fun getExpandedLength(input: String): Long {
    if (!(input.contains("(") && input.contains(")")))
        return input.length.toLong()

    var rest = input
    var length = 0L

    while (rest.contains("(") && rest.contains(")")) {
        length += rest.indexOf('(')
        val marker = rest.substringAfter("(").substringBefore(")")
        val count = marker.substringBefore("x").toInt()
        val times = marker.substringAfter("x").toInt()
        val markedStart = rest.indexOf(')') + 1
        length += getExpandedLength(rest.substring(markedStart, markedStart + count)) * times
        rest = rest.substringAfter(")").substring(count)
    }
    length += rest.length
    return length
}

fun lunch(input: String) {
    val result = getExpandedLength(input)
    println(result)
}
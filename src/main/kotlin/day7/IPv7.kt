package day7

import java.io.File

fun main() {
    val input = File("data/day7/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

fun String.isAbba(): Boolean {
    val windows = this.windowed(4, 1)
    return windows.any { it[0] == it[3] && it[1] == it[2] && it[0] != it[1] }
}

fun String.isValid(): Boolean {
    val parts = this.split("[", "]")
    val supernet = parts.filterIndexed { index, s -> index % 2 == 0 }
    val hypernet = parts.filterIndexed { index, s -> index % 2 == 1 }
    return supernet.any { it.isAbba() } && hypernet.none { it.isAbba() }
}

fun breakfast(input: List<String>) {
    val valid = input.filter { it.isValid() }
    println(valid.size)
}

fun String.getAbas(): List<String> {
    val windows = this.windowed(3, 1)
    return windows.filter { it[0] == it[2] && it[0] != it[1] }
}

fun String.isSSL(): Boolean {
    val parts = this.split("[", "]")
    val supernet = parts.filterIndexed { index, _ -> index % 2 == 0 }
    val hypernet = parts.filterIndexed { index, _ -> index % 2 == 1 }
    val abas = supernet.flatMap { it.getAbas() }
    val babs = hypernet.flatMap { it.getAbas() }.map { "" + it[1] + it[0] + it[1] }
    return abas.any { it in babs }
}

fun lunch(input: List<String>) {
    val valid = input.filter { it.isSSL() }
    println(valid.size)
}
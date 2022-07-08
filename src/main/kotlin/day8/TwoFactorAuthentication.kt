package day8

import java.io.File

typealias Display = List<Pixel>

fun main() {
    val input = File("data/day8/input.txt").readLines()
    breakfast(input)
}

fun Display.print() {
    println("-----")
    (0 until 8).forEach { y ->
        (0 until 50).forEach { x ->
            if (this.first { it.x == x && it.y == y }.on) print('*') else print(' ')
        }
        print('\n')
    }
}

data class Pixel(val x: Int, val y: Int, var on: Boolean = false)

data class Command(val command: String) {

    fun process(display: Display) {
        when {
            command.startsWith("rect") -> {
                val width = command.substringAfter("rect ")
                    .substringBefore("x").toInt()
                val height = command.substringAfter("x").toInt()
                val pixels = display.filter { it.x < width && it.y < height }
                pixels.forEach { it.on = true }
            }
            command.startsWith("rotate row") -> {
                val row = command.substringAfter("=")
                    .substringBefore(" by ").toInt()
                val steps = command.substringAfter(" by ").toInt()
                val pixelsRow = display.filter { it.y == row }
                val indexesOn = pixelsRow.filter { it.on }.map { it.x }
                pixelsRow.forEach { it.on = false }
                indexesOn.forEach {
                    val x = (it + steps) % 50
                    pixelsRow.first { p -> p.x == x }.on = true
                }
            }
            command.startsWith("rotate column") -> {
                val column = command.substringAfter("=")
                    .substringBefore(" by ").toInt()
                val steps = command.substringAfter(" by ").toInt()
                val pixelsColumn = display.filter { it.x == column }
                val indexesOn = pixelsColumn.filter { it.on }.map { it.y }
                pixelsColumn.forEach { it.on = false }
                indexesOn.forEach {
                    val y = (it + steps) % 8
                    pixelsColumn.first { p -> p.y == y }.on = true
                }
            }
        }
    }
}

fun breakfast(input: List<String>) {
    val display = (0 until 8).flatMap { y ->
        (0 until 50).map { x ->
            Pixel(x, y)
        }
    }
    val commands = input.map { Command(it) }
    commands.forEach {
        it.process(display)
        display.print()
    }
    val result = display.filter { it.on }.size
    println(result)
}
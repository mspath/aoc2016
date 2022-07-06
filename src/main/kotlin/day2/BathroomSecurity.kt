package day2

import java.io.File

fun main() {
    val input = File("data/day2/input.txt").readLines()
    breakfast(input)
}

data class Position(var value: Int) {

    fun processInstruction(instruction: Char) {
        when (instruction) {
            'U' -> {
                when (this.value) {
                    4, 5, 6, 7, 8, 9 -> this.value -= 3
                }
            }
            'D' -> {
                when (this.value) {
                    1, 2, 3, 4, 5, 6 -> this.value += 3
                }
            }
            'L' -> {
                when (this.value) {
                    2, 3, 5, 6, 8, 9 -> this.value -= 1
                }
            }
            'R' -> {
                when (this.value) {
                    1, 2, 4, 5, 7, 8 -> this.value += 1
                }
            }
        }
    }

    fun processInstructions(instructions: String): Position {
        instructions.toCharArray().forEach { instruction ->
            this.processInstruction(instruction)
        }
        return Position(this.value)
    }
}

fun breakfast(input: List<String>) {
    println(input)
    var position = Position(5)
    input.forEach { instructions ->
        position = position.processInstructions(instructions)
        print(position.value)
    }
}
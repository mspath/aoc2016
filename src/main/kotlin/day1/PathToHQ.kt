package day1

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("data/day1/input.txt").readText().split(", ")
    breakfast(input)
    lunch(input)
}

data class Instruction(val direction: Char, val steps: Int)

enum class Orientation {
    NORTH,
    EAST,
    SOUTH,
    WEST
}

data class Position(var x: Int = 0,
                    var y: Int = 0,
                    var orientation: Orientation = Orientation.NORTH) {

    fun update(instruction: Instruction) {
        when (instruction.direction) {
            'R' -> {
                this.orientation = when (this.orientation) {
                    Orientation.NORTH -> Orientation.EAST
                    Orientation.EAST -> Orientation.SOUTH
                    Orientation.SOUTH -> Orientation.WEST
                    Orientation.WEST -> Orientation.NORTH
                }
            }
            'L' -> {
                this.orientation = when (this.orientation) {
                    Orientation.NORTH -> Orientation.WEST
                    Orientation.EAST -> Orientation.NORTH
                    Orientation.SOUTH -> Orientation.EAST
                    Orientation.WEST -> Orientation.SOUTH
                }
            }
        }
        when (this.orientation) {
            Orientation.NORTH -> y -= instruction.steps
            Orientation.SOUTH -> y += instruction.steps
            Orientation.WEST -> x -= instruction.steps
            Orientation.EAST -> x += instruction.steps
        }
    }
}

fun breakfast(input: List<String>) {
    val directions = input.map {
        val direction = it[0]
        val steps = it.substring(1).toInt()
        Instruction(direction, steps)
    }
    val position = Position()
    directions.forEach { instruction ->
        position.update(instruction)
    }
    println(position)
    val result = abs(position.x) + abs(position.y)
    println(result)
}

fun lunch (input: List<String>) {
    val directions = input.map {
        val direction = it[0]
        val steps = it.substring(1).toInt()
        Instruction(direction, steps)
    }
    val position = Position()
    val visited: MutableSet<Pair<Int, Int>> = mutableSetOf(Pair(0, 0))
    var counter = 0
    run breaking@{
        directions.forEach { instruction ->
            position.update(instruction)
            val current = Pair(position.x, position.y)
            if (visited.contains(current)) return@breaking
            visited.add(current)
        }
    }
    println(position)
    val result = abs(position.x) + abs(position.y)
    println(result)
}
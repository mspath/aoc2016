package day12

import java.io.File
import java.rmi.registry.LocateRegistry

typealias Registers = MutableMap<Char, Int>

fun main() {
    val input = File("data/day12/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

data class Instruction(val command: String, val params: String) {

    // to simplify the handling of jumps it will return the offset of the next index position
    fun process(registers: Registers): Int {
        when (command) {
            "cpy" -> {
                val key = params.split(" ").first()
                val value = if (key[0] in 'a'..'d') {
                    registers.getValue(key[0])
                } else {
                    key.toInt()
                }
                val register = params.split(" ").last()[0]
                registers[register] = value
                return 1
            }
            "inc" -> {
                val register = params[0]
                registers[register] = registers.getValue(register) + 1
                return 1
            }
            "dec" -> {
                val register = params[0]
                registers[register] = registers.getValue(register) - 1
                return 1
            }
            "jnz" -> {
                val key = params.split(" ").first()
                val steps = params.split(" ").last().toInt()
                return if (key[0] in 'a'..'d') {
                    if (registers.getValue(key[0]) > 0) steps else 1
                } else {
                    steps
                }
            }
            else -> return 0
        }
    }
}

fun breakfast(input: List<String>) {
    val registers = listOf('a' to 0, 'b' to 0, 'c' to 0, 'd' to 0).toMap().toMutableMap()
    val instructions = input.map {
        val command = it.substring(0, 3)
        val params = it.substring(4)
        Instruction(command, params)
    }
    var index = 0
    while (index in instructions.indices) {
        index += instructions[index].process(registers)
    }
    println(registers)
}

fun lunch(input: List<String>) {
    val registers = listOf('a' to 0, 'b' to 0, 'c' to 1, 'd' to 0).toMap().toMutableMap()
    val instructions = input.map {
        val command = it.substring(0, 3)
        val params = it.substring(4)
        Instruction(command, params)
    }
    var index = 0
    while (index in instructions.indices) {
        index += instructions[index].process(registers)
    }
    println(registers)
}
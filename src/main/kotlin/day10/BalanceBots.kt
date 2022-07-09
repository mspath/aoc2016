package day10

import java.io.File

fun main() {
    val input = File("data/day10/input.txt").readLines()
    breakfast(input)
}

enum class Receiver {
    BIN,
    BOT
}

data class Rule(val bot: Int,
                val lowType: Receiver, val lowId: Int,
                val highType: Receiver, val highId: Int)

data class Instruction(val value: Int, val bot: Int)

data class Bot(val id: Int, val values: MutableList<Int> = mutableListOf()) {
    fun getLow() = values.min()
    fun getHigh() = values.max()
    fun checks17and61() = getLow() == 17 && getHigh() == 61
}

fun breakfast(input: List<String>) {
    val rules = input.filter { it.startsWith("bot ") }.map {
        val id = it.substringAfter("bot ").substringBefore(" gives").toInt()
        val low = it.substringAfter("low to ").substringBefore(" and")
        val high = it.substringAfter(" and high to ")
        val lowType = if (low.split(" ").first().contains("output")) Receiver.BIN
        else Receiver.BOT
        val lowId = low.split(" ").last().toInt()
        val highType = if (low.split(" ").first().contains("output")) Receiver.BIN
        else Receiver.BOT
        val highId = high.split(" ").last().toInt()
        Rule(id, lowType, lowId, highType, highId)
    }.associateBy { it.bot }
    val bots: MutableMap<Int, Bot> = mutableMapOf()
    val instructions = input.filter { it.startsWith("value ") }.map {
        val value = it.substringAfter("value ").substringBefore(" goes to").toInt()
        val bot = it.substringAfter("bot ").toInt()
        Instruction(value, bot)
    }.toMutableList()
    while (instructions.isNotEmpty()) {
        val instruction = instructions.removeAt(0)
        val bot = bots.getOrPut(instruction.bot) { Bot(instruction.bot) }
        bot.values.add(instruction.value)
        if (bot.values.size == 2) {
            if (bot.checks17and61()) {
                println("bot: ${bot.id}")
            }
            val rule = rules.getValue(bot.id)
            if (rule.lowType == Receiver.BOT) instructions.add(0, Instruction(bot.getLow(), rule.lowId))
            if (rule.highType == Receiver.BOT) instructions.add(0, Instruction(bot.getHigh(), rule.highId))
            bot.values.clear()
        }
    }
}
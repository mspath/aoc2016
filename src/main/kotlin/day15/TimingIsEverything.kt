package day15

import java.io.File

fun main() {
    val input = File("data/day15/input.txt").readLines()
    val resultBreakfast = breakfast(input)
    println(resultBreakfast)
    // I've added the additional disk for lunch manually as a second input file
    val inputLunch = File("data/day15/inputL.txt").readLines()
    val resultLunch = lunch(inputLunch)
    println(resultLunch)
}

data class Disk(val id: Int, val size: Int, val position: Int) {

    fun checkTime(time: Int) = (time + id + position) % size == 0

    companion object {
        fun fromString(s: String): Disk {
            val id = s.substringAfter("Disc #").substringBefore(" has").toInt()
            val size = s.substringAfter("has ").substringBefore(" positions").toInt()
            val position = s.substringAfter("position ").substringBefore(".").toInt()
            return Disk(id, size, position)
        }
    }
}

fun breakfast(input: List<String>) : Int {
    val disks = input.map { Disk.fromString(it) }
    repeat(Int.MAX_VALUE) {index ->
        val done = disks.all { it.checkTime(index) }
        if (done) return index
    }
    error("no match")
}

fun lunch(input: List<String>) : Int {
    val disks = input.map { Disk.fromString(it) }
    repeat(Int.MAX_VALUE) {index ->
        val done = disks.all { it.checkTime(index) }
        if (done) return index
    }
    error("no match")
}
package day14

import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val salt = "ahsbgdzn"
    val saltSample = "abc"
    //breakfast(salt)
    lunch(salt)
}

fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun md5repeat(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    var mdr = input
    repeat(2016) {
        mdr = BigInteger(1, md.digest(mdr.toByteArray())).toString(16).padStart(32, '0')
    }
    return mdr
}

fun firstThree(hash: String): Char? {
    val windows = hash.windowed(3, 1)
    val threes = windows.firstOrNull {
        it[0] == it[1] && it[1] == it[2]
    }
    return if (threes != null) threes[0] else null
}

fun collectFives(hash: String): List<Char>? {
    val windows = hash.windowed(5, 1)
    val fives = windows.filter {
        it[0] == it[1] && it[1] == it[2] && it[2] == it[3] && it[3] == it[4]
    }.map { it[0] }
    return if (fives.isNotEmpty()) fives else null
}

fun findThrees(salt: String): List<Pair<Char, Int>> {
    return (0 until 100000).mapNotNull { n ->
        val hash = md5(salt + n)
        val three = firstThree(hash)
        if (three != null) Pair(three, n) else null
    }.toList()
}

fun findThreesLunch(salt: String): List<Pair<Char, Int>> {
    return (0 until 30000).mapNotNull { n ->
        val h = md5(salt + n)
        val hash = md5repeat(h)
        val three = firstThree(hash)
        if (three != null) Pair(three, n) else null
    }.toList()
}

fun findFives(salt: String): List<Pair<List<Char>, Int>> {
    return (0 until 100000).mapNotNull { n ->
        val hash = md5(salt + n)
        val fives = collectFives(hash)
        if (fives != null) Pair(fives, n) else null
    }.toList()
}

fun findFivesLunch(salt: String): List<Pair<List<Char>, Int>> {
    return (0 until 30000).mapNotNull { n ->
        val h = md5(salt + n)
        val hash = md5repeat(h)
        val fives = collectFives(hash)
        if (fives != null) Pair(fives, n) else null
    }.toList()
}

fun List<Pair<List<Char>, Int>>.isMatch(c: Char, start: Int): Boolean {
    val slize = this.filter { it.second in start + 1 .. start + 1000 }
    val match = slize.any { c in it.first }
    return match
}

fun breakfast(salt: String) {
    val threes = findThrees(salt)
    val fives = findFives(salt)
    val keys = threes.filter { three ->
        fives.isMatch(three.first, three.second)
    }
    println(keys[63])
}

fun lunch(salt: String) {
    val threes = findThreesLunch(salt)
    val fives = findFivesLunch(salt)
    val keys = threes.filter { three ->
        fives.isMatch(three.first, three.second)
    }
    println(keys[63])
}
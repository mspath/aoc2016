package day14

import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val salt = "ahsbgdzn"
    val saltSample = "abc"
    breakfast(saltSample)
}

fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun firstThree(hash: String): Char? {
    val windows = hash.windowed(3, 1)
    val threes = windows.firstOrNull {
        it[0] == it[1] && it[1] == it[2]
    }
    return if (threes != null) threes[0] else null
}

fun firstFive(hash: String): Char? {
    val windows = hash.windowed(5, 1)
    val fives = windows.firstOrNull {
        it[0] == it[1] && it[1] == it[2] && it[2] == it[3] && it[3] == it[4]
    }
    return if (fives != null) fives[0] else null
}

fun findThrees(salt: String): List<Pair<Char, Int>> {
    return (0 until 100000).mapNotNull { n ->
        val hash = md5(salt + n)
        val three = firstThree(hash)
        if (three != null) Pair(three, n) else null
    }.toList()
}

fun findFives(salt: String): List<Pair<Char, Int>> {
    return (0 until 100000).mapNotNull { n ->
        val hash = md5(salt + n)
        val five = firstFive(hash)
        if (five != null) Pair(five, n) else null
    }.toList()
}

// incomplete
fun breakfast(salt: String) {
    val threes = findThrees(salt)
    //println(threes)
    val fives = findFives(salt)
    //println(fives)

    val keys = fives.flatMap { five ->
        val locks = threes.filter { three ->
            three.second in five.second - 1000 .. five.second && three.first == five.first
        }
        locks
    }.map { it.second }.toSortedSet().toList()
    println(keys[65])
}
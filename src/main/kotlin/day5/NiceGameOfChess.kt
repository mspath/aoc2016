package day5

import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val doorId = "uqwqemis"
    breakfast(doorId)
    lunch(doorId)
}

fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1,
        md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun breakfast(doorId: String) {
    var index = 0
    var password = ""
    while (true) {
        val hash = md5(doorId + index)
        if (hash.startsWith("00000")) {
            password += hash[5]
            if (password.length == 8) break
        }
        index++
    }
    println(password)
}

fun lunch(doorId: String) {
    var index = 0
    val password: MutableMap<Char, Char> = mutableMapOf()
    while (true) {
        val hash = md5(doorId + index)
        if (hash.startsWith("00000")) {
            val position = hash[5]
            val value = hash[6]
            if (position in "01234567" && !password.containsKey(position)) {
                password[position] = value
                if (password.size == 8) break
            }
        }
        index++
    }
    val result = password.toSortedMap().map { it.value }.joinToString("")
    println(result)
}

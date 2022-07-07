package day5

import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val sampleId = "abc"
    val doorId = "uqwqemis"
    breakfast(doorId)
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

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

// checks if the unique chars are the exact same in a string, regardless of order
fun String.charsAreTheSame(stringToMatch: String): Boolean {
    return this.toList().containsAll(stringToMatch.toList()) && this.length == stringToMatch.length
}

fun get2DMutableIntList(input: List<String>): MutableList<MutableList<Int>> {
    val intList = mutableListOf<MutableList<Int>>()
    input.forEach {
        val thisLine = mutableListOf<Int>()
        it.forEach { char ->
            thisLine.add(char.toString().toInt())
        }
        intList.add(thisLine)
    }
    return intList
}

fun getIntList(stringList: List<String>): List<Int> {
    val intList = mutableListOf<Int>()
    stringList.forEach {
        intList.add(it.toInt())
    }

    return intList
}

// helpers
fun sumVals(valList: List<Int>): Int {
    var total = 0
    valList.forEach {
        total += it
    }

    return total
}

// helpers
fun sumValsString(valList: List<String>): Int {
    val intList = mutableListOf<Int>()

    valList.forEach {
        intList.add(it.toInt())
    }

    return sumVals(intList)
}


fun flipBinary(binaryString: String): String {
    val flippedBinary = java.lang.StringBuilder()

    binaryString.forEach {
        if (it == '0') flippedBinary.append('1') else flippedBinary.append('0')
    }

    return flippedBinary.toString()
}

fun String.binaryToDecimal(): Int = Integer.parseInt(this, 2)

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

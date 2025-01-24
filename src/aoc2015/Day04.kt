
fun main() {

    data class Coordinates(val xPos: Int, val yPos: Int) {}

    fun part1(input: List<String>): Int {
        val secretKey = input.first()

        // iterate through every int until we find the first with five leading zeros
        var smallestValue = 0
        while (true) {
            val md5 = (secretKey + smallestValue.toString()).toMD5()
            val firstFiveDigits = md5.substring(0, 5)
            if (firstFiveDigits == "00000") {
                break
            } else smallestValue++
        }

        return smallestValue
    }

    // i'm surprised this works - I figured the time complexity of the iterative approach would be too much,
    // maybe lucky input? I don't know enough about md5 hashing to know if there is a trick here.
    fun part2(input: List<String>): Int {
        val secretKey = input.first()

        // iterate through every int until we find the first with six leading zeros
        var smallestValue = 0
        while (true) {
            val md5 = (secretKey + smallestValue.toString()).toMD5()
            val firstSixDigits = md5.substring(0, 6)
            if (firstSixDigits == "000000") {
                break
            } else smallestValue++
        }

        return smallestValue
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("aoc2015/Day04_test1")
    check(part1(testInput1) == 609043)

    // print solutions
    val input = readInput("aoc2015/Day04")
    println(part1(input))
    println(part2(input))
}
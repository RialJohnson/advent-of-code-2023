fun main() {
    fun getRating(input: List<String>, takeDominantBit: Boolean): Int {
        var trimmedInput = input
        var index = 0

        while (trimmedInput.size > 1) {
            var zeroCount = 0
            var oneCount = 0
            var dominantBit: Char

            // find out if 0 or 1 is dominant for this position
            trimmedInput.forEach {
                if (it[index] == '0') zeroCount++
                if (it[index] == '1') oneCount++
            }

            dominantBit = if (zeroCount > oneCount) '0' else '1'

            // flip the dominant bit if we want to take the non-dominant bit
            // this works because if we don't want the dominant bit, we also change the value of the tiebreak bit
            if (!takeDominantBit) dominantBit = if (dominantBit == '0') '1' else '0'

            // only keep the strings that contain the dominant bit at this position
            trimmedInput = trimmedInput.filter {
                it[index] == dominantBit
            }

            index++
        }

        return trimmedInput.first().binaryToDecimal()
    }

    fun part1(input: List<String>): Int {
        val gammaRate: StringBuilder = java.lang.StringBuilder()
        val maxIndex = input.first().length - 1

        for (i in 0..maxIndex) {
            var zeroCount = 0
            var oneCount = 0
                input.forEach {
                    if (it[i] == '0') zeroCount++
                    if (it[i] == '1') oneCount++
                }
            if (zeroCount >= oneCount) gammaRate.append("0") else gammaRate.append("1")
        }

        val epsilonRate = flipBinary(gammaRate.toString())
        val gammaInt = gammaRate.toString().binaryToDecimal()
        val epsilonInt = epsilonRate.binaryToDecimal()

        return gammaInt * epsilonInt
    }

    fun part2(input: List<String>): Int {
        val oxygenGenRating = getRating(input, true)
        val scrubberRating = getRating(input, false)

        return oxygenGenRating * scrubberRating
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    // print solutions
    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

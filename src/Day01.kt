const val ONE = "one"
const val TWO = "two"
const val THREE = "three"
const val FOUR = "four"
const val FIVE = "five"
const val SIX = "six"
const val SEVEN = "seven"
const val EIGHT = "eight"
const val NINE = "nine"

fun main() {

    fun String.textNumToDigitConversion(): Int {
        return when (this) {
            ONE -> 1
            TWO -> 2
            THREE -> 3
            FOUR -> 4
            FIVE -> 5
            SIX -> 6
            SEVEN -> 7
            EIGHT -> 8
            NINE -> 9
            else -> -1
        }
    }

    fun part1(input: List<String>): Int {
        var calibrationValuesSum = 0

        // go through each line of input
        input.forEach { amendedCalibrationValues ->
            // extract only the digits into their own string
            var digitsOnly = ""
            amendedCalibrationValues.forEach { char ->
                if (char.isDigit()) digitsOnly += char
            }
            // combine the first and last digits into their own string
            val calibrationValue = digitsOnly.first().toString() + digitsOnly.last().toString()
            calibrationValuesSum += calibrationValue.toInt() // sum them
        }

        return calibrationValuesSum
    }

    fun part2(input: List<String>): Int {
        var calibrationValuesSum = 0

        val matchList = listOf(ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE)

        input.forEach { amendedCalibrationValues ->

            // find and map the locations of all the text numbers on this line
            val startingIndexMap = mutableMapOf<Int, Int>()
            matchList.forEach { matchingNum ->
                val list = Regex(matchingNum).findAll(amendedCalibrationValues).map { it.range.start }.toList()
                list.forEach {
                    startingIndexMap[it] = matchingNum.textNumToDigitConversion() // maps the starting index and the string value of the match
                }
            }

            // find and map the locations of all the digits on this line
            amendedCalibrationValues.forEachIndexed { index, char ->
                if (char.isDigit()) startingIndexMap[index] = char.toString().toInt()
            }

            // find the first and last map entries and combine them for calibration value
            val sortedMap = startingIndexMap.toSortedMap()
            println(sortedMap)
            val calibrationValue = "${sortedMap[sortedMap.firstKey()]}${sortedMap[sortedMap.lastKey()]}"
            println(calibrationValue)
            calibrationValuesSum += calibrationValue.toInt() // sum them

        }
        return calibrationValuesSum
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day01_test1")
    val testInput2 = readInput("Day01_test2")
    check(part1(testInput1) == 142)
    check(part2(testInput2) == 281)

    // print solutions
    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

fun main() {

    fun part1(input: List<String>): Int {
        var totalWrappingPaper = 0

        // split up dimensions
        input.forEach { dimensions ->
            val splitDimensions = dimensions.split("x")
            val length = splitDimensions[0].toInt()
            val width = splitDimensions[1].toInt()
            val height = splitDimensions[2].toInt()

            val surfaceArea = (2 * length * width) + (2 * width * height) + (2 * height * length)
            val sortedLengths = listOf(length, width, height).sorted()
            val slack = sortedLengths[0] * sortedLengths[1]

            totalWrappingPaper += (surfaceArea + slack)
        }

        return totalWrappingPaper
    }

    fun part2(input: List<String>): Int {
        var totalWrappingPaper = 0

        // split up dimensions
        input.forEach { dimensions ->
            val splitDimensions = dimensions.split("x")
            val length = splitDimensions[0].toInt()
            val width = splitDimensions[1].toInt()
            val height = splitDimensions[2].toInt()

            val sortedLengths = listOf(length, width, height).sorted()
            val ribbonToWrapGift = (sortedLengths[0] + sortedLengths[1]) * 2 // the smallest perimeter around any one face
            val ribbonForBow = length * width * height // cubic volume

            totalWrappingPaper += (ribbonToWrapGift + ribbonForBow)
        }

        return totalWrappingPaper
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("aoc2015/Day02_test1")
    val testInput2 = readInput("aoc2015/Day02_test1")
    check(part1(testInput1) == 58)
    check(part2(testInput2) == 34)

    // print solutions
    val input = readInput("aoc2015/Day02")
    println(part1(input))
    println(part2(input))
}
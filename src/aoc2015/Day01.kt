
fun main() {

    fun part1(input: List<String>): Int {
        val sequence = input.first()
        val openCount = sequence.count { it == '(' }
        val closeCount = sequence.count { it == ')' }

        return openCount - closeCount
    }

    fun part2(input: List<String>): Int {
        val sequence = input.first()
        var count = 0

        sequence.forEachIndexed { position, character ->
            if (character == '(') count++ else count--

            if (count < 0) return position + 1
        }

        return -1 // should never happen with test endpoint
    }

    /* // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day01_test1")
    val testInput2 = readInput("Day01_test2")
    check(part1(testInput1) == 142)
    check(part2(testInput2) == 281)

     */

    // print solutions
    val input = readInput("aoc2015/Day01")
    println(part1(input))
    println(part2(input))
}
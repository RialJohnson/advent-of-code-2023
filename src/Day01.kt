fun main() {

    fun part1(input: List<String>): Int {
        var count = 0
        var prev = Int.MAX_VALUE

        input.forEach {
            if (it.toInt() > prev) count++
            prev = it.toInt()
        }

        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        var prevList = listOf<Int>()
        val curList = mutableListOf<Int>()

        input.forEach {

            // don't remove unless list of 3 is full
            if (curList.size == 3) curList.removeAt(0)
            curList.add(it.toInt())

            if (curList.size == 3 && prevList.size == 3) {
                if (sumVals(curList.toList()) > sumVals(prevList.toList())) count++
            }

            prevList = curList.toList()
        }
        return count
    }

    // alternate part 2 solution that uses fancy kotlin windowed() function
    fun part2Windowed(input: List<String>): Int {
        var count = 0
        var prevList = listOf<String>()

        // creates a list of lists of group of 3
        val groupsOf3 = input.windowed(3, 1)

        groupsOf3.forEach {
            if (prevList.isNotEmpty()) {
                if (sumValsString(it) > sumValsString(prevList)) count++
            }
            prevList = it // rest previous list
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)
    check(part2Windowed(testInput) == 5)

    // print solutions
    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
    println(part2Windowed(input))
}

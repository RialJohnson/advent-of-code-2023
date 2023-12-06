
fun main() {

    fun part1(input: List<String>): Int {
        var recordBeatersProduct = 1

        val timeDistanceMap = mutableMapOf<Int, Int>()

        val times = input.first().split(": ").last().trim().splitToIntList()
        val distances = input.last().split(": ").last().trim().splitToIntList()

        times.forEachIndexed { index, time ->
            timeDistanceMap.put(time, distances[index])
        }

        timeDistanceMap.forEach {
            var winningCombos = 0

            val time = it.key
            val recordDistance = it.value

            for (holdTime in 0..time) {
                val speed = holdTime
                val travelTime = time - holdTime

                val distanceTraveled = speed * travelTime

                if (distanceTraveled > recordDistance) winningCombos++
            }
            if (winningCombos > 0) recordBeatersProduct *= winningCombos
        }

        return recordBeatersProduct
    }

    fun part2(input: List<String>): Long {
        var recordBeatersProduct = 1L

        val time = input.first().split(": ").last().trim().splitToIntList().mergeToOneString().toLong()
        val recordDistance = input.last().split(": ").last().trim().splitToIntList().mergeToOneString().toLong()
        var winningCombos = 0L

        for (holdTime in 0..time) {
            val speed = holdTime
            val travelTime = time - holdTime

            val distanceTraveled = speed * travelTime

            if (distanceTraveled > recordDistance) winningCombos++
        }
        if (winningCombos > 0) recordBeatersProduct *= winningCombos

        return winningCombos
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day06_test")
    val testInput2 = readInput("Day06_test")
    check(part1(testInput1) == 288)
    check(part2(testInput2) == 71503L)

    // print solutions
    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
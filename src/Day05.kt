fun main() {
    fun parseInput(input: List<String>): List<List<Int>> {
        val coords = mutableListOf<List<Int>>()

        input.forEach{ line ->
            val stripped = line.replace(" -> ", ",")
            val valList = stripped.split(",")
            val set = mutableListOf<Int>()
            valList.forEach {
                set.add(it.toInt())
            }
            coords.add(set)
        }

        return coords
    }

    fun initializeGraph(maxValue: Int): MutableList<MutableList<Int>> {
        val graph = mutableListOf<MutableList<Int>>()

        for (i in 0..maxValue) {
            val row = mutableListOf<Int>()
            for (j in 0..maxValue) {
                row.add(0)
            }
            graph.add(row)
        }

        return graph
    }

    fun countGraph(graph: List<List<Int>>): Int {
        var intersectionCount = 0
        graph.forEach { line ->
            line.forEach {
                if (it > 1) intersectionCount++
            }
        }

        return intersectionCount
    }

    fun part1(input: List<String>): Int {
        val coords = parseInput(input)

        // this will determine the dimensions of my initial graph
        val maxValue = coords.maxByOrNull {
            it.maxOrNull() ?: 0
        }?.maxOrNull() ?: 0

        val graph = initializeGraph(maxValue)

        coords.forEach { line ->
            val pairs = line.chunked(2) // splits coords into pairs

            val xVals = listOf(pairs[0][0], pairs[1][0])
            val yVals = listOf(pairs[0][1], pairs[1][1])

            // if x vals are the same (vertical lines)
            if (xVals.first() == xVals.last()) {
                val xVal = xVals.first() // take either they are the same
                val min = yVals.minOrNull() ?: 0
                val max = yVals.maxOrNull() ?: 0

                for (i in min..max) {
                    graph[i][xVal]++
                }
            }

            // if y vals are the same (horizontal lines)
            else if (yVals.first() == yVals.last()) {
                val yVal = yVals.first() // take either they are the same
                val min = xVals.minOrNull() ?: 0
                val max = xVals.maxOrNull() ?: 0

                for (i in min..max) {
                    graph[yVal][i]++
                }
            }
        }
        return countGraph(graph)
    }

    fun part2(input: List<String>): Int {
        val coords = parseInput(input)

        // this will determine the dimensions of my initial graph
        val maxValue = coords.maxByOrNull {
            it.maxOrNull() ?: 0
        }?.maxOrNull() ?: 0

        val graph = initializeGraph(maxValue)

        coords.forEach { line ->
            val pairs = line.chunked(2) // splits coords into pairs

            val xVals = listOf(pairs[0][0], pairs[1][0])
            val yVals = listOf(pairs[0][1], pairs[1][1])
            val xDifference = kotlin.math.abs(xVals.first() - xVals.last())
            val yDifference = kotlin.math.abs(yVals.first() - yVals.last())

            // if x vals are the same (vertical lines)
            if (xVals.first() == xVals.last()) {
                val xVal = xVals.first() // take either they are the same
                val min = yVals.minOrNull() ?: 0
                val max = yVals.maxOrNull() ?: 0

                for (i in min..max) {
                    graph[i][xVal]++
                }
            }

            // if y vals are the same (horizontal lines)
             else if (yVals.first() == yVals.last()) {
                val yVal = yVals.first() // take either they are the same
                val min = xVals.minOrNull() ?: 0
                val max = xVals.maxOrNull() ?: 0

                for (i in min..max) {
                    graph[yVal][i]++
                }
            }

            // if abs val of difference in x and y vals are the same (diagonal lines)
            else if (xDifference == yDifference) {
                val xMin = xVals.minOrNull() ?: 0
                val xMax = xVals.maxOrNull() ?: 0
                val yMin = yVals.minOrNull() ?: 0
                val yMax = yVals.maxOrNull() ?: 0

                // this will match with the yMin or yMax
                val yStart = pairs.find {
                    it.first() == xMin // where the x vals match
                }?.last() ?: throw Exception("ain't find it")

                // if yVal is increasing
                if (yStart == yMin) {
                    var yVal = yStart
                    for (xVal in xMin..xMax) {
                        graph[yVal][xVal]++
                        yVal++
                    }
                }

               // if yVal is decreasing
               else if (yStart == yMax) {
                    var yVal = yStart
                    for (xVal in xMin..xMax) {
                        graph[yVal][xVal]++
                        yVal--
                    }
                }
            }
        }
        return countGraph(graph)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    // print solutions
    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun main() {
    data class Point(val height: Int, val inBasin: Boolean, var visited: Boolean, val xIndex: Int, val yIndex: Int)

    fun get2DintArray(input: List<String>): List<List<Point>> {
        val map = mutableListOf<MutableList<Point>>()

        input.forEachIndexed { yIndex, line ->
            val intLine = mutableListOf<Point>()
            line.forEachIndexed { xIndex, it ->
                val asInt = it.toString().toInt()
                val isBasin = asInt != 9
                intLine.add(Point(asInt, isBasin, false, xIndex, yIndex))
            }
            map.add(intLine)
        }
        return map
    }

    fun recursiveFindNeighbors(thisPoint: Point, matches: HashSet<Point>, heightMap: List<List<Point>>): HashSet<Point> {
        // recursive tail
        if (matches.contains(thisPoint)) return matches
        else matches.add(thisPoint)

        // check above
        if (thisPoint.yIndex > 0) {
            val above = heightMap[thisPoint.yIndex - 1][thisPoint.xIndex]
            if (above.inBasin && !above.visited) {
                heightMap[thisPoint.yIndex][thisPoint.xIndex].visited = true
                recursiveFindNeighbors(above, matches, heightMap)
            }
        }

        // check below
        if (thisPoint.yIndex < heightMap.size - 1) {
            val below = heightMap[thisPoint.yIndex + 1][thisPoint.xIndex]
            if (below.inBasin && !below.visited) {
                heightMap[thisPoint.yIndex][thisPoint.xIndex].visited = true
                recursiveFindNeighbors(below, matches, heightMap)
            }
        }

        // check left
        if (thisPoint.xIndex > 0) {
            val left = heightMap[thisPoint.yIndex][thisPoint.xIndex - 1]
            if (left.inBasin && !left.visited) {
                heightMap[thisPoint.yIndex][thisPoint.xIndex].visited = true
                recursiveFindNeighbors(left, matches, heightMap)
            }
        }

        // check right
        if (thisPoint.xIndex < heightMap.first().size - 1) {
            val right = heightMap[thisPoint.yIndex][thisPoint.xIndex + 1]
            if (right.inBasin && !right.visited) {
                heightMap[thisPoint.yIndex][thisPoint.xIndex].visited = true
                recursiveFindNeighbors(right, matches, heightMap)
            }
        }
        return matches // should never get hit
    }

    fun part1(input: List<String>): Int {
        val heightMap = get2DintArray(input)

        val riskFactorList = mutableListOf<Int>()

        heightMap.forEachIndexed { yIndex, line ->
            line.forEachIndexed { xIndex, point ->
                var lowest = true

                // check above
                if (yIndex > 0 && heightMap[yIndex][xIndex].height >= heightMap[yIndex - 1][xIndex].height) lowest = false

                // check below
                else if (yIndex < heightMap.size - 1 && heightMap[yIndex][xIndex].height >= heightMap[yIndex + 1][xIndex].height) lowest = false

                // check left
                else if (xIndex > 0 && heightMap[yIndex][xIndex].height >= heightMap[yIndex][xIndex - 1].height) lowest = false

                // check right
                else if (xIndex < line.size - 1 && heightMap[yIndex][xIndex].height >= heightMap[yIndex][xIndex + 1].height) lowest = false

                if (lowest) riskFactorList.add(heightMap[yIndex][xIndex].height)
            }
        }

        val count = riskFactorList.fold(0) { total, value ->
            total + value + 1
        }

        return count
    }

    fun part2(input: List<String>): Int {
        var heightMap = get2DintArray(input)

        val basins = mutableListOf<Int>()
        heightMap.forEachIndexed { yIndex, line ->
            line.forEachIndexed { xIndex, point ->
                if (point.inBasin && !point.visited) {
                    val allNeighbors = recursiveFindNeighbors(heightMap[yIndex][xIndex], hashSetOf(), heightMap)
                    if (allNeighbors.size != 1) basins.add(allNeighbors.size)
                }
            }
        }

        val threeLargest = basins.sortedDescending().take(3)
        val count = threeLargest.fold(1) { mul, value ->
            mul * value
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    // print solutions
    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

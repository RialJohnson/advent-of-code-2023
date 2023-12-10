enum class PipeType {
    NORTH_SOUTH,
    EAST_WEST,
    NORTH_EAST,
    NORTH_WEST,
    SOUTH_WEST,
    SOUTH_EAST,
    GROUND,
    START
}

enum class TravelDirection {
    NORTH,
    EAST,
    WEST,
    SOUTH
}

fun main() {
    data class Pipe(val type: PipeType, val distance: Int = 0)
    data class Tracker(val pipe: Pipe, val row: Int, val col: Int, val cameFrom: TravelDirection)

    val leftPipes = listOf(PipeType.EAST_WEST, PipeType.NORTH_EAST, PipeType.SOUTH_EAST) // pipes that are valid connections looking left
    val upPipes = listOf(PipeType.NORTH_SOUTH, PipeType.SOUTH_WEST, PipeType.SOUTH_EAST)
    val rightPipes = listOf(PipeType.SOUTH_WEST, PipeType.EAST_WEST, PipeType.NORTH_WEST)
    val downPipes = listOf(PipeType.NORTH_SOUTH, PipeType.NORTH_WEST, PipeType.NORTH_EAST)

    fun part1(input: List<String>): Int {
        var furthestDistance = 0

        // get 2d array of chars from input
        val inputAsGrid = input.to2dCharArray()

        var startIndex = Pair(0, 0) // row, col

        // create new grid replacing symbols with pipes
        val pipeGrid = arrayListOf<MutableList<Pipe>>()
        inputAsGrid.forEachIndexed { rowIndex, row ->
            val pipeRow = mutableListOf<Pipe>()
            row.forEachIndexed { colIndex, char ->
                val pipeType = when (char) {
                    '|' -> PipeType.NORTH_SOUTH
                    '-' -> PipeType.EAST_WEST
                    'L' -> PipeType.NORTH_EAST
                    'J' -> PipeType.NORTH_WEST
                    '7' -> PipeType.SOUTH_WEST
                    'F' -> PipeType.SOUTH_EAST
                    'S' -> PipeType.START
                    else -> PipeType.GROUND
                }
                if (pipeType == PipeType.START) startIndex = Pair(rowIndex, colIndex)

                val pipe = Pipe(type = pipeType)
                pipeRow.add(pipe)
            }
            pipeGrid.add(pipeRow)
        }


        val startingPipe = pipeGrid[startIndex.first][startIndex.second]
        var startRow = startIndex.first
        var startCol = startIndex.second
        var startLoopPipe = Tracker(pipe = Pipe(PipeType.START), row = 0, col = 0, cameFrom = TravelDirection.SOUTH) // pipe we start traversing the loop with (after start pipe)
        var endLoopPipe = Tracker(pipe = Pipe(PipeType.START), row = 0, col = 0, cameFrom = TravelDirection.SOUTH) // pipe we end the loop on (before start pipe)

        /*
        pipeGrid.forEach {
            it.forEach {
                print("${it.type} ")
            }
            println()
        }

         */

        // determine the direction to start the loop in
        if (startCol > 0) { // check left of starting pipe
            val leftPipe = pipeGrid[startRow][startCol - 1]
            if (leftPipes.contains(leftPipe.type)) { // check if this is a valid connection
                startLoopPipe = Tracker(leftPipe, startRow, startCol - 1, TravelDirection.EAST)
            }
        }

        if (startRow > 0) { // check up of starting pipe
            val upPipe = pipeGrid[startRow - 1][startCol]
            if (upPipes.contains(upPipe.type)) { // check if this is a valid connection
                if (startLoopPipe.pipe.type == PipeType.START) { // if it hasn't already been set
                    startLoopPipe = Tracker(upPipe, startRow - 1, startCol, TravelDirection.SOUTH)
                } else if (endLoopPipe.pipe.type == PipeType.START) { // if it hasn't already been set
                    endLoopPipe = Tracker(upPipe, startRow - 1, startCol, TravelDirection.SOUTH)
                }
            }
        }

        if (startCol < pipeGrid.first().size - 1) { // check right of starting pipe
            val rightPipe = pipeGrid[startRow][startCol + 1]
            if (rightPipes.contains(rightPipe.type)) { // check if this is a valid connection
                if (startLoopPipe.pipe.type == PipeType.START) { // if it hasn't already been set
                    startLoopPipe = Tracker(rightPipe, startRow, startCol + 1, TravelDirection.WEST)
                } else if (endLoopPipe.pipe.type == PipeType.START) { // if it hasn't already been set
                    endLoopPipe = Tracker(rightPipe, startRow, startCol + 1, TravelDirection.WEST)
                }
            }
        }

        if (startRow < pipeGrid.size - 1) { // check down of starting pipe
            println("$startCol, $startRow")
            val downPipe = pipeGrid[startRow + 1][startCol]
            if (downPipes.contains(downPipe.type)) { // check if this is a valid connection
                if (endLoopPipe.pipe.type == PipeType.START) { // if it hasn't already been set
                    endLoopPipe = Tracker(downPipe, startRow + 1, startCol, TravelDirection.NORTH)
                }
            }
        }

        println("start pipe: $startLoopPipe")
        println("end pipe: $endLoopPipe")


        // traverse the graph
        var currentPipe = startLoopPipe.pipe
        var currentRow = startLoopPipe.row
        var currentCol = startLoopPipe.col
        var cameFrom = startLoopPipe.cameFrom
        var distanceTraveled = 1


        do {

            if (currentPipe.type == PipeType.NORTH_SOUTH) {
                if (cameFrom == TravelDirection.NORTH) {
                    cameFrom = TravelDirection.NORTH // head south
                    currentRow = currentRow + 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++

                } else if (cameFrom == TravelDirection.SOUTH) {
                    cameFrom = TravelDirection.SOUTH // head north
                    currentRow = currentRow - 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++
                }
            } else if (currentPipe.type == PipeType.EAST_WEST) {
                if (cameFrom == TravelDirection.EAST) {
                    cameFrom = TravelDirection.EAST // head west
                    currentCol = currentCol - 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++

                } else if (cameFrom == TravelDirection.WEST) {
                    cameFrom = TravelDirection.WEST // head east
                    currentCol = currentCol + 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++
                }
            } else if (currentPipe.type == PipeType.NORTH_EAST) {
                if (cameFrom == TravelDirection.NORTH) {
                    cameFrom = TravelDirection.WEST // head east
                    currentCol = currentCol + 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++

                } else if (cameFrom == TravelDirection.EAST) {
                    cameFrom = TravelDirection.SOUTH // head north
                    currentRow = currentRow - 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++
                }
            } else if (currentPipe.type == PipeType.NORTH_WEST) {
                if (cameFrom == TravelDirection.NORTH) {
                    cameFrom = TravelDirection.EAST // head west
                    currentCol = currentCol - 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++

                } else if (cameFrom == TravelDirection.WEST) {
                    cameFrom = TravelDirection.SOUTH // head north
                    currentRow = currentRow - 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++
                }
            } else if (currentPipe.type == PipeType.SOUTH_WEST) {
                if (cameFrom == TravelDirection.SOUTH) {
                    cameFrom = TravelDirection.EAST // head west
                    currentCol = currentCol - 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++

                } else if (cameFrom == TravelDirection.WEST) {
                    cameFrom = TravelDirection.NORTH// head south
                    currentRow = currentRow + 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++
                }
            } else if (currentPipe.type == PipeType.SOUTH_EAST) {
                if (cameFrom == TravelDirection.SOUTH) {
                    cameFrom = TravelDirection.WEST // head east
                    currentCol = currentCol + 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++

                } else if (cameFrom == TravelDirection.EAST) {
                    cameFrom = TravelDirection.NORTH// head south
                    currentRow = currentRow + 1
                    currentPipe = pipeGrid[currentRow][currentCol]
                    distanceTraveled++
                }
            }
        } while (currentPipe.type != PipeType.START)

        furthestDistance = if (distanceTraveled % 2 == 0) distanceTraveled / 2 else (distanceTraveled / 2) + 1

        return furthestDistance
    }

    // TODO
    fun part2(input: List<String>): Int {
        var previousValueSum = 0

        return previousValueSum
    }


    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day10_test")
    val testInput2 = readInput("Day10_test")
    check(part1(testInput1) == 8)
    // check(part2(testInput2) == 2)

    // print solutions
    val input = readInput("Day10")
    println(part1(input))
    // println(part2(input))
}
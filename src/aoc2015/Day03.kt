
fun main() {

    data class Coordinates(val xPos: Int, val yPos: Int) {}

    fun part1(input: List<String>): Int {
        val deliverySequence = input.first()

        // tracks where santa currently is
        var xPos = 0
        var yPos = 0
        val startingCoordinates = Coordinates(xPos, yPos)
        val presentMap = mutableMapOf<Coordinates, Int>() // tracks how many presents were delivered to each coordinate
        presentMap[startingCoordinates] = 1

        deliverySequence.forEach {
            if (it == 'v') {
                xPos -= 1
            } else if (it == '^') {
                xPos += 1
            } else if (it == '<') {
                yPos -= 1
            } else if (it == '>') {
                yPos += 1
            } else throw Exception("$it is not a valid character input")

            // check how many presents have been delivered to this house and add one
            val currentPresentsAtThisHouse = presentMap[Coordinates(xPos, yPos)] ?: 0
            presentMap[Coordinates(xPos, yPos)] = currentPresentsAtThisHouse + 1
        }

        // for part one, we just want to know how many houses have at least one present (num entires in the map)
        return presentMap.size
    }

    fun part2(input: List<String>): Int {
        val deliverySequence = input.first()

        // tracks where santa currently is
        var santaXPos = 0
        var santaYPos = 0
        var roboXPos = 0
        var roboYPos = 0
        val startingCoordinates = Coordinates(santaXPos, santaYPos)
        val presentMap = mutableMapOf<Coordinates, Int>() // tracks how many presents were delivered to each coordinate
        presentMap[startingCoordinates] = 1

        deliverySequence.forEachIndexed { index, direction ->
            var xPos: Int
            var yPos: Int

            // santa uses odd instructions, robo uses even instructions
            if (index % 2 != 0) {
                xPos = santaXPos
                yPos = santaYPos
            } else {
                xPos = roboXPos
                yPos = roboYPos
            }

            if (direction == 'v') {
                xPos -= 1
            } else if (direction == '^') {
                xPos += 1
            } else if (direction == '<') {
                yPos -= 1
            } else if (direction == '>') {
                yPos += 1
            } else throw Exception("$direction is not a valid character input")

            // check how many presents have been delivered to this house and add one
            val currentPresentsAtThisHouse = presentMap[Coordinates(xPos, yPos)] ?: 0
            presentMap[Coordinates(xPos, yPos)] = currentPresentsAtThisHouse + 1

            // set the current coordinates for santa or robo
            if (index % 2 != 0) {
                santaXPos = xPos
                santaYPos = yPos
            } else {
                roboXPos = xPos
                roboYPos = yPos
            }
        }

        // we just want to know how many houses have at least one present (num entires in the map)
        return presentMap.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("aoc2015/Day03_test1")
    check(part1(testInput1) == 4)
    check(part2(testInput1) == 3)

    // print solutions
    val input = readInput("aoc2015/Day03")
    println(part1(input))
    println(part2(input))
}
fun main() {

    fun part1(input: List<String>): Int {
        var horizontalPosition = 0
        var depth = 0

        input.forEach {
            val info = it.split(" ")
            val amount = info[1].toInt()
            val direction = info[0]

            if (direction == "forward") horizontalPosition += amount
            if (direction == "down") depth += amount
            if (direction == "up") depth -= amount
        }

        return horizontalPosition * depth
    }

    fun part2(input: List<String>): Int {
        var horizontalPosition = 0
        var aim = 0
        var depth = 0

        input.forEach {
            val info = it.split(" ")
            val amount = info[1].toInt()
            val direction = info[0]

            if (direction == "forward") {
                horizontalPosition += amount
                depth += (aim * amount)
            }
            if (direction == "down") aim += amount
            if (direction == "up") aim -= amount
        }

        return horizontalPosition * depth
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    // print solutions
    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

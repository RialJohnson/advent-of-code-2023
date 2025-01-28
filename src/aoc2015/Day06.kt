
data class Light (var on: Boolean = false, var brightness: Int = 0)
data class Coordinates(val xPos: Int, val yPos: Int)

const val ON = "turn on "
const val OFF = "turn off "
const val TOGGLE = "toggle "

fun main() {
    fun part1(input: List<String>): Int {

        // create map of lights that are off
        val lights = mutableMapOf<Coordinates, Light>()
        for (y in 0..999) {
            for (x in 0..999) {
                lights[Coordinates(x, y)] = Light(false)
            }
        }

        // parses input and flicks off the lights by iterating through the instructions and map lookups
        input.forEach { line ->
            val lightStatus = if (line.contains(ON)) ON else if (line.contains(OFF)) OFF else TOGGLE
            val instruction = line.split(lightStatus).last()
            val coords = instruction.split(" through ")
            val startLight = coords.first()
            val endLight = coords.last()
            val startX = startLight.split(",").first().toInt()
            val startY = startLight.split(",").last().toInt()
            val endX = endLight.split(",").first().toInt()
            val endY = endLight.split(",").last().toInt()

            for (y in startY..endY) {
                for (x in startX..endX) {
                    if (lightStatus == ON) {
                        lights[Coordinates(x, y)] = Light(true)
                    } else if (lightStatus == OFF) {
                        lights[Coordinates(x, y)] = Light(false)
                    } else {
                        val previousLightStatus = lights[Coordinates(x, y)]?.on ?: false
                        lights[Coordinates(x, y)] = Light(!previousLightStatus)
                    }
                }
            }
        }

        val lightOnCount = lights.count { (_, light) -> light.on }
        return lightOnCount
    }

    fun part2(input: List<String>): Int {

        // create map of lights that are off
        val lights = mutableMapOf<Coordinates, Light>()
        for (y in 0..999) {
            for (x in 0..999) {
                lights[Coordinates(x, y)] = Light(on = false, brightness = 0)
            }
        }

        // parses input and flicks off the lights by iterating through the instructions and map lookups
        input.forEach { line ->
            val lightStatus = if (line.contains(ON)) ON else if (line.contains(OFF)) OFF else TOGGLE
            val instruction = line.split(lightStatus).last()
            val coords = instruction.split(" through ")
            val startLight = coords.first()
            val endLight = coords.last()
            val startX = startLight.split(",").first().toInt()
            val startY = startLight.split(",").last().toInt()
            val endX = endLight.split(",").first().toInt()
            val endY = endLight.split(",").last().toInt()

            for (y in startY..endY) {
                for (x in startX..endX) {
                    if (lightStatus == ON) {
                        val previousBrightness = lights[Coordinates(x, y)]?.brightness ?: 0
                        lights[Coordinates(x, y)] = Light(brightness = previousBrightness + 1)
                    } else if (lightStatus == OFF) {
                        val previousBrightness = lights[Coordinates(x, y)]?.brightness ?: 0
                        if (previousBrightness != 0) { // minimum brightness is zero
                            lights[Coordinates(x, y)] = Light(brightness = previousBrightness - 1)
                        }
                    } else {
                        val previousBrightness = lights[Coordinates(x, y)]?.brightness ?: 0
                        lights[Coordinates(x, y)] = Light(brightness = previousBrightness + 2)
                    }
                }
            }
        }

        var totalBrightness = 0
        lights.forEach { (_, light) -> totalBrightness += light.brightness }
        return totalBrightness
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("aoc2015/Day06_test1")
    check(part1(testInput1) == 4)

    // print solutions
    val input = readInput("aoc2015/Day06")
    println(part1(input))
    println(part2(input))
}
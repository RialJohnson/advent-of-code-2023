fun main() {
    data class Octopus(val energyLevel: Int, val alreadyFlashed: Boolean)

    fun part1(input: List<String>): Int {
        var flashCount = 0
        val octopiEnergyLevel = get2DMutableIntList(input)
        val octopi = mutableListOf<MutableList<Octopus>>()
        octopiEnergyLevel.forEach { line ->
            val octopiLine = mutableListOf<Octopus>()
            line.forEach {
                octopiLine.add(Octopus(it, false))
            }
            octopi.add(octopiLine)
        }

        // do 100 steps
        for (step in 1..100) {
            // first just increase every energy level by 1
            octopi.forEachIndexed { yIndex, line ->
                line.forEachIndexed { xIndex, octopus ->
                    octopi[yIndex][xIndex] = Octopus(octopus.energyLevel + 1, false)
                }
            }

            // keep looping through the entire thing until there are no flashes (inefficient but only 10x10)
            var anyFlashes: Boolean
            do {
                anyFlashes = false
                // check for flashes
                octopi.forEachIndexed { yIndex, line ->
                    line.forEachIndexed { xIndex, octopus ->
                        // it flashed!
                        if (octopus.energyLevel > 9) {
                            flashCount++
                            octopi[yIndex][xIndex] = Octopus(0, true)
                            anyFlashes = true

                            // increment all neighbors if flashed

                            // top left
                            try {
                                val thisOctopus = octopi[yIndex - 1][xIndex - 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex - 1][xIndex - 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // top
                            try {
                                val thisOctopus = octopi[yIndex - 1][xIndex]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex - 1][xIndex] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // top right
                            try {
                                val thisOctopus = octopi[yIndex - 1][xIndex + 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex - 1][xIndex + 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // left
                            try {
                                val thisOctopus = octopi[yIndex][xIndex - 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex][xIndex - 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // right
                            try {
                                val thisOctopus = octopi[yIndex][xIndex + 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex][xIndex + 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // bottom left
                            try {
                                val thisOctopus = octopi[yIndex + 1][xIndex - 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex + 1][xIndex - 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // bottom
                            try {
                                val thisOctopus = octopi[yIndex + 1][xIndex]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex + 1][xIndex] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // bottom right
                            try {
                                val thisOctopus = octopi[yIndex + 1][xIndex + 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex + 1][xIndex + 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}
                        }
                    }
                }
            } while (anyFlashes)
        }

        return flashCount
    }

    // calculate first step evey octopus flashes
    fun part2(input: List<String>): Int {
        val octopiEnergyLevel = get2DMutableIntList(input)
        val octopi = mutableListOf<MutableList<Octopus>>()
        octopiEnergyLevel.forEach { line ->
            val octopiLine = mutableListOf<Octopus>()
            line.forEach {
                octopiLine.add(Octopus(it, false))
            }
            octopi.add(octopiLine)
        }

        var allFlashed = false
        var stepCount = 0

        // do 100 steps
        while (!allFlashed) {
            stepCount++

            // first just increase every energy level by 1
            octopi.forEachIndexed { yIndex, line ->
                line.forEachIndexed { xIndex, octopus ->
                    octopi[yIndex][xIndex] = Octopus(octopus.energyLevel + 1, false)
                }
            }

            // keep looping through the entire thing until there are no flashes (inefficient but only 10x10)
            var anyFlashes: Boolean
            do {
                anyFlashes = false
                // check for flashes
                octopi.forEachIndexed { yIndex, line ->
                    line.forEachIndexed { xIndex, octopus ->
                        // it flashed!
                        if (octopus.energyLevel > 9) {
                            octopi[yIndex][xIndex] = Octopus(0, true)
                            anyFlashes = true

                            // increment all neighbors if flashed

                            // top left
                            try {
                                val thisOctopus = octopi[yIndex - 1][xIndex - 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex - 1][xIndex - 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // top
                            try {
                                val thisOctopus = octopi[yIndex - 1][xIndex]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex - 1][xIndex] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // top right
                            try {
                                val thisOctopus = octopi[yIndex - 1][xIndex + 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex - 1][xIndex + 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // left
                            try {
                                val thisOctopus = octopi[yIndex][xIndex - 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex][xIndex - 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // right
                            try {
                                val thisOctopus = octopi[yIndex][xIndex + 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex][xIndex + 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // bottom left
                            try {
                                val thisOctopus = octopi[yIndex + 1][xIndex - 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex + 1][xIndex - 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // bottom
                            try {
                                val thisOctopus = octopi[yIndex + 1][xIndex]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex + 1][xIndex] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}

                            // bottom right
                            try {
                                val thisOctopus = octopi[yIndex + 1][xIndex + 1]
                                if (!thisOctopus.alreadyFlashed) {
                                    octopi[yIndex + 1][xIndex + 1] = Octopus(thisOctopus.energyLevel + 1, false)
                                }
                            } catch (e: Exception) {}
                        }
                    }
                }
            } while (anyFlashes)

            // check if they've all flashed
            var checkAllFlashed = true
            octopi.forEachIndexed { yIndex, line ->
                line.forEachIndexed { xIndex, octopus ->
                    if (!octopus.alreadyFlashed) checkAllFlashed = false
                }
            }

            if (checkAllFlashed) allFlashed = true
        }

        return stepCount
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    // print solutions
    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

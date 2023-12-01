fun main() {
    data class FishGroup(val daysToSpawn: Int, val quantity: Long)

    fun parseInput(input: List<String>): MutableList<Int> {
        val fish = input.first().split(",")
        val intFish = mutableListOf<Int>()

        fish.forEach{
            intFish.add(it.toInt())
        }

        return intFish
    }

    fun parseInputFish(input: List<String>): MutableList<FishGroup> {
        val fish = input.first().split(",")
        val intFish = mutableListOf<FishGroup>()

        fish.forEach{
            intFish.add(FishGroup(it.toInt(), 1)) // add 1 fish with this spawn date to the initial list
        }

        return intFish
    }

    // brute force solution that individually calculates each fish every day
    fun part1(input: List<String>): Int {
        val fish = parseInput(input)

        // simulate reproduction over 80 days
        for (i in 1..80) {
            val fishToAdd = mutableListOf<Int>() // store new fish to add at the end of the day
            fish.forEachIndexed { index, daysToSpawn ->
                // spawn time
                if (daysToSpawn == 0) {
                    fish[index] = 6 // reset spawn cycle
                    fishToAdd.add(8) // add new fish
                } else fish[index]--
            }
            fish.addAll(fishToAdd) // add new fish for the day
        }
        return fish.size
    }

    // better solution that groups fish that do the same thing on a given day
    fun part2(input: List<String>): Long {
        val fishGroups = parseInputFish(input)

        // simulate reproduction over 256 days
        for (i in 1..256) {
            var fishToAdd: Long = 0 // count new fish to add at the end of the day
            fishGroups.forEachIndexed { index, thisFishGroup ->
                // spawn time
                if (thisFishGroup.daysToSpawn == 0) {
                    fishGroups[index] = FishGroup(6, thisFishGroup.quantity) // reset spawn cycle
                    fishToAdd += thisFishGroup.quantity // add new fish
                } else {
                    fishGroups[index] = FishGroup(thisFishGroup.daysToSpawn - 1, thisFishGroup.quantity)
                }
            }
            fishGroups.add(FishGroup(8, fishToAdd)) // add new fish group for the day
        }

        // count the fish
        var count: Long = 0
        fishGroups.forEach {
            count += it.quantity
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    // print solutions
    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

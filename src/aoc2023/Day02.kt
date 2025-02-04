package aoc2023

import readInput

const val RED = "red"
const val GREEN = "green"
const val BLUE = "blue"

const val RED_MAX = 12
const val GREEN_MAX = 13
const val BLUE_MAX = 14

fun main() {

    fun part1(input: List<String>): Int {
        var partNumbersSum = 0

        // go through each line of input
        input.forEachIndexed { gameIndex, game ->
            val cubeMap = mutableMapOf<String, Int>() // highest value color string and amount int
            val gameplay = game.split(": ").last()
            val draws = gameplay.split("; ")

            draws.forEach { draw ->
                val cubes = draw.split(", ")
                cubes.forEach { cube ->
                    val (amount, color) = cube.split(" ")
                    // if the amount of cubes of this color in this draw was greater than any other in the same game, replace it in the map
                    if (amount.toInt() > (cubeMap[color] ?: 0)) cubeMap[color] = amount.toInt()
                }
            }
            if ((cubeMap[aoc2023.RED] ?: 0) <= aoc2023.RED_MAX && (cubeMap[aoc2023.BLUE] ?: 0) <= aoc2023.BLUE_MAX && (cubeMap[aoc2023.GREEN] ?: 0) <= aoc2023.GREEN_MAX) {
                partNumbersSum += gameIndex + 1 // if the game is possible for every color, add the game id to the sum
            }
        }

        return partNumbersSum
    }

    fun part2(input: List<String>): Int {
        var cubePowerSums = 0

        // go through each line of input
        input.forEachIndexed { gameIndex, game ->
            val cubeMap = mutableMapOf<String, Int>() // highest value color string and amount int
            val gameplay = game.split(": ").last()
            val draws = gameplay.split("; ")

            draws.forEach { draw ->
                val cubes = draw.split(", ")
                cubes.forEach { cube ->
                    val (amount, color) = cube.split(" ")
                    // if the amount of cubes of this color in this draw was greater than any other in the same game, replace it in the map
                    if (amount.toInt() > (cubeMap[color] ?: 0)) cubeMap[color] = amount.toInt()
                }
            }

            val power = (cubeMap[aoc2023.RED] ?: 0) * (cubeMap[aoc2023.BLUE] ?: 0) * (cubeMap[aoc2023.GREEN] ?: 0)
            cubePowerSums += power
        }

        return cubePowerSums
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day02_test")
    val testInput2 = readInput("Day02_test")
    check(part1(testInput1) == 8)
    check(part2(testInput2) == 2286)

    // print solutions
    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
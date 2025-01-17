package aoc2023

import readInput
import to2dCharArray
import print2DArray

fun main() {

    fun part1(input: List<String>): Int {
        var furthestDistance = 0

        // get 2d array of chars from input
        val inputAsGrid = input.to2dCharArray()

        inputAsGrid.print2DArray()
        println()

        val expandedRows = mutableListOf<MutableList<Char>>()

        inputAsGrid.forEach { row ->
            var anyGalaxies = false
            row.forEach {
                if (it == '#') anyGalaxies = true
            }

            if (anyGalaxies == false) { // expand by adding two copies of the same row
                expandedRows.add(row.toMutableList())
                expandedRows.add(row.toMutableList())
            } else {
                expandedRows.add(row.toMutableList())
            }
        }

        expandedRows.print2DArray()

        println()

        val numCols = expandedRows[0].size
        val numRows = expandedRows.size

        // Iterate over columns
        for (col in 0 until numCols) {
            println("Column $col:")
            for (row in 0 until numRows) {
                println(expandedRows[row][col])
            }
        }

        return furthestDistance
    }

    // TODO
    fun part2(input: List<String>): Int {
        var previousValueSum = 0

        return previousValueSum
    }


    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day11_test")
    val testInput2 = readInput("Day11_test")
    check(part1(testInput1) == 374)
    // check(part2(testInput2) == 2)

    // print solutions
    val input = readInput("Day11")
    println(part1(input))
    // println(part2(input))
}
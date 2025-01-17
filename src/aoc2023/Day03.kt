package aoc2023

import readInput
import java.math.BigInteger

const val GEAR = -1
const val POINTLESS_SYMBOL = -2

fun main() {

    fun part1(input: List<String>): Int {
        var partNumberSum = 0

        var rowIndex = 0
        while (rowIndex < input.size) {
            var colIndex = 0
            while (colIndex < input[rowIndex].length) {
                var number = ""
                var currentToken = input[rowIndex][colIndex]

                if (!currentToken.isDigit()) { // if we aren't looking at a number, simply move on to the next token
                    colIndex++
                } else { // if we are looking at a number, capture the whole length of the number
                    while (currentToken.isDigit()) {
                        number += currentToken
                        colIndex++
                        if (colIndex >= input[rowIndex].length) break
                        else {
                            currentToken = input[rowIndex][colIndex]
                        }

                    }
                }

                // check if there are any adjacent symbols to this number
                if (number.isNotEmpty()) {
                    var numIndex = colIndex - number.length

                    val top = rowIndex > 0
                    val left = numIndex > 0
                    val right = numIndex + 1 < input[rowIndex].length
                    val bottom = rowIndex < input.size - 1

                    numberLength@ while (numIndex < colIndex && numIndex < input[rowIndex].length) { // iterate through the length of the number in the grid
                        // top-left check
                        if (top && left) {
                            val tokenToCheck = input[rowIndex - 1][numIndex - 1]
                            if (!tokenToCheck.isDigit() && tokenToCheck != '.') {
                                partNumberSum += number.toInt()
                                break@numberLength // if any adjacent symbol hits, no need to check the others
                            }
                        }

                        // top check
                        if (top) {
                            val tokenToCheck = input[rowIndex - 1][numIndex]
                            if (!tokenToCheck.isDigit() && tokenToCheck != '.') {
                                partNumberSum += number.toInt()
                                break@numberLength // if any adjacent symbol hits, no need to check the others
                            }
                        }

                        // top-right check
                        if (top && right) {
                            // could not figure out how I was getting an index out of bounds here so just use null handling to deal with it instead
                            val tokenToCheck = input.getOrNull(rowIndex - 1)?.getOrNull(numIndex + 1) ?: break@numberLength
                            if (!tokenToCheck.isDigit() && tokenToCheck != '.') {
                                partNumberSum += number.toInt()
                                break@numberLength // if any adjacent symbol hits, no need to check the others
                            }
                        }

                        // left check
                        if (left) {
                            val tokenToCheck = input[rowIndex][numIndex - 1]
                            if (!tokenToCheck.isDigit() && tokenToCheck != '.') {
                                partNumberSum += number.toInt()
                                break@numberLength // if any adjacent symbol hits, no need to check the others
                            }
                        }

                        // right check
                        if (right) {
                            val tokenToCheck = input[rowIndex][numIndex + 1]
                            if (!tokenToCheck.isDigit() && tokenToCheck != '.') {
                                partNumberSum += number.toInt()
                                break@numberLength // if any adjacent symbol hits, no need to check the others
                            }
                        }

                        // bottom-left check
                        if (bottom && left) {
                            val tokenToCheck = input[rowIndex + 1][numIndex - 1]
                            if (!tokenToCheck.isDigit() && tokenToCheck != '.') {
                                partNumberSum += number.toInt()
                                break@numberLength // if any adjacent symbol hits, no need to check the others
                            }
                        }

                        // bottom check
                        if (bottom) {
                            val tokenToCheck = input[rowIndex + 1][numIndex]
                            if (!tokenToCheck.isDigit() && tokenToCheck != '.') {
                                partNumberSum += number.toInt()
                                break@numberLength // if any adjacent symbol hits, no need to check the others
                            }
                        }

                        // bottom-right check
                        if (bottom && right) {
                            val tokenToCheck = input[rowIndex + 1][numIndex + 1]
                            if (!tokenToCheck.isDigit() && tokenToCheck != '.') {
                                partNumberSum += number.toInt()
                                break@numberLength // if any adjacent symbol hits, no need to check the others
                            }
                        }
                        numIndex++
                    }
                }
            }
            rowIndex++
        }
        return partNumberSum
    }

    fun part2(input: List<String>): BigInteger {
        var gearRatiosSum: BigInteger = BigInteger.ZERO

        // transform input to grid of integers to represent what we care about
        val modifiedSchematic: MutableList<MutableList<Int>> = input.map { string ->
            string.map { char ->
                if (char.isDigit()) char.toString().toInt() // number
                else if (char == '*') GEAR // gear
                else POINTLESS_SYMBOL // don't care
            }.toMutableList()
        }.toMutableList()

        val partNumberValueMap = mutableMapOf<Int, String>()

        // replace the whole length of a number with a series of the same unique integer e.g. 467 -> 111 and 32 -> 22
        var currentIteratingInt = 1
        var rowIndex = 0
        while (rowIndex < modifiedSchematic.size) {
            var colIndex = 0
            while (colIndex < modifiedSchematic[rowIndex].size) {
                var currentToken = modifiedSchematic[rowIndex][colIndex]

                if (currentToken < 0) { // if we aren't looking at a number, simply move on to the next token
                    colIndex++
                } else { // if we are looking at a number, capture the whole length of the number
                    while (currentToken >= 0) {
                        if (colIndex == modifiedSchematic[rowIndex].size - 1) {
                            colIndex++
                            break
                        } else {
                            // before replacing the value, save the original out to a map for later use
                            if (partNumberValueMap.containsKey(currentIteratingInt)) {
                                val newMapValue = partNumberValueMap[currentIteratingInt].plus(modifiedSchematic[rowIndex][colIndex])
                                partNumberValueMap[currentIteratingInt] = newMapValue
                            } else {
                                partNumberValueMap[currentIteratingInt] = modifiedSchematic[rowIndex][colIndex].toString()
                            }

                            modifiedSchematic[rowIndex][colIndex] = currentIteratingInt // replace the whole number with a different but unique int
                            colIndex++
                            currentToken = modifiedSchematic[rowIndex][colIndex]
                        }

                    }
                    currentIteratingInt++
                }
            }
            rowIndex++
        }

        rowIndex = 0
        while (rowIndex < modifiedSchematic.size) {
            var colIndex = 0
            val numCols = modifiedSchematic[rowIndex].size
            while (colIndex < numCols) {
                var currentToken = modifiedSchematic[rowIndex][colIndex]

                // if we are on a gear, determine how many numbers it is adjacent to
                if (currentToken == GEAR) {
                    val adjacentNums = mutableListOf<Int>() // tracks which unique numbers this gear is adjacent to

                    val top = rowIndex > 0
                    val left = colIndex > 0
                    val right = colIndex + 1 < numCols
                    val bottom = rowIndex < modifiedSchematic.size - 1

                    // top-left check
                    if (top && left) {
                        val tokenToCheck = modifiedSchematic[rowIndex - 1][colIndex - 1]
                        if (tokenToCheck > 0 && !adjacentNums.contains(tokenToCheck)) adjacentNums.add(tokenToCheck)
                    }

                    // top check
                    if (top) {
                        val tokenToCheck = modifiedSchematic[rowIndex - 1][colIndex]
                        if (tokenToCheck > 0 && !adjacentNums.contains(tokenToCheck)) adjacentNums.add(tokenToCheck)
                    }

                    // top-right check
                    if (top && right) {
                        // could not figure out how I was getting an index out of bounds here so just use null handling to deal with it instead
                        val tokenToCheck = modifiedSchematic.getOrNull(rowIndex - 1)?.getOrNull(colIndex + 1)
                        if (tokenToCheck != null && tokenToCheck > 0 && !adjacentNums.contains(tokenToCheck)) adjacentNums.add(tokenToCheck)
                    }

                    // left check
                    if (left) {
                        val tokenToCheck = modifiedSchematic[rowIndex][colIndex - 1]
                        if (tokenToCheck > 0 && !adjacentNums.contains(tokenToCheck)) adjacentNums.add(tokenToCheck)
                    }

                    // right check
                    if (right) {
                        val tokenToCheck = modifiedSchematic[rowIndex][colIndex + 1]
                        if (tokenToCheck > 0 && !adjacentNums.contains(tokenToCheck)) adjacentNums.add(tokenToCheck)
                    }

                    // bottom-left check
                    if (bottom && left) {
                        val tokenToCheck = modifiedSchematic[rowIndex + 1][colIndex - 1]
                        if (tokenToCheck > 0 && !adjacentNums.contains(tokenToCheck)) adjacentNums.add(tokenToCheck)
                    }

                    // bottom check
                    if (bottom) {
                        val tokenToCheck = modifiedSchematic[rowIndex + 1][colIndex]
                        if (tokenToCheck > 0 && !adjacentNums.contains(tokenToCheck)) adjacentNums.add(tokenToCheck)
                    }

                    // bottom-right check
                    if (bottom && right) {
                        val tokenToCheck = modifiedSchematic[rowIndex + 1][colIndex + 1]
                        if (tokenToCheck > 0 && !adjacentNums.contains(tokenToCheck)) adjacentNums.add(tokenToCheck)
                    }
                    colIndex++

                    // calculate gear ratios (only if gear is valid by having two adjacent part numbers)
                    if (adjacentNums.size == 2) {
                        val firstAdjacentValue = partNumberValueMap[adjacentNums.first()]?.toInt() ?: 1
                        val secondAdjacentValue = partNumberValueMap[adjacentNums.last()]?.toInt() ?: 1
                        val gearRatio = firstAdjacentValue * secondAdjacentValue

                        gearRatiosSum += gearRatio.toBigInteger()
                    }
                } else colIndex++ // if current token is not a gear

            }
            rowIndex++
        }

        return gearRatiosSum
    }


    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day03_test")
    val testInput2 = readInput("Day03_test")
    check(part1(testInput1) == 4361)
    check(part2(testInput2) == (467835).toBigInteger())

    // print solutions
    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
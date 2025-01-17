package aoc2023

import readInput
import splitToIntList

fun main() {

    fun part1(input: List<String>): Int {
        var nextValueSum = 0

        input.forEach { sequence ->
            // parse starting sequence and create a history to be added to
            val intSequence = sequence.splitToIntList()
            val sequenceHistory = mutableListOf<MutableList<Int>>()
            var currentSequence = intSequence.toMutableList()
            sequenceHistory.add(currentSequence)

            // create sequence history by reducing list to difference between values in previous list
            var notReduceToZero = true
            while (notReduceToZero) {
                val pairs = currentSequence.windowed(2)
                val differenceSequence = mutableListOf<Int>()

                pairs.forEach {
                    val difference = it.last() - it.first()
                    differenceSequence.add(difference)
                }

                currentSequence = differenceSequence
                sequenceHistory.add(currentSequence)
                if (currentSequence.all { it == 0 }) notReduceToZero = false // end when the entire sequence is zero
            }

            // iterate through the history of sequences backwards and add up the final value of one list to the list above
            var lastValue = 0
            sequenceHistory.reversed().forEach { sequence ->
                lastValue = sequence.last() + lastValue
            }

            nextValueSum += lastValue
        }
        return nextValueSum
    }

    fun part2(input: List<String>): Int {
        var previousValueSum = 0

        input.forEach { sequence ->
            // parse starting sequence and create a history to be added to
            val intSequence = sequence.splitToIntList()
            val sequenceHistory = mutableListOf<MutableList<Int>>()
            var currentSequence = intSequence.toMutableList()
            sequenceHistory.add(currentSequence)

            // create sequence history by reducing list to difference between values in previous list
            var notReduceToZero = true
            while (notReduceToZero) {
                val pairs = currentSequence.windowed(2)
                val differenceSequence = mutableListOf<Int>()

                pairs.forEach {
                    val difference = it.last() - it.first()
                    differenceSequence.add(difference)
                }

                currentSequence = differenceSequence
                sequenceHistory.add(currentSequence)
                if (currentSequence.all { it == 0 }) notReduceToZero = false // end when the entire sequence is zero
            }

            // iterate through the history of sequences backwards and subtract the first value of one list from the list above
            var lastValue = 0
            sequenceHistory.reversed().forEach { sequence ->
                lastValue = sequence.first() - lastValue
            }

            previousValueSum += lastValue
        }
        return previousValueSum
    }


    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day09_test")
    val testInput2 = readInput("Day09_test")
    check(part1(testInput1) == 114)
    check(part2(testInput2) == 2)

    // print solutions
    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
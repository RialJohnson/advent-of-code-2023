fun main() {
    data class IO(val input: List<String>, val output: List<String>)

    fun getIOList(input: List<String>): List<IO> {
        val ioList = mutableListOf<IO>()

        input.forEach {
            val breakDown = it.split(" | ")
            val inputString = breakDown.first()
            val outputString = breakDown.last()
            val inputList = inputString.split(" ")
            val outputList = outputString.split(" ")
            ioList.add(IO(inputList, outputList))
        }

        return ioList
    }

    fun part1(input: List<String>): Int {
        val ioList = getIOList(input)

        // count number of 1 (2 digit), 4 (4 digit), 7 (3 digit), 8 (7 digit)
        var count = 0
        ioList.forEach {
            it.output.forEach { digit ->
                when (digit.length) {
                    2 -> count++
                    4 -> count++
                    3 -> count++
                    7 -> count++
                }
            }
        }

        return count
    }

    // mean
    fun part2(input: List<String>): Int {
        val ioList = getIOList(input)
        val finalValList = mutableListOf<Int>()

        ioList.forEach { puzzleLine ->
            // positions in this list start at the top and work clockwise, ending with the middle segment
            val positionList = mutableListOf<Char>('z','z','z','z','z','z','z') // z is a placeholder

            // these have a unique number of digits so we just know what they are
            var zero = ""
            val one = puzzleLine.input.first { digit -> digit.length == 2 }
            var two = ""
            var three = ""
            val four = puzzleLine.input.first { digit -> digit.length == 4 }
            var five = ""
            var six = ""
            val seven = puzzleLine.input.first { digit -> digit.length == 3 }
            val eight = puzzleLine.input.first { digit -> digit.length == 7 }
            var nine = ""

            val fiveSegmentNumbers = puzzleLine.input.filter { digit -> digit.length == 5 }
            val sixSegmentNumbers = puzzleLine.input.filter { digit -> digit.length == 6 }

            /* start determining each number by deduction */

            // get top segment (position 0)
            seven.forEach { thisSegmentInSeven ->
                // the only difference between 7 and 1 is the top segment
                if (!one.contains(thisSegmentInSeven)) {
                    positionList[0] = thisSegmentInSeven
                }
            }

            // get top-right segment (position 1) and bottom-right segment (position 2)
            one.forEach { thisSegmentInOne ->
                // of the 6 segment numbers, 6 is the only number that doesn't have all the segments in 1 (top right)
                var topRightChosen = false
                sixSegmentNumbers.forEach { thisSixSegmentNumber ->
                    if (!thisSixSegmentNumber.contains(thisSegmentInOne)) {
                        positionList[1] = thisSegmentInOne
                        six = thisSixSegmentNumber
                        topRightChosen = true
                    }
                }
                // if the segment in one is not the top right one, it must be bottom right (there are only 2)
                if (!topRightChosen) positionList[2] = thisSegmentInOne
            }

            // find two, three, and five
            fiveSegmentNumbers.forEach { thisFiveSegmentNumber ->
                // 3 is the only 5 segment number to have all three of these segments
                if (thisFiveSegmentNumber.contains(positionList[0]) &&
                    thisFiveSegmentNumber.contains(positionList[1]) &&
                    thisFiveSegmentNumber.contains(positionList[2])) three = thisFiveSegmentNumber

                if (thisFiveSegmentNumber.contains(positionList[1]) &&
                    !thisFiveSegmentNumber.contains(positionList[2])) two = thisFiveSegmentNumber

                if (thisFiveSegmentNumber.contains(positionList[2]) &&
                    !thisFiveSegmentNumber.contains(positionList[1])) five = thisFiveSegmentNumber
            }

            // get bottom-left segment (position 4)
            six.forEach { thisSegmentInSix ->
                if (!five.contains(thisSegmentInSix)) positionList[4] = thisSegmentInSix
            }

            // find zero, nine
            sixSegmentNumbers.forEach { thisSixSegmentNumber ->
                if (!thisSixSegmentNumber.contains(positionList[4])) nine = thisSixSegmentNumber
                else if (thisSixSegmentNumber != six) zero = thisSixSegmentNumber
            }

            // add vals to output list
            var intBuilder: StringBuilder = java.lang.StringBuilder()
            puzzleLine.output.forEach { outputDigit ->
                if (outputDigit.charsAreTheSame(zero)) intBuilder.append('0')
                else if (outputDigit.charsAreTheSame(one)) intBuilder.append('1')
                else if (outputDigit.charsAreTheSame(two)) intBuilder.append('2')
                else if (outputDigit.charsAreTheSame(three)) intBuilder.append('3')
                else if (outputDigit.charsAreTheSame(four)) intBuilder.append('4')
                else if (outputDigit.charsAreTheSame(five)) intBuilder.append('5')
                else if (outputDigit.charsAreTheSame(six)) intBuilder.append('6')
                else if (outputDigit.charsAreTheSame(seven)) intBuilder.append('7')
                else if (outputDigit.charsAreTheSame(eight)) intBuilder.append('8')
                else if (outputDigit.charsAreTheSame(nine)) intBuilder.append('9')

            }
            finalValList.add(intBuilder.toString().toInt())
        }

        // add up all the output ints for final answer
        var count = 0
        finalValList.forEach {
            count += it
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    // print solutions
    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

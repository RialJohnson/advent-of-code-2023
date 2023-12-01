import java.util.Stack

fun main() {
    fun calculateSyntaxError(errorChar: Char): Int {
        return when (errorChar) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }

    fun calculatePoints(errorChar: Char): Int {
        return when (errorChar) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> 0
        }
    }

    fun calculateScore(stack: Stack<Char>): Long {
        var totalScore: Long = 0

        while (stack.isNotEmpty()) {
            val points = calculatePoints(stack.peek())
            totalScore = totalScore * 5 + points
            stack.pop()
        }
        return totalScore
    }

    fun part1(input: List<String>): Int {
        var syntaxError = 0

        input.forEach { line ->
            val closingStack = Stack<Char>()
            val closingSet = setOf(')', ']', '}', '>')

            run mainLoop@{
                line.forEach {
                    // check for open brackets

                    // if we get an opening bracket, add the equivalent closing bracket to the stack
                    when (it) {
                        '(' -> closingStack.push(')')
                        '[' -> closingStack.push(']')
                        '{' -> closingStack.push('}')
                        '<' -> closingStack.push('>')
                    }

                    // if we get a closing bracket, make sure it's the right one
                    if (closingSet.contains(it)) {

                        // if the stack is not empty, make sure we got the correct closer
                        if (closingStack.isNotEmpty()) {

                            if (it == closingStack.peek()) {
                                closingStack.pop() // remove from stack if closed correctly
                            } else {
                                syntaxError += calculateSyntaxError(it) // add penalty if closed incorrectly
                                return@mainLoop // stop, we only care about first error
                            }
                        } else { // if stack was empty but we still got a closer
                            syntaxError += calculateSyntaxError(it)
                            return@mainLoop // stop, we only care about first error
                        }

                    }

                }
            }
        }
        return syntaxError
    }

    fun part2(input: List<String>): Long {
        val scores = mutableListOf<Long>()
        input.forEach { line ->
            val closingStack = Stack<Char>()
            val closingSet = setOf(')', ']', '}', '>')
            var lineCorrupt = false

            run mainLoop@{
                line.forEach {
                    // check for open brackets

                    // if we get an opening bracket, add the equivalent closing bracket to the stack
                    when (it) {
                        '(' -> closingStack.push(')')
                        '[' -> closingStack.push(']')
                        '{' -> closingStack.push('}')
                        '<' -> closingStack.push('>')
                    }

                    // if we get a closing bracket, make sure it's the right one
                    if (closingSet.contains(it)) {

                        // if the stack is not empty, make sure we got the correct closer
                        if (closingStack.isNotEmpty()) {
                            if (it == closingStack.peek()) closingStack.pop() // remove from stack if closed correctly
                            else {
                                lineCorrupt = true
                                return@mainLoop // corrupt line, we don't care about it
                            }
                        } else { // if stack was empty but we still got a closer
                            lineCorrupt = true
                            return@mainLoop // corrupt line, we don't care about it
                        }
                    }

                }
            }

            // if the stack isn't empty, this is an incomplete line and reading the stack tells us what we need to complete
            if (closingStack.isNotEmpty() && !lineCorrupt) {
                scores.add(calculateScore(closingStack))
            }
        }

        val middleScore = scores.sorted()[scores.size / 2]
        return middleScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == (288957).toLong())

    // print solutions
    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

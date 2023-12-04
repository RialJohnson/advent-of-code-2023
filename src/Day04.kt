import java.math.BigInteger

fun main() {

    fun part1(input: List<String>): BigInteger {
        var scratchcardsSum: BigInteger = (0).toBigInteger()

        input.forEach { scratchcard ->
            // parse game info
            val gameInfo = scratchcard.split(": ").last()
            val (winningNumbersString, cardNumbersString) = gameInfo.split (" | ")
            val winningNumberList = winningNumbersString.trim().split("\\s+".toRegex()).map { it.toInt() }
            val cardNumbersList = cardNumbersString.trim().split("\\s+".toRegex()).map { it.toInt() }

            // find matching numbers
            var numHits = 0
            cardNumbersList.forEach { cardNumber ->
                if (winningNumberList.contains((cardNumber))){
                    numHits++
                }
            }

            // calculate score
            val score = if (numHits == 0) BigInteger.ZERO
            else if (numHits == 1) BigInteger.ONE
            else 2.toBigInteger().pow(numHits - 1) // points can be calculated using 2^(n-1)

            scratchcardsSum += score
        }

        return scratchcardsSum
    }

    fun part2(input: List<String>): Int {
        var numScratchcards = 0

        // create map to track cards and initialize it to one copy of each
        val cardTracker = mutableMapOf<Int, Int>()
        for (i in 1..input.size) {
            cardTracker[i] = 1
        }


        input.forEachIndexed { originalCardIndex, scratchcard ->
            val cardNumber = originalCardIndex + 1

            // parse game info
            val gameInfo = scratchcard.split(": ").last()
            val (winningNumbersString, cardNumbersString) = gameInfo.split (" | ")
            val winningNumberList = winningNumbersString.trim().split("\\s+".toRegex()).map { it.toInt() }
            val cardNumbersList = cardNumbersString.trim().split("\\s+".toRegex()).map { it.toInt() }

            // find matching numbers
            var numHits = 0
            cardNumbersList.forEach { cardNumber ->
                if (winningNumberList.contains((cardNumber))){
                    numHits++
                }
            }

            // find number of copies we have of this card and count them that many times
            val currentNumCopies = cardTracker[cardNumber] ?: 0
            for (i in 1..currentNumCopies) {
                // add the copies of cards created by matches on this card
                for (i in cardNumber + 1..cardNumber + numHits) {
                    val currentNumCopies = cardTracker[i] ?: 1
                    cardTracker[i] = currentNumCopies + 1
                }
            }
        }

        // count up the total number of scratchcards from the map
        cardTracker.forEach {
            numScratchcards += it.value
        }

        return numScratchcards
    }


    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day04_test")
    val testInput2 = readInput("Day04_test")
    check(part1(testInput1) == (13).toBigInteger())
    check(part2(testInput2) == 30)

    // print solutions
    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
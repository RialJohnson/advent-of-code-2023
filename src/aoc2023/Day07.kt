package aoc2023

import charToInt
import readInput

const val YAHTZEE = 0
const val QUADS = 1
const val FULL_HOUSE = 2
const val SET = 3
const val TWO_PAIR = 4
const val PAIR = 5
const val HIGH_CARD = 6

const val JOKER_VALUE = -1

fun main() {

    data class Hand(val cards: String, val bet: Int, val type: Int = -1, val rank: Int = -1)

    val FACE_CARD_MAP = mapOf(
        Pair('A', 14),
        Pair('K', 13),
        Pair('Q', 12),
        Pair('J', 11),
        Pair('T', 10)
    )

    val FACE_CARD_MAP_JOKER = mapOf(
        Pair('A', 14),
        Pair('K', 13),
        Pair('Q', 12),
        Pair('J', -1),
        Pair('T', 10)
    )

    fun Char.cardToValue(): Int {
        return if (this.isDigit()) this.charToInt()
        else FACE_CARD_MAP[this] ?: 0
    }

    fun Char.cardToValueJoker(): Int {
        return if (this.isDigit()) this.charToInt()
        else FACE_CARD_MAP_JOKER[this] ?: 0
    }

    // sorts hands by evaluating each card in order, moving on to the next card only if the current cards are equal
    fun List<Hand>.sortHandsWithinRank(): List<Hand> {
        return this
            .sortedWith(compareBy(
                { it.cards[0].cardToValue() },
                { it.cards[1].cardToValue() },
                { it.cards[2].cardToValue() },
                { it.cards[3].cardToValue() },
                { it.cards[4].cardToValue() }
            ))
    }

    // sorts hands by evaluating each card in order, moving on to the next card only if the current cards are equal
    fun List<Hand>.sortHandsWithinRankJoker(): List<Hand> {
        return this
            .sortedWith(compareBy(
                { it.cards[0].cardToValueJoker() },
                { it.cards[1].cardToValueJoker() },
                { it.cards[2].cardToValueJoker() },
                { it.cards[3].cardToValueJoker() },
                { it.cards[4].cardToValueJoker() }
            ))
    }

    // does same thing as .any{} but excludes the joker
    fun Map<Int, Int>.anyNotJoker(predicate: (Map.Entry<Int, Int>) -> Boolean): Boolean {
        return this.filterKeys { it != JOKER_VALUE }.entries.any(predicate)
    }

    fun part1(input: List<String>): Int {
        var totalWinnings = 0

        // parse input into data class
        var camelHands = mutableListOf<Hand>()

        input.forEach {
            val cards = it.split(" ").first()
            val bet = it.split(" ").last().toInt()

            camelHands.add(Hand(cards = cards, bet = bet))
        }

        // classify hands into hand types
        camelHands.forEachIndexed { index, hand ->
            val cardMap = mutableMapOf<Int, Int>() // map of how many times each card occurred in this hand

            hand.cards.forEach { card ->
                if (card.isDigit()) { // card is value 2 - 9
                    val intValueOfCard = card.charToInt()
                    val currentCount = cardMap[intValueOfCard] ?: 0
                    cardMap[intValueOfCard] = currentCount + 1
                } else { // card is face card (10 - A)
                    val intValueOfCard = FACE_CARD_MAP[card]!!
                    val currentCount = cardMap[intValueOfCard] ?: 0
                    cardMap[intValueOfCard] = currentCount + 1
                }
            }

            val type =
                if (cardMap.any { it.value == 5 }) YAHTZEE
                else if (cardMap.any { it.value == 4 }) QUADS
                else if (cardMap.any { it.value == 3 } && (cardMap.any { it.value == 2 })) FULL_HOUSE
                else if (cardMap.any { it.value == 3 }) SET
                else if ((cardMap.values.count { it == 2 } == 2)) TWO_PAIR
                else if (cardMap.any { it.value == 2 }) PAIR
                else HIGH_CARD

            val currentHandState = camelHands[index]
            val newHandState = currentHandState.copy(type = type)
            camelHands[index] = newHandState
        }

        // rank the hands (from bottom up, since rank == 1 is the worst)
        val highCardHands = camelHands.filter { it.type == HIGH_CARD }.sortHandsWithinRank()
        val pairHands = camelHands.filter { it.type == PAIR }.sortHandsWithinRank()
        val twoPairHands = camelHands.filter { it.type == TWO_PAIR }.sortHandsWithinRank()
        val setHands = camelHands.filter { it.type == SET }.sortHandsWithinRank()
        val fullHouseHands = camelHands.filter { it.type == FULL_HOUSE }.sortHandsWithinRank()
        val quadsHands = camelHands.filter { it.type == QUADS }.sortHandsWithinRank()
        val yahtzeeHands = camelHands.filter { it.type == YAHTZEE }.sortHandsWithinRank()

        val orderedHands = highCardHands + pairHands + twoPairHands + setHands + fullHouseHands + quadsHands + yahtzeeHands

        // calculate and add up the winnings from all the hands
        orderedHands.forEachIndexed { index, hand ->
            val rank = index + 1
            val winnings = rank * hand.bet
            totalWinnings += winnings
        }

        return totalWinnings
    }

    fun part2(input: List<String>): Int {

        var totalWinnings = 0

        // parse input into data class
        var camelHands = mutableListOf<Hand>()

        input.forEach {
            val cards = it.split(" ").first()
            val bet = it.split(" ").last().toInt()

            camelHands.add(Hand(cards = cards, bet = bet))
        }

        // classify hands into hand types
        camelHands.forEachIndexed { index, hand ->
            val cardMap = mutableMapOf<Int, Int>() // map of how many times each card occurred in this hand

            hand.cards.forEach { card ->
                if (card.isDigit()) { // card is value 2 - 9
                    val intValueOfCard = card.charToInt()
                    val currentCount = cardMap[intValueOfCard] ?: 0
                    cardMap[intValueOfCard] = currentCount + 1
                } else { // card is face card (10 - A)
                    val intValueOfCard = FACE_CARD_MAP_JOKER[card]!!
                    val currentCount = cardMap[intValueOfCard] ?: 0
                    cardMap[intValueOfCard] = currentCount + 1
                }
            }

            val jokerCount = cardMap[JOKER_VALUE] ?: 0

            val type =
                if (cardMap.any { it.value == 5 }) YAHTZEE
                else if (cardMap.anyNotJoker { it.value == 4 } && jokerCount == 1) YAHTZEE
                else if (cardMap.anyNotJoker { it.value == 3 } && jokerCount == 2) YAHTZEE
                else if (cardMap.anyNotJoker { it.value == 2 } && jokerCount == 3) YAHTZEE
                else if (cardMap.anyNotJoker { it.value == 1 } && jokerCount == 4) YAHTZEE

                else if (cardMap.anyNotJoker { it.value == 4 }) QUADS
                else if (cardMap.anyNotJoker { it.value == 3 } && jokerCount == 1) QUADS
                else if (cardMap.anyNotJoker { it.value == 2 } && jokerCount == 2) QUADS
                else if (cardMap.anyNotJoker { it.value == 1 } && jokerCount == 3) QUADS

                else if (cardMap.anyNotJoker { it.value == 3 } && (cardMap.anyNotJoker { it.value == 2 })) FULL_HOUSE
                else if ((cardMap.values.count { it == 2 } == 2) && jokerCount == 1) FULL_HOUSE // two pair and a joker
                else if (cardMap.anyNotJoker { it.value == 2 } && jokerCount == 2) FULL_HOUSE // a pair and two jokers

                else if (cardMap.anyNotJoker { it.value == 3 }) SET
                else if (cardMap.anyNotJoker { it.value == 2 } && jokerCount == 1) SET // a pair and one joker
                else if (jokerCount == 2) SET // if we have a pair of jokers and nothing better

                else if ((cardMap.values.count { it == 2 } == 2)) TWO_PAIR
                else if (cardMap.anyNotJoker { it.value == 2 } && jokerCount == 1) TWO_PAIR // a pair and a joker

                else if (cardMap.anyNotJoker { it.value == 2 }) PAIR
                else if (jokerCount == 1) PAIR // if we have a joker, it's always a pair
                else HIGH_CARD

            val currentHandState = camelHands[index]
            val newHandState = currentHandState.copy(type = type)
            camelHands[index] = newHandState
        }

        // rank the hands (from bottom up, since rank == 1 is the worst)
        val highCardHands = camelHands.filter { it.type == HIGH_CARD }.sortHandsWithinRankJoker()
        val pairHands = camelHands.filter { it.type == PAIR }.sortHandsWithinRankJoker()
        val twoPairHands = camelHands.filter { it.type == TWO_PAIR }.sortHandsWithinRankJoker()
        val setHands = camelHands.filter { it.type == SET }.sortHandsWithinRankJoker()
        val fullHouseHands = camelHands.filter { it.type == FULL_HOUSE }.sortHandsWithinRankJoker()
        val quadsHands = camelHands.filter { it.type == QUADS }.sortHandsWithinRankJoker()
        val yahtzeeHands = camelHands.filter { it.type == YAHTZEE }.sortHandsWithinRankJoker()

        val orderedHands = highCardHands + pairHands + twoPairHands + setHands + fullHouseHands + quadsHands + yahtzeeHands

        // calculate and add up the winnings from all the hands
        orderedHands.forEachIndexed { index, hand ->
            val rank = index + 1
            val winnings = rank * hand.bet
            totalWinnings += winnings
        }

        return totalWinnings
    }


    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day07_test")
    val testInput2 = readInput("Day07_test")
    check(part1(testInput1) == 6440)
    check(part2(testInput2) == 5905)

    // print solutions
    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
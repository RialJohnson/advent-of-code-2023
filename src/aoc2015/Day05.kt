
// this one is just can you figure out how to construct the regex. not a particularly useful skill imo so I used a lot
// of references to figure it out
fun main() {
    fun part1(input: List<String>): Int {
        var niceStrings = 0
        val threeValuesRegex = "(?=(.*[aeiou]){3}).*".toRegex()
        val twiceInARowRegex = "(.)\\1".toRegex()
        val naughtyRegex = ("^(?!.*(ab|cd|pq|xy)).*$").toRegex()

        input.forEach { string ->
            if (string.matches(threeValuesRegex) && twiceInARowRegex.find(string) != null && string.matches(naughtyRegex)) {
                niceStrings++
            }
        }

        return niceStrings
    }

    fun part2(input: List<String>): Int {
        var niceStrings = 0
        val pairNoOverlapRegex = """(..).*\1""".toRegex()
        val sandwichRegex = """(.).\1""".toRegex()

        input.forEach { string ->
            if (pairNoOverlapRegex.find(string) != null && sandwichRegex.find(string) != null) {
                niceStrings++
            }
        }

        return niceStrings
    }

    // test if implementation meets criteria from the description, like:
    // val testInput1 = readInput("aoc2015/Day04_test1")
    // check(part1(testInput1) == 609043)

    // print solutions
    val input = readInput("aoc2015/Day05")
    println(part1(input))
    println(part2(input))
}
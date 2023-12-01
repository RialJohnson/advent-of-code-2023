fun main() {

    fun getGroupedBingoBoards(input: List<String>): MutableList<List<MutableList<Int>>> {
        // remove the number order and newline to just get the bingo boards
        val allBingoBoards = input.drop(2)
        val formattedBingoBoards = mutableListOf<MutableList<Int>>()

        allBingoBoards.forEach { bingoLine ->
            if (bingoLine.isNotEmpty()) {
                val stringList = bingoLine.chunked(3) // split into a list of strings of only the individual ints
                val intRow = mutableListOf<Int>()
                stringList.forEach { singleVal ->
                    intRow.add(singleVal.filter { !it.isWhitespace() }.toInt()) // remove spaces before converting to int
                }
                formattedBingoBoards.add(intRow)
            }
        }

        return formattedBingoBoards.chunked(5).toMutableList()
    }

    fun getMarkedBoard(groupedBoards: MutableList<List<MutableList<Int>>>): MutableList<MutableList<MutableList<Boolean>>>{
        val markedBoard = mutableListOf<MutableList<MutableList<Boolean>>>()
        val falseList = listOf(false, false, false, false, false)

        // initialize our marked board with as many bingo boards as there actually are
        groupedBoards.forEach {
            markedBoard.add(mutableListOf(
                falseList.toMutableList(),
                falseList.toMutableList(),
                falseList.toMutableList(),
                falseList.toMutableList(),
                falseList.toMutableList()
            ))
        }

        return markedBoard
    }

    fun part1(input: List<String>): Int {
        var sumOfBingoBoard = 0
        var score = 0
        val numberOrder = input.first()
        val numberOrderList = numberOrder.split(",")

        val groupedBingoBoards = getGroupedBingoBoards(input)
        val markedBoard = getMarkedBoard(groupedBingoBoards)

        run mainLoop@{
            numberOrderList.forEach {
                val currentBingoNumber = it.toInt()

                groupedBingoBoards.forEachIndexed { index1, board ->
                    board.forEachIndexed { index2, line ->
                        line.forEachIndexed { index3, singleVal ->
                            if (singleVal == currentBingoNumber) markedBoard[index1][index2][index3] = true
                        }
                    }
                }

                // check for bingos
                var anyBingo = false
                var boardNumber = -1
                markedBoard.forEachIndexed { index, board ->
                    // check for bingos along rows
                    board.forEach { row ->
                        if (row.all { boolVal -> boolVal == true }) {
                            anyBingo = true
                            boardNumber = index
                        }
                    }

                    // check for bingos along columns
                    for (i in 0..4) {
                        if (board.all { it[i] == true }) {
                            anyBingo = true
                            boardNumber = index
                        }
                    }
                }

                // if we have a bingo, we can calculate the values
                if (anyBingo && boardNumber != -1) {
                    val bingoBoard = groupedBingoBoards[boardNumber]
                    bingoBoard.forEachIndexed { rowIndex, row ->
                        row.forEachIndexed { singleValIndex, singleVal ->
                            if (markedBoard[boardNumber][rowIndex][singleValIndex] == false) sumOfBingoBoard += singleVal
                        }

                    }


                }

                if (anyBingo) {
                    score = sumOfBingoBoard * currentBingoNumber
                    return@mainLoop
                }

            }
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var sumOfBingoBoard = 0
        var score = 0
        var finalBoardNumber = -1
        val numberOrder = input.first()
        val numberOrderList = numberOrder.split(",")

        val groupedBingoBoards = getGroupedBingoBoards(input)
        val markedBoard = getMarkedBoard(groupedBingoBoards)

        run mainLoop@{
            numberOrderList.forEach {
                val currentBingoNumber = it.toInt()

                groupedBingoBoards.forEachIndexed { index1, board ->
                    board.forEachIndexed { index2, line ->
                        line.forEachIndexed { index3, singleVal ->
                            if (singleVal == currentBingoNumber) markedBoard[index1][index2][index3] = true
                        }
                    }
                }

                // check for bingos
                var numBingos = 0
                markedBoard.forEachIndexed { index, board ->
                    var localBingo = false
                    // check for bingos along rows
                    board.forEach { row ->
                        if (!localBingo) {
                            if (row.all { boolVal -> boolVal == true }) {
                                numBingos++
                                localBingo = true
                            }
                        }
                    }
                    // don't check for column bingos if we already found one
                    if (!localBingo) {
                        // check for bingos along columns
                        for (i in 0..4) {
                            if (!localBingo && board.all { it[i] == true }) {
                                numBingos++
                            }
                        }
                    }
                }

                // when there is only 1 bingo board left without a bingo
                if (groupedBingoBoards.size - numBingos == 1) {

                    // find the board without a bingo
                    markedBoard.forEachIndexed { index, board ->
                        var anyBingo = false
                        // check for bingos along rows
                        board.forEach { row ->
                            if (row.all { boolVal -> boolVal == true }) {
                                anyBingo = true
                            }
                        }

                        // check for bingos along columns
                        for (i in 0..4) {
                            if (board.all { it[i] == true }) {
                                anyBingo = true
                            }
                        }

                        // there is only 1 board without a bingo, so the first one we find is correct
                        if (!anyBingo) {
                            finalBoardNumber = index
                        }
                    }
                }

                // our final board got a bingo, so we can score it
                if (groupedBingoBoards.size - numBingos == 0) {
                    val bingoBoard = groupedBingoBoards[finalBoardNumber]
                    bingoBoard.forEachIndexed { rowIndex, row ->
                        row.forEachIndexed { singleValIndex, singleVal ->
                            if (markedBoard[finalBoardNumber][rowIndex][singleValIndex] == false) sumOfBingoBoard += singleVal
                        }

                    }

                    score = sumOfBingoBoard * currentBingoNumber
                    return@mainLoop
                }
            }
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    // print solutions
    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

const val START_NODE = "AAA"
const val END_NODE = "ZZZ"
const val LEFT = 'L'
const val RIGHT = 'R'

fun main() {

    data class Node(val label: String, val left: String, val right: String)

    // calculates the greatest common denominator of two numbers
    fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }

    // calculates the least common multiple of two numbers
    fun lcm(a: Long, b: Long): Long {
        return if (a == 0L || b == 0L) 0L else (a * b) / gcd(a, b)
    }

    // calculates the least common multiple of a list of ints
    fun List<Int>.calculateLCM(): Long {
        return this.fold(1) { acc, num ->
            lcm(acc, num.toLong())
        }
    }

    fun part1(input: List<String>): Int {
        var stepCount = 0
        val nodeMap = mutableMapOf<String, Node>()
        val pathToFollow = input.first()

        input.forEachIndexed { index, line ->
            // for each node, add it to the map along with it's left and right paths
            if (index != 0 && line.isNotEmpty()) { // discount the first two lines that aren't the node map
                val (nodeLabel, nodePaths) = line.split(" = ")
                val (leftPathRaw, rightPathRaw) = nodePaths.split(", ")
                val leftPath = leftPathRaw.replace("(", "")
                val rightPath = rightPathRaw.replace(")", "")

                val node = Node(nodeLabel, leftPath, rightPath)
                nodeMap[nodeLabel] = node
            }
        }

        var currentNode = nodeMap[START_NODE]
        while (currentNode?.label != END_NODE) { // keep looping through the path until we reach the end
            pathToFollow.forEach { direction ->
                if (currentNode?.label == END_NODE) return stepCount // return as soon as we reach the end

                if (direction == LEFT) {
                    currentNode = nodeMap[currentNode?.left]
                    stepCount++
                } else if (direction == RIGHT) {
                    currentNode = nodeMap[currentNode?.right]
                    stepCount++
                }
            }
        }

        return stepCount // should never reach this but easier than all the null handling
    }

    // solution that disregards the heat death of the universe
    fun part2(input: List<String>): Int {
        var stepCount = 0
        val nodeMap = mutableMapOf<String, Node>()
        val pathToFollow = input.first()

        input.forEachIndexed { index, line ->
            // for each node, add it to the map along with it's left and right paths
            if (index != 0 && line.isNotEmpty()) { // discount the first two lines that aren't the node map
                val (nodeLabel, nodePaths) = line.split(" = ")
                val (leftPathRaw, rightPathRaw) = nodePaths.split(", ")
                val leftPath = leftPathRaw.replace("(", "")
                val rightPath = rightPathRaw.replace(")", "")

                val node = Node(nodeLabel, leftPath, rightPath)
                nodeMap[nodeLabel] = node
            }
        }

        val startingNodeList = nodeMap.filter { it.key.endsWith('A') }
        println(startingNodeList)

        var notAllTerminusReached = true
        val currentNodeList = startingNodeList.values.toMutableList()

        monkey@ while (notAllTerminusReached) { // keep looping through the path until we reach the end

            pathToFollow.forEach { direction ->
                var terminusReachedCount = 0
                currentNodeList.forEachIndexed { index, currentNode ->
                    // println("Current node: $currentNode, go $direction")

                    if (currentNode.label.endsWith('Z') == true) {
                        terminusReachedCount++
                        // println(terminusReachedCount)
                    } // return as soon as we reach the end

                    if (direction == LEFT) {
                        currentNodeList[index] = nodeMap[currentNode.left]!!
                    } else if (direction == RIGHT) {
                        currentNodeList[index] = nodeMap[currentNode.right]!!
                    }
                }
                // println()
                stepCount++ // since we took a path down each way, increment

                if (terminusReachedCount == startingNodeList.size) {
                    notAllTerminusReached = false
                    return stepCount - 1
                } else terminusReachedCount = 0 // reset if we didn't reach the terminus for every path at the same time
            }

         }

        // println(stepCount - 1)
        return stepCount - 1 // the first "step" doesn't count
    }

    // correct solution that disregards the heat death of the universe
    fun part2LCM(input: List<String>): Long {
        val nodeMap = mutableMapOf<String, Node>()
        val pathToFollow = input.first()

        input.forEachIndexed { index, line ->
            // for each node, add it to the map along with it's left and right paths
            if (index != 0 && line.isNotEmpty()) { // discount the first two lines that aren't the node map
                val (nodeLabel, nodePaths) = line.split(" = ")
                val (leftPathRaw, rightPathRaw) = nodePaths.split(", ")
                val leftPath = leftPathRaw.replace("(", "")
                val rightPath = rightPathRaw.replace(")", "")

                val node = Node(nodeLabel, leftPath, rightPath)
                nodeMap[nodeLabel] = node
            }
        }

        val startingNodeMap = nodeMap.filter { it.key.endsWith('A') }
        val startingNodeList = startingNodeMap.values.toMutableList()
        val pathLengthList = mutableListOf<Int>() // a list of how long it takes to reach an end node (and start looping again)

        // iterate through to each end node from each start node (same as part 1 but for each start node)
        startingNodeList.forEach { startingNode ->
            var pathLength = 0
            var currentNode = startingNode
            var keepLoop = true
            while (keepLoop) { // keep looping through the path until we reach the end
                pathToFollow.forEach { direction ->
                    if (currentNode.label.endsWith('Z') == true) {
                        pathLengthList.add(pathLength) // once we find the end node, save the path length
                        keepLoop = false // end the loop
                    }

                    if (direction == LEFT) {
                        currentNode = nodeMap[currentNode?.left]!!
                        pathLength++
                    } else if (direction == RIGHT) {
                        currentNode = nodeMap[currentNode?.right]!!
                        pathLength++
                    }
                }
            }
        }

        // the total steps it takes is just the least common multiple of how long it takes each starting node to reach
        // the end node, given that the end nodes happen to look back to the starting nodes
        return pathLengthList.calculateLCM()
    }


    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day08_test")
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput1) == 6)
    check(part2LCM(testInput2) == 6L)

    // print solutions
    val input = readInput("Day08")
    println(part1(input))
    println(part2LCM(input))
}
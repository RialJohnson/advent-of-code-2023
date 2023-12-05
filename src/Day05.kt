const val SOURCE_RANGE_START = 1
const val DESTINATION_RANGE_START = 0
const val RANGE = 2

fun main() {

    fun mapAlmanac(index: Int, input: List<String>): Map<Long, Long> {
        val map = mutableMapOf<Long, Long>()
        var nextIndex = index + 1
        var nextLine: String = input[nextIndex]
        do {
            val mapping = nextLine.splitToLongList()
            val sourceRangeStart = mapping[SOURCE_RANGE_START]
            val destinationRangeStart = mapping[DESTINATION_RANGE_START]
            val range = mapping[RANGE]

            for (i in 0 until range) {
                map[sourceRangeStart + i] = destinationRangeStart + i
            }

            nextIndex++
            if (nextIndex < input.size) nextLine = input[nextIndex] else break
        } while (nextLine.isNotEmpty())

        return map
    }

    fun getAlmanacLists(index: Int, input: List<String>): List<List<Long>> {
        val listOfMappingLists = mutableListOf<List<Long>>()

        var nextIndex = index + 1
        var nextLine: String = input[nextIndex]
        do {
            val mapping = nextLine.splitToLongList()
            val sourceRangeStart = mapping[SOURCE_RANGE_START]
            val destinationRangeStart = mapping[DESTINATION_RANGE_START]
            val range = mapping[RANGE]

            listOfMappingLists.add(listOf(sourceRangeStart, destinationRangeStart, range))

            nextIndex++
            if (nextIndex < input.size) nextLine = input[nextIndex] else break
        } while (nextLine.isNotEmpty())

        return listOfMappingLists
    }

    fun checkMaps(listsToCheck: List<List<Long>>, inputValue: Long): Long {
        listsToCheck.forEach { mapping ->
            val sourceStartValue = mapping.first()
            val destinationStartValue = mapping[1]
            val rangeLength = mapping.last()

            val sourceRange = sourceStartValue until sourceStartValue + rangeLength

            if (sourceRange.contains(inputValue)) {
                val destinationOffset = inputValue - sourceStartValue
                return destinationStartValue + destinationOffset
            }
        }

        return inputValue
    }

    // this works on the test input but time complexity is too large for real input
    fun part1BadTimeComplexity(input: List<String>): Long {
        // parse input
        val seedsString = input.first().split(": ").last()
        val startingSeedList = seedsString.splitToLongList()

        // map of source (key) to destination (value)
        var seedToSoilMap = mapOf<Long, Long>()
        var soilToFertilizerMap = mapOf<Long, Long>()
        var fertilizerToWaterMap = mapOf<Long, Long>()
        var waterToLightMap = mapOf<Long, Long>()
        var lightToTemperatureMap = mapOf<Long, Long>()
        var temperatureToHumidityMap = mapOf<Long, Long>()
        var humidityToLocationMap = mapOf<Long, Long>()

        // create mappings for each source -> destination
        input.forEachIndexed { index, line ->
            if (line == "seed-to-soil map:") {
                seedToSoilMap = mapAlmanac(index, input)
            } else if (line == "soil-to-fertilizer map:") {
                soilToFertilizerMap = mapAlmanac(index, input)
            } else if (line == "fertilizer-to-water map:") {
                fertilizerToWaterMap = mapAlmanac(index, input)
            } else if (line == "water-to-light map:") {
                waterToLightMap = mapAlmanac(index, input)
            } else if (line == "light-to-temperature map:") {
                lightToTemperatureMap = mapAlmanac(index, input)
            } else if (line == "temperature-to-humidity map:") {
                temperatureToHumidityMap = mapAlmanac(index, input)
            } else if (line == "humidity-to-location map:") {
                humidityToLocationMap = mapAlmanac(index, input)
            }
        }

        // get the locations by going through each map with each seed
        val locationList = mutableListOf<Long>() // stores the list of location values, in order of initial seed list
        startingSeedList.forEach { seed ->
            val soil = seedToSoilMap[seed] ?: seed
            val fertilizer = soilToFertilizerMap[soil] ?: soil
            val water = fertilizerToWaterMap[fertilizer] ?: fertilizer
            val light = waterToLightMap[water] ?: water
            val temperature = lightToTemperatureMap[light] ?: light
            val humidity = temperatureToHumidityMap[temperature] ?: temperature
            val location = humidityToLocationMap[humidity] ?: humidity

            locationList.add(location)
        }

        return locationList.minOf { it } // find the lowest location value of all the seeds
    }

    fun part1(input: List<String>): Long {

        // parse input
        val seedsString = input.first().split(": ").last()
        val startingSeedList = seedsString.splitToLongList()

        // holds list of
        var seedToSoilLists = listOf<List<Long>>()
        var soilToFertilizerLists = listOf<List<Long>>()
        var fertilizerToWaterLists = listOf<List<Long>>()
        var waterToLightLists = listOf<List<Long>>()
        var lightToTemperatureLists = listOf<List<Long>>()
        var temperatureToHumidityLists = listOf<List<Long>>()
        var humidityToLocationLists = listOf<List<Long>>()


        // create mappings for each source -> destination
        input.forEachIndexed { index, line ->
            if (line == "seed-to-soil map:") {
                seedToSoilLists = getAlmanacLists(index, input)
            } else if (line == "soil-to-fertilizer map:") {
                soilToFertilizerLists = getAlmanacLists(index, input)
            } else if (line == "fertilizer-to-water map:") {
                fertilizerToWaterLists = getAlmanacLists(index, input)
            } else if (line == "water-to-light map:") {
                waterToLightLists = getAlmanacLists(index, input)
            } else if (line == "light-to-temperature map:") {
                lightToTemperatureLists = getAlmanacLists(index, input)
            } else if (line == "temperature-to-humidity map:") {
                temperatureToHumidityLists = getAlmanacLists(index, input)
            } else if (line == "humidity-to-location map:") {
                humidityToLocationLists = getAlmanacLists(index, input)
            }
        }


        // get the locations by going through each map with each seed
        val locationList = mutableListOf<Long>() // stores the list of location values, in order of initial seed list
        startingSeedList.forEach { seed ->
            val soil = checkMaps(seedToSoilLists, seed)
            val fertilizer = checkMaps(soilToFertilizerLists, soil)
            val water = checkMaps(fertilizerToWaterLists, fertilizer)
            val light = checkMaps(waterToLightLists, water)
            val temperature = checkMaps(lightToTemperatureLists, light)
            val humidity = checkMaps(temperatureToHumidityLists, temperature)
            val location = checkMaps(humidityToLocationLists, humidity)


            locationList.add(location)
        }

        return locationList.minOf { it } // find the lowest location value of all the seeds
    }

    // this works but is very slow (~15 mins)
    fun part2(input: List<String>): Long {
        // parse input
        val seedsString = input.first().split(": ").last()
        val startingSeedList = seedsString.splitToLongList()
        val startingSeedRangeLists = startingSeedList.chunked(2)

        // holds list of pairs of starting values and ranges
        var seedToSoilLists = listOf<List<Long>>()
        var soilToFertilizerLists = listOf<List<Long>>()
        var fertilizerToWaterLists = listOf<List<Long>>()
        var waterToLightLists = listOf<List<Long>>()
        var lightToTemperatureLists = listOf<List<Long>>()
        var temperatureToHumidityLists = listOf<List<Long>>()
        var humidityToLocationLists = listOf<List<Long>>()


        // create mappings for each source -> destination
        input.forEachIndexed { index, line ->
            if (line == "seed-to-soil map:") {
                seedToSoilLists = getAlmanacLists(index, input)
            } else if (line == "soil-to-fertilizer map:") {
                soilToFertilizerLists = getAlmanacLists(index, input)
            } else if (line == "fertilizer-to-water map:") {
                fertilizerToWaterLists = getAlmanacLists(index, input)
            } else if (line == "water-to-light map:") {
                waterToLightLists = getAlmanacLists(index, input)
            } else if (line == "light-to-temperature map:") {
                lightToTemperatureLists = getAlmanacLists(index, input)
            } else if (line == "temperature-to-humidity map:") {
                temperatureToHumidityLists = getAlmanacLists(index, input)
            } else if (line == "humidity-to-location map:") {
                humidityToLocationLists = getAlmanacLists(index, input)
            }
        }

        // get a list of ranges to check
        val startingSeedRangePairs = startingSeedRangeLists.map { it.first() to it.last() }

        var currentLocationMin = Long.MAX_VALUE
        startingSeedRangePairs.forEach { pair ->
            for (seed in pair.first until pair.first + pair.second) {
                val soil = checkMaps(seedToSoilLists, seed)
                val fertilizer = checkMaps(soilToFertilizerLists, soil)
                val water = checkMaps(fertilizerToWaterLists, fertilizer)
                val light = checkMaps(waterToLightLists, water)
                val temperature = checkMaps(lightToTemperatureLists, light)
                val humidity = checkMaps(temperatureToHumidityLists, temperature)
                val location = checkMaps(humidityToLocationLists, humidity)

                if (location < currentLocationMin) currentLocationMin = location
            }
        }

        return currentLocationMin // find the lowest location value of all the seeds
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day05_test")
    val testInput2 = readInput("Day05_test")
    check(part1(testInput1) == (35).toLong())
    check(part2(testInput2) == (46).toLong())

    // print solutions
    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
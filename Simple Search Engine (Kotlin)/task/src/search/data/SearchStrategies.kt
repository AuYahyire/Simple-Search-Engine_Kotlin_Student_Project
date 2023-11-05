package search.data
/**
 * Class that handles different search strategies for matching people in the data.
 *
 * @param fileData A list of strings representing the data to search in.
 */
class SearchStrategies(private val fileData: List<String>) {
    /**
     * Searches for matching people in the data and prints the results based on the chosen search strategy.
     *
     * @param data The inverted index for searching.
     * @param query The search query provided by the user.
     * @param strategy The search strategy ("ALL", "ANY", or "NONE") to use for matching.
     */
    fun searchAndPrintResults(data: MutableMap<String, MutableList<Int>>, query: String, strategy: String) {
        // Determine the matching indexes based on the chosen strategy
        val matchingIndexes = when (strategy) {
            "ALL" -> searchAll(data, query)
            "ANY" -> searchAny(data, query)
            "NONE" -> searchNone(data, query)
            else -> emptyList()
        }

        val size = matchingIndexes.size

        if (matchingIndexes.isNotEmpty()) {
            println("$size ${"person".plural(size)} found:")
            // Print the matching people based on the indexes
            matchingIndexes.forEach { index ->
                println(fileData[index])
            }
        } else {
            println("No matching people found.")
        }
    }

    /**
     * Searches for matching all the terms in the query using the inverted index.
     *
     * @param data The inverted index for searching.
     * @param query The search query provided by the user.
     * @return A list of matching indexes for all terms in the query.
     */
    private fun searchAll(data: MutableMap<String, MutableList<Int>>, query: String): List<Int> {
        val results = mutableListOf<Int>()
        val queryTokens = query.split(" ")
        for (token in queryTokens) {
            data[token]?.forEach { results.add(it) } ?: continue
        }
        return mostExactMatch(results)
    }

    /**
     * Finds the most exact matches from a list of matching indexes.
     *
     * @param results A list of matching indexes.
     * @return A list of indexes with the most exact matches.
     */
    private fun mostExactMatch(results: List<Int>): List<Int> {
        val frequenciesGrouping = results.groupingBy { it }
        val frequencies = frequenciesGrouping.eachCount()
        val maxFrequencies = frequencies.values.maxOrNull() ?: 0
        return frequencies.filterValues { it == maxFrequencies }.keys.toList()
    }

    /**
     * Searches for matching any of the terms in the query using the inverted index.
     *
     * @param data The inverted index for searching.
     * @param query The search query provided by the user.
     * @return A set of matching indexes for any of the terms in the query.
     */
    private fun searchAny(data: MutableMap<String, MutableList<Int>>, query: String): Set<Int> {
        val results = mutableSetOf<Int>()
        val queryTokens = query.split(" ")
        for (token in queryTokens) {
            data[token]?.forEach { results.add(it) } ?: continue
        }
        return results.toSet()
    }

    /**
     * Searches for matching none of the terms in the query using the inverted index.
     *
     * @param data The inverted index for searching.
     * @param query The search query provided by the user.
     * @return A set of indexes that do not match any of the terms in the query.
     */
    private fun searchNone(data: MutableMap<String, MutableList<Int>>, query: String): Set<Int> {
        val results = mutableSetOf<Int>()
        for (index in fileData.indices) {
            results.add(index)
        }
        val queryTokens = query.split(" ")
        for (token in queryTokens) {
            data[token]?.forEach { results.remove(it) } ?: continue
        }
        return results.toSet()
    }

    /**
     * Adds the plural form to a string based on the given count.
     *
     * @param i The count to determine singular or plural form.
     * @return The original string in plural form if `i` is greater than 1, otherwise the original string.
     */
    private fun String.plural(i: Int): String {
        return if (i > 1) this + "s" else this
    }
}
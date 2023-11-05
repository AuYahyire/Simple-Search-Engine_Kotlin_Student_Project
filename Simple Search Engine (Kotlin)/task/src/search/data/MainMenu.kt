package search.data

/**
 * Represents the main menu of a search application.
 *
 * @property invertedIndex A mutable map that stores word-to-document index mappings for searching.
 * @property fileData A list of strings representing the data to be searched.
 */
class MainMenu(private val invertedIndex: MutableMap<String, MutableList<Int>>, private val fileData: List<String>) {
    // The available menu options
    private val menuOptions = listOf("Find a person", "Print all people", "Exit")
    private val searchStrategies = SearchStrategies(fileData)

    /**
     * Displays the main menu and handles user interactions.
     */
    fun menu() {
        while (true) {
            println("\n=== Menu ===")
            menuOptions.forEachIndexed { index, option ->
                if (option != "Exit") println("${index + 1}. $option") else println("0. $option")
            }

            // Read user's choice from input
            val choice = readlnOrNull()?.toInt() ?: -1

            when (choice) {
                1 -> {
                    println("Select a matching strategy: ALL, ANY, NONE")
                    val strategy = readln().uppercase()
                    println("Enter a name or email to search all matching people.")
                    val query = readlnOrNull()?.lowercase() ?: ""
                    // Search and print results based on user's query
                    searchStrategies.searchAndPrintResults(invertedIndex, query, strategy)
                }

                2 -> {
                    println("\n=== List of people ===")
                    // Display all people in the list
                    fileData.forEach { println(it) }
                }

                0 -> {
                    println("Bye!")
                    return
                }

                else -> {
                    println("Incorrect option! Try again.")
                }
            }
        }
    }

}



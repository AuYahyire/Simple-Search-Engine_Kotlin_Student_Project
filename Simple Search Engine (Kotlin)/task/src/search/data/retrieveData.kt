package search.data

import java.io.File

/**
 * A utility class for retrieving and processing data for the search application.
 *
 * @property arguments An array of command-line arguments passed to the application.
 */
class retrieveData(private val arguments: Array<String>) {
    /**
     * Parses command-line arguments to extract the data file path.
     *
     * @return A mutable list containing the data file path.
     */
    fun parseArguments(): MutableList<String> {
        // Default file path
        var fileData = DEFAULT_FILE

        // Store parsed and organized arguments.
        val parsedArguments = mutableListOf<String>()

        // Process command-line arguments.
        for (i in arguments.indices) {
            when (arguments[i]) {
                "--data" -> fileData = arguments[i + 1]
                else -> continue
            }
        }

        // Add parsed data file path to the list.
        parsedArguments.add(fileData)

        return parsedArguments
    }

    /**
     * Reads the content of a file at the specified path and returns it as a list of strings.
     *
     * @param path The path to the file to be read.
     * @return A list of strings containing the lines of the file.
     */
    fun readFile(path: String): List<String> {
        val file = File(path)

        // Check if the file exists and is a regular file (optional, commented out).
        /*if (!file.exists() || !file.isFile) {
            println("File does not exist or is not a regular file.")
            return emptyList()
        }*/

        return file.readLines()
    }

    /**
     * Creates an inverted index for the given list of strings (data).
     *
     * @param fileData A list of strings representing the data to be indexed.
     * @return A mutable map where keys are normalized words and values are lists of line indexes containing those words.
     */
    fun invertedIndex(fileData: List<String>): MutableMap<String, MutableList<Int>> {
        // Create an inverted index
        val invertedIndex = mutableMapOf<String, MutableList<Int>>()

        // Build the inverted index
        for (lineIndex in fileData.indices) {
            val line = fileData[lineIndex]
            val words = line.split(" ")
            for (word in words) {
                val normalizedWord = word.lowercase() // Normalize to lowercase
                invertedIndex.computeIfAbsent(normalizedWord) { mutableListOf() }.add(lineIndex)
            }
        }

        return invertedIndex
    }

}

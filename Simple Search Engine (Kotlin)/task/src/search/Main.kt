package search

import search.data.MainMenu
import search.data.retrieveData

fun main(args: Array<String>) {
    // Create an instance of the retrieveData class to process command-line arguments and data retrieval.
    val data = retrieveData(args)

    // Parse command-line arguments and obtain the data file path.
    val sortedArgs = data.parseArguments()

    // Read the content of the data file specified in the command-line arguments.
    val dataInFile = data.readFile(sortedArgs[0])

    // Build an inverted index based on the data from the file.
    val invertedIndex = data.invertedIndex(dataInFile)

    // Create and display the main menu with the inverted index and data.
    MainMenu(invertedIndex, dataInFile).menu()
}





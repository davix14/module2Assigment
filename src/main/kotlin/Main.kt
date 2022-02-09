fun main() {
    println("*******\tStarting to Read: \"The Raven\"\t*******\n")
    val wordCounter = WordCounter("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm")
    wordCounter.getDocAndWords()
    println("\n*******\tTHE END\t*******")
}
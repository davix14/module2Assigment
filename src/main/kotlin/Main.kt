fun main(args: Array<String>) {
    println("Hello World!")
    println()
    val wordCounter = WordCounter("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm")
    wordCounter.getDocAndWords()
}
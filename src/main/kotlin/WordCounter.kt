import org.jsoup.Jsoup
import java.net.URL
import java.util.*
import kotlin.collections.LinkedHashMap

class WordCounter constructor(
    url: String
) {
    private val docUrl: URL
    private lateinit var scnr1: Scanner

    init {
        //  create URL obj and Scanner obj to get the file
        docUrl = URL(url)

        //  add error handling in case of error getting the file
        try {
            scnr1 = Scanner(docUrl.openStream())
        } catch (e: Exception) {
            println("ERROR: Failed to get doc")
        }
    }

    private lateinit var scnr2: Scanner

    //  hashmap to store words and count
    private var wordsMap = hashMapOf<String, Int>()

    //  flag to exit the loops once poem is read
    private var proceed = true

    //  constants object for the start and end markers
    object PoemConstants {
        const val START_OF_POEM = "by Edgar Allan Poe"
        const val END_OF_POEM = "***"
    }

    fun getDocAndWords() {
        //  first loop to read the first lines before the poem begins
        while (proceed) {
            //  get next line and remove html markup
            var nextLine = scnr1.nextLine()
            nextLine = Jsoup.parse(nextLine).text()
            //  check if we are at the line before the first word of the poem
            if (nextLine == PoemConstants.START_OF_POEM) {
                //  call the readPoem method until the flag changes
                while (proceed) {
                    readPoem()
                }
            }
        }
        printWordCount()
    }

    private fun readPoem() {
        //  get the next line and strip html markup
        scnr2 = Scanner(Jsoup.parse(scnr1.nextLine()).text())
        //  while there are more words in this line loop
        while (scnr2.hasNext()) {
            // get the next word
            val nextWord = scnr2.next()
            //  if the nextWord is the matches the word after the last word of the poem break the loop and set the flag to false to break all loops and exit
            if (nextWord == PoemConstants.END_OF_POEM) {
                proceed = false
                break
            }
            //  pass word to the clean word to strip all punctuation and then pass to the method that counts the word occurrences
            addToWordCount(cleanWord(nextWord))
        }
    }

    private fun cleanWord(wordIn: String): String {
        val regex = Regex("[^A-Za-z0-9 ]")
        return regex.replace(wordIn, "").lowercase(Locale.getDefault())
    }

    private fun addToWordCount(wordIn: String) {
        val count = if (wordsMap.containsKey(wordIn)) (wordsMap[wordIn]?.plus(1)) else 1
        wordsMap[wordIn] = count!!
    }

    private fun printWordCount() {
        val sortedMap = sortMap()
        print("Word Occurrences (Sorted by occurrence # from High to Low):\n\n")
        for (word in sortedMap) {
            print("Word: ${word.key}\t Occurrences: ${word.value}\n")
        }
    }

    private fun sortMap(): LinkedHashMap<String, Int> {
        val sortedMap = kotlin.collections.LinkedHashMap<String, Int>()
        val indexOrder = arrayListOf<String>()
        var maxValue = 0
        //  find largest value
        wordsMap.onEachIndexed { _, entry ->
            if (entry.value > maxValue) {
                maxValue = entry.value
            }
        }
        var counter = maxValue
        while (counter != 0) {
            wordsMap.onEachIndexed { _, entry ->
                if (entry.value == counter) {
                    indexOrder.add(entry.key)
                }
            }
            counter--
        }

        for (index in indexOrder) {
            wordsMap[index]?.let { sortedMap.put(index, it) }
        }

        return sortedMap
    }
}

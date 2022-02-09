import org.jsoup.Jsoup
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

class WordCounter constructor(
    url: String
) {

    //  create URL obj and Scanner obj to get the file
    private val docUrl: URL = URL(url)
    val scnr1 = Scanner(docUrl.openStream())
    lateinit var scnr2: Scanner
    //  hashmap to store words and count
    var wordsMap = HashMap<String, Int>()
    //  flag to exit the loops once poem is read
    var proceed = true

    object PoemConstants {
        const val START_OF_POEM = "by Edgar Allan Poe"
        const val END_OF_POEM = "***"
    }

    fun getDocAndWords() {
        while (proceed) {
            var nextLine = scnr1.nextLine()
            nextLine = Jsoup.parse(nextLine).text()
            if (nextLine == PoemConstants.START_OF_POEM) {
                println("START OF POEM")
                while(proceed) {
                    readPoem()
                }
            }
        }
        printWordCount()
    }

    private fun readPoem() {
        scnr2 = Scanner(Jsoup.parse(scnr1.nextLine()).text())
        while (scnr2.hasNext()) {
            val nextWord = scnr2.next()

            if (nextWord == PoemConstants.END_OF_POEM) {
                proceed = false
                break
            }
            println(cleanWord(nextWord))
            addToWordCount(cleanWord(nextWord))
        }
    }

    private fun cleanWord(wordIn: String): String{
        val regex = Regex("[^A-Za-z0-9 ]")
        return regex.replace(wordIn, "").lowercase(Locale.getDefault())
    }

    private fun addToWordCount(wordIn: String){
        val count = if(wordsMap.containsKey(wordIn)) (wordsMap[wordIn]?.plus(1)) else 1
        wordsMap[wordIn] = count!!
    }

    private fun printWordCount(){
        for(word in wordsMap){
            println(word)
        }
    }
}
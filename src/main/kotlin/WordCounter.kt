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
    var wordsMap = HashMap<String, Int>()
    lateinit var scnr2: Scanner
    var proceed = true

    object PoemConstants {
        const val START_OF_POEM = "by Edgar Allan Poe"
        const val END_OF_POEM = "lifted—nevermore!"
    }

    fun getDocAndWords() {
        while (proceed) {
            var nextLine = scnr1.nextLine()
            nextLine = Jsoup.parse(nextLine).text()
//            println(nextLine)
//            println(nextLine)
            if (nextLine == PoemConstants.START_OF_POEM) {
                println("START OF POEM")
                while(proceed) {
                    readPoem()
                }
            }

/*
            if (nextLine == PoemConstants.START_OF_POEM) {
                //  each loop will read the next line and parse the words from tha line
                while (proceed) {
                    readPoem()
                }
            }
*/
        }
        printWordCount()
    }

    private fun readPoem() {
        scnr2 = Scanner(Jsoup.parse(scnr1.nextLine()).text())
        while (scnr2.hasNext()) {
            var nextWord = scnr2.next()

            if (nextWord == PoemConstants.END_OF_POEM) {
                proceed = false
                break
            }
            println(nextWord)
            addToWordCount(nextWord)
        }
    }

    private fun addToWordCount(wordIn: String){
        val count = if(wordsMap.containsKey(wordIn)) wordsMap[wordIn] else 1
        wordsMap[wordIn] = count!!
    }

    private fun printWordCount(){
        wordsMap.toString()
    }
    //  START of poem <div class="chapter">
// END of poem </div><!--end chapter-->
}
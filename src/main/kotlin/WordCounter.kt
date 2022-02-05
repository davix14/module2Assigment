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
        const val START_OF_POEM = "<div class=\"chapter\">"
        const val END_OF_POEM = "</div><!--end"
    }

    fun getDocAndWords() {
        while (proceed) {
            var nextLine = scnr1.nextLine()
            if (nextLine == PoemConstants.START_OF_POEM) {
                //  each loop will read the next line and parse the words from tha line
                while (proceed) {
                    readPoem()
                }
            }
        }
    }

    private fun readPoem() {
        scnr2 = Scanner(scnr1.nextLine())
        while (scnr2.hasNext()) {
            var nextWord = scnr2.next()

            when(nextWord){

            }

            if (nextWord == PoemConstants.END_OF_POEM) {
                proceed = false
            }
        }
    }
    //  START of poem <div class="chapter">
// END of poem </div><!--end chapter-->
}
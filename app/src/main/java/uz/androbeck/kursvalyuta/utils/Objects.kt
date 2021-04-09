package uz.androbeck.kursvalyuta.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object Objects {

    fun document(url: String): Document {
        return Jsoup.connect(url).get()
    }
}
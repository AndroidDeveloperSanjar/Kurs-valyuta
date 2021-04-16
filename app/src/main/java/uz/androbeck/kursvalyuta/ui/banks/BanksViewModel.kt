package uz.androbeck.kursvalyuta.ui.banks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class BanksViewModel : ViewModel() {

    fun getMarkaziyBankValyuta(): LiveData<Elements?> = liveData {
        try {
            val element = withContext(IO) {
                Jsoup.connect("https://cbu.uz/oz/").get().getElementsByClass("exchange__item_value")
            }
            emit(element)
        } catch (e: Exception) {
            println("exception -> ${e.message}")
        }
    }
}
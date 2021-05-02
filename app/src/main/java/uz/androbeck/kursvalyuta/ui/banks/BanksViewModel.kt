package uz.androbeck.kursvalyuta.ui.banks

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.jsoup.select.Elements
import uz.androbeck.kursvalyuta.utils.Objects

class BanksViewModel : ViewModel() {
    fun getMarkaziyBankValyuta(): LiveData<Elements?> = liveData {
        try {
            val element = withContext(IO) {
                Objects.document("https://cbu.uz/oz/").getElementsByClass("exchange__item_value")
            }
            emit(element)
        } catch (e: Exception) {
            println("getMarkaziyBankValyuta -> exception -> ${e.message}")
            emit(null)
        }
    }
}
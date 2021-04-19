package uz.androbeck.kursvalyuta.ui.banks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
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
            println("exception -> ${e.message}")
            emit(null)
        }
    }

    fun getAsakaBankValyuta(): LiveData<Elements?> = liveData {
        try {
            val element = withContext(IO) {
                Objects.document("https://asakabank.uz/uz").getElementsByClass("table_col tar")
            }
            emit(element)
        } catch (e: Exception) {
            println("exception -> ${e.message}")
            emit(null)
        }
    }

    fun getIpotekaBankValyuta(): LiveData<Elements?> = liveData {
        try {
            val element = withContext(IO) {
                Objects.document("https://www.ipotekabank.uz/uzb/").getElementsByClass("usd")
            }
            emit(element)
        } catch (e: Exception) {
            println("exception -> ${e.message}")
            emit(null)
        }
    }

    fun getKapitalBankValyuta(): LiveData<Elements?> = liveData {
        try {
            val element = withContext(IO) {
                Objects.document("https://kapitalbank.uz/uz/welcome.php")
                    .getElementsByClass("items")
            }
            emit(element)
        } catch (e: Exception) {
            println("exception -> ${e.message}")
            emit(null)
        }
    }
}
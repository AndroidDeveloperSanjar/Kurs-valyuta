package uz.androbeck.kursvalyuta

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            supportActionBar?.setTitle(Html.fromHtml("<font color='#546E7A'>Kurs valyuta</font/g_medium>", 1))
        } else {
            supportActionBar?.setTitle(Html.fromHtml("<font color='#546E7A'>Kurs valyuta</font/g_medium>"))
        }
        setContentView(R.layout.activity_main)
    }
}
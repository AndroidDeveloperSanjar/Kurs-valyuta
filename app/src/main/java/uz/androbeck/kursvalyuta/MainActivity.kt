package uz.androbeck.kursvalyuta

import android.os.Bundle
import android.text.Html
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import uz.androbeck.kursvalyuta.adapter.MainAdapter
import uz.androbeck.kursvalyuta.adapter.model.MainModel
import uz.androbeck.kursvalyuta.databinding.ActivityMainBinding
import uz.androbeck.kursvalyuta.utils.Helper

class MainActivity : AppCompatActivity(), MainAdapter.MainAdapterListener {

    private lateinit var mainAdapter: MainAdapter

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            supportActionBar?.setTitle(
                Html.fromHtml(
                    "<font color='#546E7A'>Kurs valyuta</font/g_medium>",
                    1
                )
            )
        } else {
            supportActionBar?.setTitle(Html.fromHtml("<font color='#546E7A'>Kurs valyuta</font/g_medium>"))
        }
        setContentView(binding.root)
        initRV()
    }

    private fun initRV() {
        mainAdapter = MainAdapter(this)
        with(binding) {
            rv.layoutManager = LinearLayoutManager(this@MainActivity)
            rv.adapter = mainAdapter
            mainAdapter.submitList(Helper.dataList())
        }
    }

    override fun clickCalculate(position: Int, data: MainModel) {
        Toast.makeText(this, "Kalkulyator bosildi position -> #$position", Toast.LENGTH_SHORT)
            .show()
    }
}
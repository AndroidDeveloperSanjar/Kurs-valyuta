package uz.androbeck.kursvalyuta

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.baoyz.widget.PullRefreshLayout
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import uz.androbeck.kursvalyuta.adapter.MainAdapter
import uz.androbeck.kursvalyuta.adapter.model.MainModel
import uz.androbeck.kursvalyuta.databinding.ActivityMainBinding
import uz.androbeck.kursvalyuta.utils.Helper
import uz.androbeck.kursvalyuta.utils.Objects

class MainActivity : AppCompatActivity(), MainAdapter.MainAdapterListener,
    PullRefreshLayout.OnRefreshListener {

    private lateinit var mainAdapter: MainAdapter

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

        initRV()

        getAllDataWithJsoup()
    }

    private fun getAllDataWithJsoup() {
        CoroutineScope(IO).launch {
            getDataFromAgroBank()
            getDataFromAsakaBank()
        }
    }

    private fun getDataFromAgroBank() {
        val documentAgroBank = Objects.document("https://www.agrobank.uz/")
        val imageElementsAgroBank = documentAgroBank.getElementsByClass("header__logo-desktop")
        val logoAgroBank = imageElementsAgroBank[0].absUrl("src")
        println("agrobank -> ${logoAgroBank}")
    }

    private fun getDataFromAsakaBank() {
        val documentAsakaBank = Objects.document("https://asakabank.uz/uz")
        val imageElementsAsakaBank = documentAsakaBank.getElementsByTag("img")
        val logoAsakaBank = imageElementsAsakaBank[0].absUrl("src")
        println("asakbank -> ${logoAsakaBank}")
    }

    private fun init() {
        with(binding) {
            refreshLayout.setOnRefreshListener(this@MainActivity)
            refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN)
        }
    }

    private fun initRV() {
        mainAdapter = MainAdapter(this)
        with(binding) {
            rv.layoutManager = LinearLayoutManager(this@MainActivity)
            rv.adapter = mainAdapter
            mainAdapter.submitList(Helper.dataList())
            rv.scheduleLayoutAnimation()
        }
    }

    override fun clickCalculate(position: Int, data: MainModel) {
        toast("position #$position")
    }

    override fun onRefresh() {
        toast("Updated...")
        MainScope().launch {
            delay(3000L)
            binding.refreshLayout.setRefreshing(false)
        }
    }
}
package uz.androbeck.kursvalyuta.ui.connection

import android.content.Intent
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import uz.androbeck.kursvalyuta.databinding.ActivityConnectionBinding
import uz.androbeck.kursvalyuta.snackbar
import uz.androbeck.kursvalyuta.ui.banks.BanksActivity
import uz.androbeck.kursvalyuta.utils.NetworkLiveData

class ConnectionActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var networkLiveData: NetworkLiveData

    private val binding: ActivityConnectionBinding by viewBinding()

    private var isNetwork = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

    }

    private fun init() {
        networkLiveData = NetworkLiveData(this)

        checkInternet()

        with(binding) {
            swipeRefreshLayout.setOnRefreshListener(this@ConnectionActivity)
        }
    }

    private fun checkInternet() {
        with(binding) {
            networkLiveData.observe(this@ConnectionActivity, { network ->
                if (network != null) {
                    if (network) {
                        startActivity(Intent(this@ConnectionActivity, BanksActivity::class.java))
                        swipeRefreshLayout.isRefreshing = false
                        isNetwork = true
                    } else {
                        binding.root.snackbar("Iltimos internetni yoqib keyin urinib ko'ring!")
                        swipeRefreshLayout.isRefreshing = false
                        isNetwork = false
                    }
                }
            })
        }
    }

    override fun onBackPressed() {
        if (isNetwork)
            super.onBackPressed()
        else
            binding.root.snackbar("Iltimos internetni yoqib keyin urinib ko'ring!")
    }

    override fun onRefresh() {
        checkInternet()
    }
}
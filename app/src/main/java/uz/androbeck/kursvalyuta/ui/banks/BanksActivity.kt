package uz.androbeck.kursvalyuta.ui.banks

import android.content.Intent
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import uz.androbeck.kursvalyuta.MainActivity
import uz.androbeck.kursvalyuta.adapter.BanksAdapter
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.databinding.ActivityBanksBinding
import uz.androbeck.kursvalyuta.utils.Helper

class BanksActivity : AppCompatActivity(), BanksAdapter.BanksAdapterListener {

    private val binding: ActivityBanksBinding by viewBinding()

    private lateinit var banksAdapter: BanksAdapter

    private var dataList = ArrayList<BanksModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRV()

        //getAllDataWithJsoup()
    }

    private fun initRV() {
        banksAdapter = BanksAdapter(this)
        with(binding) {
            rv.layoutManager = LinearLayoutManager(this@BanksActivity)
            rv.adapter = banksAdapter
            banksAdapter.submitList(Helper.banksLogoAndNameList())
            binding.rv.scheduleLayoutAnimation()
        }
    }

    override fun itemClick(position: Int, data: BanksModel) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
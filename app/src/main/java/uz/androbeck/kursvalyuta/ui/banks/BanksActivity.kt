package uz.androbeck.kursvalyuta.ui.banks

import android.annotation.SuppressLint
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.baoyz.widget.PullRefreshLayout
import kotlinx.coroutines.*
import uz.androbeck.kursvalyuta.DialogListener
import uz.androbeck.kursvalyuta.R
import uz.androbeck.kursvalyuta.adapter.BanksAdapter
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.databinding.ActivityBanksBinding
import uz.androbeck.kursvalyuta.db.preferences.PreferencesManager
import uz.androbeck.kursvalyuta.toast
import uz.androbeck.kursvalyuta.ui.dialogs.Dialogs
import uz.androbeck.kursvalyuta.utils.Helper

@SuppressLint("SetTextI18n")
class BanksActivity : AppCompatActivity(), BanksAdapter.BanksAdapterListener,
    PullRefreshLayout.OnRefreshListener, DialogListener {

    private val binding: ActivityBanksBinding by viewBinding()

    private lateinit var banksAdapter: BanksAdapter

    private lateinit var preferenceManager: PreferencesManager

    private lateinit var dialogs: Dialogs

    private val viewModel: BanksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

        initRV()

        clicks()
    }

    private fun init() {
        preferenceManager = PreferencesManager(this)

        setKursTypeToTV()

        dialogs = Dialogs(this)

        with(binding) {
            refreshLayout.setOnRefreshListener(this@BanksActivity)
            refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN)
        }

        with(binding) {
            viewModel.getMarkaziyBankValyuta().observe(this@BanksActivity, {
                if (it != null) {
                    when (preferenceManager.kursDialogValyuta) {
                        0 -> {

                        }
                        1 -> {

                        }
                        2 -> {

                        }
                    }
                    println("dsadsa -> " + it[0].text())
                    println("dsadsa -> " + it[1].text())
                }
            })
        }
    }

    private fun clicks() {
        with(binding) {
            ivKursTypeUpdate.setOnClickListener {
                dialogs.showDialog(this@BanksActivity, preferenceManager, tvKursType, ivKursType)
            }
        }
    }

    private fun setKursTypeToTV() {
        with(binding) {
            when (preferenceManager.kursDialogValyuta) {
                0 -> {
                    tvKursType.text = "Dollar"
                    ivKursType.setImageResource(R.drawable.ic_kurs_dollar)
                }
                1 -> {
                    tvKursType.text = "Ruble"
                    ivKursType.setImageResource(R.drawable.ic_kurs_ruble)
                }
                2 -> {
                    tvKursType.text = "Euro"
                    ivKursType.setImageResource(R.drawable.ic_kurs_euro)
                }
            }
        }
    }

    private fun initRV() {
        banksAdapter = BanksAdapter(this, preferenceManager.kursDialogValyuta)
        with(binding) {
            rv.layoutManager = LinearLayoutManager(this@BanksActivity)
            rv.adapter = banksAdapter
            banksAdapter.submitList(Helper.banksLogoAndNameList())
            binding.rv.scheduleLayoutAnimation()
        }
    }

    override fun itemClick(position: Int, data: BanksModel) {

    }

    override fun onRefresh() {
        toast("Updated...")
        MainScope().launch {
            delay(3000L)
            binding.refreshLayout.setRefreshing(false)
        }
    }

    override fun itemKursTypeDialogClick() {
        initRV()
    }
}
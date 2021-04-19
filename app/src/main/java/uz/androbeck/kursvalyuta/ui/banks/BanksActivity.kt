package uz.androbeck.kursvalyuta.ui.banks

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.baoyz.widget.PullRefreshLayout
import kotlinx.coroutines.*
import uz.androbeck.kursvalyuta.DialogListener
import uz.androbeck.kursvalyuta.R
import uz.androbeck.kursvalyuta.adapter.BanksAdapter
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.databinding.ActivityBanksBinding
import uz.androbeck.kursvalyuta.db.preferences.PreferencesManager
import uz.androbeck.kursvalyuta.ui.banks.item.ItemBankActivity
import uz.androbeck.kursvalyuta.ui.connection.ConnectionActivity
import uz.androbeck.kursvalyuta.ui.dialogs.Dialogs
import uz.androbeck.kursvalyuta.utils.NetworkLiveData
import uz.androbeck.kursvalyuta.visible

@SuppressLint("SetTextI18n")
class BanksActivity : AppCompatActivity(), BanksAdapter.BanksAdapterListener,
    PullRefreshLayout.OnRefreshListener, DialogListener {

    private val binding: ActivityBanksBinding by viewBinding()

    private lateinit var banksAdapter: BanksAdapter

    private lateinit var preferenceManager: PreferencesManager

    private lateinit var dialogs: Dialogs

    private val viewModel: BanksViewModel by viewModels()

    private val dataList = ArrayList<BanksModel>()

    private lateinit var networkLiveData: NetworkLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

        initRV()

        clicks()

        networkLiveData.observe(this, {
            if (it != null) {
                if (it) {
                    observeDataFromMarkaziyBank()

                    observeDataFromAsakaBank()

                    binding.cvProgress.visible(true)
                } else
                    startActivity(Intent(this, ConnectionActivity::class.java))
            } else
                startActivity(Intent(this, ConnectionActivity::class.java))
        })
    }

    private fun init() {
        networkLiveData = NetworkLiveData(this)

        preferenceManager = PreferencesManager(this)

        dialogs = Dialogs(this)

        with(binding) {
            refreshLayout.setOnRefreshListener(this@BanksActivity)
            refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN)
        }
    }

    private fun observeDataFromMarkaziyBank() {
        with(binding) {
            viewModel.getMarkaziyBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    tvCbuUsd.text = elements[0].text()
                    tvCbuEur.text = elements[1].text()
                    tvCbuRub.text = elements[2].text()
                    tvCbuGbp.text = elements[3].text()
                    tvCbuJpy.text = elements[4].text()
                    tvCbuChf.text = elements[5].text()
                }
            })
        }
    }

    private fun observeDataFromAsakaBank() {
        viewModel.getAsakaBankValyuta().observe(this, { elements ->
            if (elements != null) {
                val dollarBuy = elements[2].text()
                val dollarSale = elements[3].text()
                val euroBuy = elements[4].text()
                val euroSale = elements[5].text()
                val gbpBuy = elements[6].text()
                val gbpSale = elements[7].text()
                val chfBuy = elements[8].text()
                val chfSale = elements[9].text()
                val jpyBuy = elements[10].text()
                val jpySale = elements[11].text()
                val rubBuy = elements[12].text()
                val rubSale = elements[13].text()
                val usdAtmBuy = elements[14].text()
                val usdAtmSale = elements[15].text()
                println("$chfBuy | $chfSale | $jpyBuy | $jpySale | $rubBuy | $rubSale | $usdAtmBuy | $usdAtmSale")
                dataList.add(
                    BanksModel(
                        banksLogo = R.drawable.asaka_bank_logo,
                        bankName = "Asaka bank",
                        buyUsd = "$dollarBuy so'm",
                        saleUsd = "$dollarSale so'm",
                        buyEur = "$euroBuy so'm",
                        saleEur = "$euroSale so'm",
                        buyGbp = "$gbpBuy so'm",
                        saleGbp = "$gbpSale so'm",
                        buyChf = "$chfBuy so'm",
                        saleChf = "$chfSale so'm",
                        buyJpy = "$jpyBuy so'm",
                        saleJpy = "$jpySale so'm",
                        buyRub = "$rubBuy so'm",
                        saleRub = "$rubSale so'm",
                        buyUsdAtm = "$usdAtmBuy so'm",
                        saleUsdAtm = "$usdAtmSale so'm"
                    )
                )
                observeDataFromIpotekaBank()
            }
        })
    }

    private fun observeDataFromIpotekaBank() {
        viewModel.getIpotekaBankValyuta().observe(this, { elements ->
            if (elements != null) {
                val buyUsd =
                    elements[0].getElementsByClass("purchase")[0].getElementsByClass("corrupt")
                        .text()
                val saleUsd =
                    elements[0].getElementsByClass("purchase")[1].getElementsByTag("span")[0].text()
                val buyEur =
                    elements[0].getElementsByClass("purchase")[0].getElementsByTag("span")[1].text()
                val saleEur =
                    elements[0].getElementsByClass("purchase")[1].getElementsByTag("span")[1].text()
                val buyGbp =
                    elements[0].getElementsByClass("purchase")[0].getElementsByTag("span")[2].text()
                val saleGbp =
                    elements[0].getElementsByClass("purchase")[1].getElementsByTag("span")[2].text()
                dataList.add(
                    BanksModel(
                        banksLogo = R.drawable.ipoteka_bank_logo,
                        bankName = "Ipoteka bank",
                        buyUsd = "$buyUsd so'm",
                        saleUsd = "$saleUsd so'm",
                        buyEur = "$buyEur so'm",
                        saleEur = "$saleEur so'm",
                        buyGbp = "$buyGbp so'm",
                        saleGbp = "$saleGbp so'm",
                    )
                )
                observeDataFromKapitalBank()
            }
        })
    }

    private fun observeDataFromKapitalBank() {
        viewModel.getKapitalBankValyuta().observe(this) { elements ->
            if (elements != null) {
                val buyUsd =
                    elements[0].getElementsByClass("item-desc")[0].getElementsByTag("span")[0].text()
                val saleUsd =
                    elements[0].getElementsByClass("item-desc")[0].getElementsByTag("span")[2].text()
                val buyEur =
                    elements[0].getElementsByClass("item-desc")[1].getElementsByTag("span")[0].text()
                val saleEur =
                    elements[0].getElementsByClass("item-desc")[1].getElementsByTag("span")[2].text()
                val buyGbp =
                    elements[0].getElementsByClass("item-desc")[2].getElementsByTag("span")[0].text()
                val saleGbp =
                    elements[0].getElementsByClass("item-desc")[2].getElementsByTag("span")[2].text()
                dataList.add(
                    BanksModel(
                        banksLogo = R.drawable.kapital_bank_logo,
                        bankName = "Kapital bank",
                        buyUsd = "$buyUsd so'm",
                        saleUsd = "$saleUsd so'm",
                        buyEur = "$buyEur so'm",
                        saleEur = "$saleEur so'm",
                        buyGbp = "$buyGbp so'm",
                        saleGbp = "$saleGbp so'm",
                    )
                )
                println(dataList)
                binding.cvCbu.visible(true)
                binding.cvProgress.visible(false)
                banksAdapter.submitList(dataList)
                binding.refreshLayout.setRefreshing(false)
            }
        }
    }

    private fun clicks() {
        with(binding) {
            ivKursTypeUpdate.setOnClickListener {
                dialogs.showDialog(this@BanksActivity, preferenceManager)
            }
        }
    }

    private fun initRV() {
        banksAdapter = BanksAdapter(this)
        with(binding) {
            rv.layoutManager = LinearLayoutManager(this@BanksActivity)
            rv.adapter = banksAdapter
        }
    }

    override fun itemClick(position: Int, data: BanksModel) {
        binding.refreshLayout.setRefreshing(false)
        val intent = Intent(this, ItemBankActivity::class.java)
        intent.putExtra("data_intent", bundleOf("data" to data))
        startActivity(intent)
    }

    override fun onRefresh() {
        dataList.clear()
        networkLiveData.observe(this, {
            if (it != null) {
                if (it) {
                    observeDataFromMarkaziyBank()

                    observeDataFromAsakaBank()
                } else
                    startActivity(Intent(this, ConnectionActivity::class.java))
            } else
                startActivity(Intent(this, ConnectionActivity::class.java))
        })
    }

    override fun itemKursTypeDialogClick() {

    }
}
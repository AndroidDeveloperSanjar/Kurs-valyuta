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
import uz.androbeck.kursvalyuta.*
import uz.androbeck.kursvalyuta.adapter.BanksAdapter
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.databinding.ActivityBanksBinding
import uz.androbeck.kursvalyuta.db.preferences.PreferencesManager
import uz.androbeck.kursvalyuta.ui.banks.item.ItemBankActivity
import uz.androbeck.kursvalyuta.ui.connection.ConnectionActivity
import uz.androbeck.kursvalyuta.ui.dialogs.Dialogs
import uz.androbeck.kursvalyuta.utils.NetworkLiveData

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

    private var isLoaded = false

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

                    observeDataFromIpotekaBank()

                    observeDataFromKapitalBank()

                    observeQishloqQurilishBank()

                    observeSanoatQurilishBank()

                    binding.progressBar.visible(true)
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
            progressBar.visible(true)
            isLoaded = false
            viewModel.getMarkaziyBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    tvCbuUsd.text = elements[0].text()
                    tvCbuEur.text = elements[1].text()
                    tvCbuRub.text = elements[2].text()
                    tvCbuGbp.text = elements[3].text()
                    tvCbuJpy.text = elements[4].text()
                    tvCbuChf.text = elements[5].text()
                    progressBar.visible(false)
                    cvCbu.visible(true)
                    isLoaded = true
                } else {
                    progressBar.visible(false)
                    isLoaded = true
                }
            })
        }
    }

    private fun observeDataFromAsakaBank() {
        with(binding) {
            progressBar.visible(true)
            isLoaded = false
            viewModel.getAsakaBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    val buyUsd = elements[2].text()
                    val saleUsd = elements[3].text()
                    val buyEur = elements[4].text()
                    val saleEur = elements[5].text()
                    val buyGbp = elements[6].text()
                    val saleGbp = elements[7].text()
                    val buyChf = elements[8].text()
                    val saleChf = elements[9].text()
                    val buyJpy = elements[10].text()
                    val saleJby = elements[11].text()
                    val buyRub = elements[12].text()
                    val saleRub = elements[13].text()
                    val buyUsdAtm = elements[14].text()
                    val saleUsdAtm = elements[15].text()
                    dataList.add(
                        BanksModel(
                            banksLogo = R.drawable.asaka_bank_logo,
                            bankName = "Asaka bank",
                            buyUsd = buyUsd.replace(" ", "").substring(0, 5),
                            saleUsd = saleUsd.replace(" ", "").substring(0, 5),
                            buyEur = buyEur.replace(" ", "").substring(0, 5),
                            saleEur = saleEur.replace(" ", "").substring(0, 5),
                            buyGbp = buyGbp.replace(" ", "").substring(0, 5),
                            saleGbp = saleGbp.replace(" ", "").substring(0, 5),
                            buyChf = buyChf.replace(" ", "").substring(0, 5),
                            saleChf = saleChf.replace(" ", "").substring(0, 5),
                            buyJpy = buyJpy.substring(0, 3).replace(",", ""),
                            saleJpy = saleJby.substring(0, 3).replace(",", ""),
                            buyRub = buyRub.substring(0, 3).replace(",", ""),
                            saleRub = saleRub.substring(0, 3).replace(",", ""),
                            buyUsdAtm = buyUsdAtm.replace(" ", "").substring(0, 5),
                            saleUsdAtm = saleUsdAtm.replace(" ", "").substring(0, 5)
                        )
                    )
                    banksAdapter.submitList(dataList)
                    banksAdapter.notifyDataSetChanged()
                    progressBar.visible(false)
                    isLoaded = true
                } else {
                    progressBar.visible(false)
                    isLoaded = true
                }
            })
        }
    }

    private fun observeDataFromIpotekaBank() {
        with(binding) {
            progressBar.visible(true)
            isLoaded = false
            viewModel.getIpotekaBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    val buyUsd =
                        elements[0].getElementsByClass("purchase")[0].getElementsByClass("corrupt")[0]
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
                    println("djkal -> $buyUsd")
                    dataList.add(
                        BanksModel(
                            banksLogo = R.drawable.ipoteka_bank_logo,
                            bankName = "Ipoteka bank",
                            buyUsd = buyUsd.substring(0, 5).replace(" ", ""),
                            saleUsd = saleUsd.substring(0, 5).replace(" ", ""),
                            buyEur = buyEur.substring(0, 5).replace(" ", ""),
                            saleEur = saleEur.substring(0, 5).replace(" ", ""),
                            buyGbp = buyGbp.substring(0, 5).replace(" ", ""),
                            saleGbp = saleGbp.substring(0, 5).replace(" ", "")
                        )
                    )
                    banksAdapter.submitList(dataList)
                    banksAdapter.notifyDataSetChanged()
                    progressBar.visible(false)
                    isLoaded = true
                } else {
                    progressBar.visible(false)
                    isLoaded = true
                }
            })
        }
    }

    private fun observeDataFromKapitalBank() {
        with(binding) {
            progressBar.visible(true)
            isLoaded = false
            viewModel.getKapitalBankValyuta().observe(this@BanksActivity) { elements ->
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
                    val buyRub =
                        elements[0].getElementsByClass("item-desc")[3].getElementsByTag("span")[0].text()
                    val saleRub =
                        elements[0].getElementsByClass("item-desc")[3].getElementsByTag("span")[2].text()
                    dataList.add(
                        BanksModel(
                            banksLogo = R.drawable.kapital_bank_logo,
                            bankName = "Kapital bank",
                            buyUsd = buyUsd.substring(0, 5).replace(" ", ""),
                            saleUsd = saleUsd.substring(0, 5).replace(" ", ""),
                            buyEur = buyEur.substring(0, 5).replace(" ", ""),
                            saleEur = saleEur.substring(0, 5).replace(" ", ""),
                            buyGbp = buyGbp.substring(0, 5).replace(" ", ""),
                            saleGbp = saleGbp.substring(0, 5).replace(" ", ""),
                            buyRub = buyRub.substring(0, 3).replace(",", ""),
                            saleRub = saleRub.substring(0, 3).replace(",", "")
                        )
                    )
                    banksAdapter.submitList(dataList)
                    banksAdapter.notifyDataSetChanged()
                    isLoaded = true
                    progressBar.visible(false)
                } else {
                    isLoaded = true
                    progressBar.visible(false)
                }
            }
        }
    }

    private fun observeQishloqQurilishBank() {
        println("jdksajdakl")
        with(binding) {
            progressBar.visible(true)
            isLoaded = false
            viewModel.getQishloqQurilishBankValyuta().observe(this@BanksActivity, { elements ->
              if (elements != null){
                  println(elements.text())
                  //banksAdapter.submitList(dataList)
                  //banksAdapter.notifyDataSetChanged()
                  //isLoaded = true
                  //progressBar.visible(false)
              }else{
                  isLoaded = true
                  progressBar.visible(false)
              }
            })
        }
    }

    private fun observeSanoatQurilishBank() {
        with(binding) {
            progressBar.visible(true)
            isLoaded = false
            viewModel.getSanoatQurilishBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    println(elements.text())
                    //banksAdapter.submitList(dataList)
                    //banksAdapter.notifyDataSetChanged()
                    //isLoaded = true
                    //progressBar.visible(false)
                } else {
                    isLoaded = true
                    progressBar.visible(false)
                }
            })
        }
    }

    private fun clicks() {
        with(binding) {
            ivKursTypeUpdate.setOnClickListener {
                dialogs.showDialog(this@BanksActivity)
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
        if (isLoaded) {
            binding.refreshLayout.setRefreshing(false)
            val intent = Intent(this, ItemBankActivity::class.java)
            intent.putExtra("data_intent", bundleOf("data" to data))
            startActivity(intent)
        } else {
            binding.root.snackbar("Iltimos ma'lumotlar yuklanib bo'lishini kuting!")
        }
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

    override fun itemBuyOrSaleValyutaDialogClick(buyOrSale: String) {
        dialogs.showBuyAndSaleAllValyutaDialog(this, buyOrSale)
    }

    override fun itemAllKursTypeDialogClick(buyOrSale: String, typeKursValyuta: String) {
        toast("$buyOrSale | $typeKursValyuta", true)
        when (buyOrSale) {
            "buy" -> {
                when (typeKursValyuta) {
                    "usd" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyUsd.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "eur" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyEur.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "gbp" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyGbp.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "chf" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyChf.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "jpy" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyJpy.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "rub" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyRub.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                }
            }
            "sale" -> {
                when (typeKursValyuta) {
                    "usd" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.saleUsd.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "eur" -> {
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleEur.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "gbp" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.saleGbp.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "chf" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.saleChf.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "jpy" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.saleJpy.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "rub" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.saleRub.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}
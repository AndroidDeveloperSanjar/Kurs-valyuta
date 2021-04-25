package uz.androbeck.kursvalyuta.ui.banks

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.*
import uz.androbeck.kursvalyuta.*
import uz.androbeck.kursvalyuta.adapter.BanksAdapter
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.databinding.ActivityBanksBinding
import uz.androbeck.kursvalyuta.db.preferences.PreferencesManager
import uz.androbeck.kursvalyuta.ui.banks.item.ItemBankActivity
import uz.androbeck.kursvalyuta.ui.dialogs.Dialogs
import uz.androbeck.kursvalyuta.utils.NetworkLiveData

@SuppressLint("SetTextI18n")
class BanksActivity : AppCompatActivity(), BanksAdapter.BanksAdapterListener, DialogListener,
    SwipeRefreshLayout.OnRefreshListener {

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

//        CoroutineScope(IO).launch {
//            val doc =
//                Jsoup.connect("https://qishloqqurilishbank.uz/documents/t/korporativ-boshqaruv-kodeksi-tavsiyalari")
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
//                    .get()
//            println(doc.body())
//        }

        init()

        initRV()

        clicks()

        isNetwork()
    }

    private fun isNetwork() {
        networkLiveData.observe(this, {
            if (it != null) {
                if (it) {
                    observeDataFromMarkaziyBank()

                    observeDataFromAsakaBank()

                    observeDataFromIpotekaBank()

                    observeDataFromKapitalBank()

                    observeQishloqQurilishBank()

                    observeSanoatQurilishBank()

                    observeTurkistonBank()

                    observeAloqaBank()

                    observeIpakYoliBank()

                    binding.progressBar.visible(true)
                } else
                    dialogs.showNoConnectionDialog(this, lifecycleScope)
            } else
                dialogs.showNoConnectionDialog(this, lifecycleScope)
        })
    }

    private fun init() {
        networkLiveData = NetworkLiveData(this)

        preferenceManager = PreferencesManager(this)

        dialogs = Dialogs(this)

        with(binding) {
            swipeRefreshLayout.setOnRefreshListener(this@BanksActivity)
        }
    }

    private fun observeDataFromMarkaziyBank() {
        with(binding) {
            stateProgressAndLoadedData(true)
            viewModel.getMarkaziyBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    dataList.add(
                        BanksModel(
                            bankId = 0,
                            banksLogo = R.drawable.markaziy_bank_logo,
                            bankName = "Markaziy bank",
                            buyUsd = "50000",
                            saleUsd = "50000",
                            buyEur = "50000",
                            saleEur = "50000",
                            buyGbp = "50000",
                            saleGbp = "50000",
                            buyChf = "50000",
                            saleChf = "50000",
                            buyJpy = "50000",
                            saleJpy = "50000",
                            buyRub = "50000",
                            saleRub = "50000",
                            buyUsdAtm = "50000",
                            saleUsdAtm = "50000",
                            cbuUsd = elements[0].text(),
                            cbuEur = elements[1].text(),
                            cbuRub = elements[2].text(),
                            cbuGbp = elements[3].text(),
                            cbuJpy = elements[4].text(),
                            cbuChf = elements[5].text()
                        )
                    )
                    banksAdapter.submitList(dataList.sortedBy { it.bankId })
                    banksAdapter.notifyDataSetChanged()
                    rv.layoutManager?.scrollToPosition(0)
                    progressBar.visible(false)
                    isLoaded = true
                    stateProgressAndLoadedData(false)
                } else
                    stateProgressAndLoadedData(false)
            })
        }
    }

    private fun stateProgressAndLoadedData(isState: Boolean) {
        with(binding) {
            if (isState) {
                progressBar.visible(true)
                swipeRefreshLayout.isRefreshing = true
                isLoaded = false
            } else {
                progressBar.visible(false)
                swipeRefreshLayout.isRefreshing = false
                isLoaded = true
            }
        }
    }

    private fun observeDataFromAsakaBank() {
        with(binding) {
            stateProgressAndLoadedData(true)
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
                            bankId = 3,
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
                    banksAdapter.submitList(dataList.sortedBy { it.bankId })
                    banksAdapter.notifyDataSetChanged()
                    rv.layoutManager?.scrollToPosition(0)
                    stateProgressAndLoadedData(false)
                } else
                    stateProgressAndLoadedData(false)
            })
        }
    }

    private fun observeDataFromIpotekaBank() {
        with(binding) {
            stateProgressAndLoadedData(true)
            viewModel.getIpotekaBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    val buyUsd =
                        elements[0].getElementsByClass("purchase")[0].getElementsByClass("corrupt2")[0]
                            .text()
                    println("buyUsd -> $buyUsd")
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
                            bankId = 4,
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
                    banksAdapter.submitList(dataList.sortedBy { it.bankId })
                    banksAdapter.notifyDataSetChanged()
                    rv.layoutManager?.scrollToPosition(0)
                    stateProgressAndLoadedData(false)
                } else stateProgressAndLoadedData(false)
            })
        }
    }

    private fun observeDataFromKapitalBank() {
        with(binding) {
            stateProgressAndLoadedData(true)
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
                            bankId = 5,
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
                    banksAdapter.submitList(dataList.sortedBy { it.bankId })
                    banksAdapter.notifyDataSetChanged()
                    rv.layoutManager?.scrollToPosition(0)
                    isLoaded = true
                    stateProgressAndLoadedData(false)
                } else stateProgressAndLoadedData(false)
            }
        }
    }

    private fun observeQishloqQurilishBank() {
        with(binding) {
            progressBar.visible(true)
            isLoaded = false
            viewModel.getQishloqQurilishBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    println("elements != null")
                    //banksAdapter.submitList(dataList)
                    //banksAdapter.notifyDataSetChanged()
                    //isLoaded = true
                    //progressBar.visible(false)
                } else {
                    println("elements == null")
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
                    println("sanoat elements != null")
                    //println(elements)
                    //banksAdapter.submitList(dataList)
                    //banksAdapter.notifyDataSetChanged()
                    //isLoaded = true
                    //progressBar.visible(false)
                } else {
                    println("sanoat elements == null")
                    isLoaded = true
                    progressBar.visible(false)
                }
            })
        }
    }

    private fun observeTurkistonBank() {
        with(binding) {
            stateProgressAndLoadedData(true)
            viewModel.getTurkistonBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    //println(elements)
                    val buyUsd =
                        elements[0].getElementsByClass("currency-table__value_data")[0].text()
                    val saleUsd =
                        elements[0].getElementsByClass("currency-table__value_data")[1].text()
                    val buyEur =
                        elements[0].getElementsByClass("currency-table__value_data")[3].text()
                    val saleEur =
                        elements[0].getElementsByClass("currency-table__value_data")[4].text()
                    val buyGbp =
                        elements[0].getElementsByClass("currency-table__value_data")[6].text()
                    val saleGbp =
                        elements[0].getElementsByClass("currency-table__value_data")[7].text()
                    val buyJpy =
                        elements[0].getElementsByClass("currency-table__value_data")[9].text()
                    val saleJpy =
                        elements[0].getElementsByClass("currency-table__value_data")[10].text()
                    dataList.add(
                        BanksModel(
                            bankId = 6,
                            banksLogo = R.drawable.turkiston_bank_logo,
                            bankName = "Turkiston bank",
                            buyUsd = buyUsd.substring(0, 6).replace(" ", ""),
                            saleUsd = saleUsd.substring(0, 6).replace(" ", ""),
                            buyEur = buyEur.substring(0, 6).replace(" ", ""),
                            saleEur = saleEur.substring(0, 6).replace(" ", ""),
                            buyGbp = buyGbp.substring(0, 6).replace(" ", ""),
                            saleGbp = saleGbp.substring(0, 6).replace(" ", ""),
                            buyJpy = buyJpy,
                            saleJpy = saleJpy
                        )
                    )
                    banksAdapter.submitList(dataList.sortedBy { it.bankId })
                    banksAdapter.notifyDataSetChanged()
                    rv.layoutManager?.scrollToPosition(0)
                    stateProgressAndLoadedData(false)
                } else
                    stateProgressAndLoadedData(false)
            })
        }
    }

    private fun observeAloqaBank() {
        with(binding) {
            stateProgressAndLoadedData(true)
            viewModel.getAloqaBankValyuta().observe(this@BanksActivity, { element ->
                if (element != null) {
                    val buyUsd = element.getElementsByTag("td")[1].text()
                    val saleUsd = element.getElementsByTag("td")[2].text()
                    val buyEur = element.getElementsByTag("td")[5].text()
                    val saleEur = element.getElementsByTag("td")[6].text()
                    val buyGbp = element.getElementsByTag("td")[9].text()
                    val saleGbp = element.getElementsByTag("td")[10].text()
                    val buyJpy = element.getElementsByTag("td")[13].text()
                    val saleJpy = element.getElementsByTag("td")[14].text()
                    val buyChf = element.getElementsByTag("td")[17].text()
                    val saleChf = element.getElementsByTag("td")[18].text()
                    dataList.add(
                        BanksModel(
                            bankId = 7,
                            banksLogo = R.drawable.aloqa_bank_logo,
                            bankName = "Aloqa bank",
                            buyUsd = buyUsd,
                            saleUsd = saleUsd,
                            buyEur = buyEur,
                            saleEur = saleEur,
                            buyGbp = buyGbp,
                            saleGbp = saleGbp,
                            buyJpy = buyJpy,
                            saleJpy = saleJpy,
                            buyChf = buyChf,
                            saleChf = saleChf
                        )
                    )
                    banksAdapter.submitList(dataList.sortedBy { it.bankId })
                    banksAdapter.notifyDataSetChanged()
                    rv.layoutManager?.scrollToPosition(0)
                    stateProgressAndLoadedData(false)
                } else stateProgressAndLoadedData(false)
            })
        }
    }

    private fun observeIpakYoliBank() {
        with(binding) {
            stateProgressAndLoadedData(true)
            viewModel.getIpakYoliBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    val buyUsd = elements.select("td")
                    println("abc -> $buyUsd")
//                    val saleUsd = element.getElementsByTag("td")[2].text()
//                    val buyEur = element.getElementsByTag("td")[5].text()
//                    val saleEur = element.getElementsByTag("td")[6].text()
//                    val buyGbp = element.getElementsByTag("td")[9].text()
//                    val saleGbp = element.getElementsByTag("td")[10].text()
//                    val buyJpy = element.getElementsByTag("td")[13].text()
//                    val saleJpy = element.getElementsByTag("td")[14].text()
//                    val buyChf = element.getElementsByTag("td")[17].text()
//                    val saleChf = element.getElementsByTag("td")[18].text()
//                    dataList.add(
//                        BanksModel(
//                            banksLogo = R.drawable.aloqa_bank_logo,
//                            bankName = "Aloqa bank",
//                            buyUsd = buyUsd,
//                            saleUsd = saleUsd,
//                            buyEur = buyEur,
//                            saleEur = saleEur,
//                            buyGbp = buyGbp,
//                            saleGbp = saleGbp,
//                            buyJpy = buyJpy,
//                            saleJpy = saleJpy,
//                            buyChf = buyChf,
//                            saleChf = saleChf
//                        )
//                    )
//                    banksAdapter.submitList(dataList)
//                    banksAdapter.notifyDataSetChanged()
                    stateProgressAndLoadedData(false)
                } else stateProgressAndLoadedData(false)
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
            if (position != 0) {
                val intent = Intent(this, ItemBankActivity::class.java)
                intent.putExtra("data_intent", bundleOf("data" to data))
                startActivity(intent)
            }
        } else {
            binding.root.snackbar("Iltimos ma'lumotlar yuklanib bo'lishini kuting!")
        }
    }

    override fun itemBuyOrSaleValyutaDialogClick(buyOrSale: String) {
        dialogs.showBuyAndSaleAllValyutaDialog(this, buyOrSale)
    }

    override fun itemAllKursTypeDialogClick(buyOrSale: String, typeKursValyuta: String) {
        when (buyOrSale) {
            "buy" -> {
                when (typeKursValyuta) {
                    "usd" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyUsd = "-50000"
                        banksAdapter.submitList(dataList.sortedBy { it.buyUsd.toInt() })
                        banksAdapter.notifyDataSetChanged()
                        binding.rv.layoutManager?.scrollToPosition(0)
                    }
                    "eur" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyEur = "-50000"
                        banksAdapter.submitList(dataList.sortedBy { it.buyEur.toInt() })
                        banksAdapter.notifyDataSetChanged()
                        binding.rv.layoutManager?.scrollToPosition(0)
                    }
                    "gbp" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyGbp = "-50000"
                        banksAdapter.submitList(dataList.sortedBy { it.buyGbp.toInt() })
                        banksAdapter.notifyDataSetChanged()
                        binding.rv.layoutManager?.scrollToPosition(0)
                    }
                    "chf" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyChf = "-50000"
                        banksAdapter.submitList(dataList.sortedBy { it.buyChf.toInt() })
                        banksAdapter.notifyDataSetChanged()
                        binding.rv.layoutManager?.scrollToPosition(0)
                    }
                    "jpy" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyJpy = "-50000"
                        banksAdapter.submitList(dataList.sortedBy { it.buyJpy.toInt() })
                        banksAdapter.notifyDataSetChanged()
                        binding.rv.layoutManager?.scrollToPosition(0)

                    }
                    "rub" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyRub = "-50000"
                        banksAdapter.submitList(dataList.sortedBy { it.buyRub.toInt() })
                        banksAdapter.notifyDataSetChanged()
                        binding.rv.layoutManager?.scrollToPosition(0)
                    }
                }
            }
            "sale" -> {
                when (typeKursValyuta) {
                    "usd" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyUsd = "50000"
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleUsd.toInt() })
                        banksAdapter.notifyDataSetChanged()
                        binding.rv.layoutManager?.scrollToPosition(0)
                    }
                    "eur" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyEur = "50000"
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleEur.toInt() })
                        banksAdapter.notifyDataSetChanged()
                        binding.rv.layoutManager?.scrollToPosition(0)
                    }
                    "gbp" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyGbp = "50000"
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleGbp.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "chf" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyChf = "50000"
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleChf.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "jpy" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyJpy = "50000"
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleJpy.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                    "rub" -> {
                        dataList.find { it.bankName == "Markaziy bank" }?.buyRub = "50000"
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleRub.toInt() })
                        banksAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onRefresh() {
        if (networkLiveData.value!!) {
            dataList.clear()
            isNetwork()
        } else {
            binding.swipeRefreshLayout.isRefreshing = false
            dialogs.showNoConnectionDialog(this, lifecycleScope)
            binding.root.snackbar("Iltimos internetni yoqib keyin urinib ko'ring!")
        }
    }
}
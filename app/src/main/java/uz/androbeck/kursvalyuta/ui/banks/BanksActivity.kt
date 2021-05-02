package uz.androbeck.kursvalyuta.ui.banks

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import uz.androbeck.kursvalyuta.*
import uz.androbeck.kursvalyuta.adapter.BanksAdapter
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.databinding.ActivityBanksBinding
import uz.androbeck.kursvalyuta.db.preferences.PreferencesManager
import uz.androbeck.kursvalyuta.ui.banks.item.ItemBankActivity
import uz.androbeck.kursvalyuta.ui.banks.item.QQBBankActivity
import uz.androbeck.kursvalyuta.ui.dialogs.Dialogs
import uz.androbeck.kursvalyuta.utils.Helper
import uz.androbeck.kursvalyuta.utils.NetworkLiveData
import uz.androbeck.kursvalyuta.utils.Objects


@SuppressLint("SetTextI18n")
class BanksActivity : AppCompatActivity(), BanksAdapter.BanksAdapterListener, DialogListener {

    private lateinit var binding: ActivityBanksBinding

    private lateinit var banksAdapter: BanksAdapter

    private lateinit var preferenceManager: PreferencesManager

    private lateinit var dialogs: Dialogs

    private val viewModel: BanksViewModel by viewModels()

    private lateinit var networkLiveData: NetworkLiveData

    private var dataList = ArrayList<BanksModel>()

    private var buyUsdList = ArrayList<Data>()
    private var saleUsdList = ArrayList<Data>()
    private var buyRubList = ArrayList<Data>()
    private var saleRubList = ArrayList<Data>()
    private var buyEurList = ArrayList<Data>()
    private var saleEurList = ArrayList<Data>()
    private var buyGbpList = ArrayList<Data>()
    private var saleGbpList = ArrayList<Data>()
    private var buyJpyList = ArrayList<Data>()
    private var saleJpyList = ArrayList<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBanksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        initRV()

        clicks()

        isNetwork()
    }

    private fun getData() {
        CoroutineScope(Default).launch {
            val doc = Objects.document("https://bank.uz/uz/currency")
            /// usd ///
            val organizationContactsLeftUSD =
                doc.getElementsByClass("organization-contacts")[0].getElementsByClass("bc-inner-blocks-left")[0]
            val buyUsdValyutaList = organizationContactsLeftUSD.getElementsByTag("span").eachText()
            var sumBuyUsd = 2
            for (i in 1 until buyUsdValyutaList.lastIndex step 2) {
                buyUsdList.add(
                    Data(
                        "buy",
                        "usd",
                        buyUsdValyutaList[i],
                        buyUsdValyutaList[sumBuyUsd].substring(0, 6).replace(" ", "")
                    )
                )
                sumBuyUsd += 2
            }
            val organizationContactsRightUSD =
                doc.getElementsByClass("organization-contacts")[0].getElementsByClass("bc-inner-blocks-right")[0]
            val saleUsdValyutaList =
                organizationContactsRightUSD.getElementsByTag("span").eachText()
            var sumSaleUsd = 2
            for (i in 1 until saleUsdValyutaList.lastIndex step 2) {
                saleUsdList.add(
                    Data(
                        "sale",
                        "usd",
                        saleUsdValyutaList[i],
                        saleUsdValyutaList[sumSaleUsd].substring(0, 6).replace(" ", "")
                    )
                )
                sumSaleUsd += 2
            }
            /// usd ///

            /// rub ///
            val organizationContactsLeftRUB =
                doc.getElementsByClass("organization-contacts")[1].getElementsByClass("bc-inner-blocks-left")[0]
            val buyRubValyutaList = organizationContactsLeftRUB.getElementsByTag("span").eachText()
            var sumBuyRub = 2
            for (i in 1 until buyRubValyutaList.lastIndex step 2) {
                buyRubList.add(
                    Data(
                        "buy",
                        "rub",
                        buyRubValyutaList[i],
                        buyRubValyutaList[sumBuyRub].substring(0, 3).replace(" ", "")
                    )
                )
                sumBuyRub += 2
            }

            val organizationContactsRightRUB =
                doc.getElementsByClass("organization-contacts")[1].getElementsByClass("bc-inner-blocks-right")[0]
            val saleRubValyutaList =
                organizationContactsRightRUB.getElementsByTag("span").eachText()
            var sumSaleRub = 2
            for (i in 1 until saleRubValyutaList.lastIndex step 2) {
                saleRubList.add(
                    Data(
                        "sale",
                        "rub",
                        saleRubValyutaList[i],
                        saleRubValyutaList[sumSaleRub].substring(0, 3).replace(" ", "")
                    )
                )
                sumSaleRub += 2
            }
            /// rub ///

            /// eur ///
            val organizationContactsLeftEUR =
                doc.getElementsByClass("organization-contacts")[2].getElementsByClass("bc-inner-blocks-left")[0]
            val buyEurValyutaList = organizationContactsLeftEUR.getElementsByTag("span").eachText()
            var sumBuyEur = 2
            for (i in 1 until buyEurValyutaList.lastIndex step 2) {
                buyEurList.add(
                    Data(
                        "buy",
                        "eur",
                        buyEurValyutaList[i],
                        buyEurValyutaList[sumBuyEur].substring(0, 6).replace(" ", "")
                    )
                )
                sumBuyEur += 2
            }

            val organizationContactsRightEUR =
                doc.getElementsByClass("organization-contacts")[2].getElementsByClass("bc-inner-blocks-right")[0]
            val saleEurValyutaList =
                organizationContactsRightEUR.getElementsByTag("span").eachText()
            println(saleEurValyutaList)
            var sumSaleEur = 2
            for (i in 1 until saleEurValyutaList.lastIndex step 2) {
                saleEurList.add(
                    Data(
                        "sale",
                        "eur",
                        saleEurValyutaList[i],
                        saleEurValyutaList[sumSaleEur].substring(0, 6).replace(" ", "")
                    )
                )
                sumSaleEur += 2
            }
            println(saleEurList)
            /// eur ///

            /// gbp ///
            val organizationContactsLeftGBP =
                doc.getElementsByClass("organization-contacts")[3].getElementsByClass("bc-inner-blocks-left")[0]
            val buyGbpValyutaList = organizationContactsLeftGBP.getElementsByTag("span").eachText()
            var sumBuyGbp = 2
            for (i in 1 until buyGbpValyutaList.lastIndex step 2) {
                buyGbpList.add(
                    Data(
                        "buy",
                        "gbp",
                        buyGbpValyutaList[i],
                        buyGbpValyutaList[sumBuyGbp].substring(0, 6).replace(" ", "")
                    )
                )
                sumBuyGbp += 2
            }

            val organizationContactsRightGBP =
                doc.getElementsByClass("organization-contacts")[3].getElementsByClass("bc-inner-blocks-right")[0]
            val saleGbpValyutaList =
                organizationContactsRightGBP.getElementsByTag("span").eachText()
            var sumSaleGbp = 2
            for (i in 1 until saleGbpValyutaList.lastIndex step 2) {
                saleGbpList.add(
                    Data(
                        "sale",
                        "gbp",
                        saleGbpValyutaList[i],
                        saleGbpValyutaList[sumSaleGbp].substring(0, 6).replace(" ", "")
                    )
                )
                sumSaleGbp += 2
            }
            /// gbp ///

            /// jpy ///
            val organizationContactsLeftJPY =
                doc.getElementsByClass("organization-contacts")[4].getElementsByClass("bc-inner-blocks-left")[0]
            val buyJpyValyutaList = organizationContactsLeftJPY.getElementsByTag("span").eachText()
            var sumBuyJpy = 2
            for (i in 1 until buyJpyValyutaList.lastIndex step 2) {
                buyJpyList.add(
                    Data(
                        "buy",
                        "jpy",
                        buyJpyValyutaList[i],
                        buyJpyValyutaList[sumBuyJpy].substring(0, 3).replace(" ", "")
                    )
                )
                sumBuyJpy += 2
            }

            val organizationContactsRightJPY =
                doc.getElementsByClass("organization-contacts")[4].getElementsByClass("bc-inner-blocks-right")[0]
            val saleJpyValyutaList =
                organizationContactsRightJPY.getElementsByTag("span").eachText()
            var sumSaleJpy = 2
            for (i in 1 until saleJpyValyutaList.lastIndex step 2) {
                saleJpyList.add(
                    Data(
                        "sale",
                        "jpy",
                        saleJpyValyutaList[i],
                        saleJpyValyutaList[sumSaleJpy].substring(0, 3).replace(" ", "")
                    )
                )
                sumSaleJpy += 2
            }
            /// jpy ///
            MainScope().launch {
                dataList()
                binding.progressBar.visible(false)
                banksAdapter.submitList(dataList)
            }
        }
    }

    private fun dataList() {
        /// usd ///
        buyUsdList.forEach { data ->
            dataList.add(BanksModel(bankName = data.bankName))
        }
        buyUsdList.forEach { data ->
            dataList.find { it.bankName == data.bankName }?.buyUsd = data.sum
        }
        saleUsdList.forEach { data ->
            dataList.find { it.bankName == data.bankName }?.saleUsd = data.sum
        }
        /// usd ///

        /// rub ///
        buyRubList.forEach { data ->
            dataList.find { it.bankName == data.bankName }?.buyRub = data.sum
        }
        saleRubList.forEach { data ->
            dataList.find { it.bankName == data.bankName }?.saleRub = data.sum
        }
        /// rub ///

        /// eur ///
        println(saleEurList)
        buyEurList.forEach { data ->
            dataList.find { it.bankName == data.bankName }?.buyEur = data.sum
        }
        saleEurList.forEach { data ->
            dataList.find { it.bankName == data.bankName }?.saleEur = data.sum
        }
        /// eur ///

        /// gbp ///
        buyGbpList.forEach { data ->
            dataList.find { it.bankName == data.bankName }?.buyGbp = data.sum
        }
        saleGbpList.forEach { data ->
            dataList.find { it.bankName == data.bankName }?.saleGbp = data.sum
        }
        /// gbp ///

        /// jpy ///
        buyJpyList.forEach { data ->
            dataList.find { it.bankName == data.bankName }?.buyJpy = data.sum
        }
        saleJpyList.forEach { data ->
            dataList.find { it.bankName == data.bankName }?.saleJpy = data.sum
        }
        /// jpy ///

        /// set logo ///
        Helper.bankLogoList().forEach { data ->
            dataList.find { it.bankName == data.bankName }?.banksLogo = data.logo
        }
        /// set logo ///
    }

    data class Data(
        var typeByBuyOrSaleValyuta: String = "",
        var typeValyuta: String = "",
        var bankName: String = "",
        var sum: String = ""
    )

    private fun isNetwork() {
        networkLiveData.observe(this, {
            if (it != null) {
                if (it) {
                    binding.progressBar.visible(true)
                    observeDataFromMarkaziyBank()
                    getData()
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
    }

    private var isDown = false

    private fun observeDataFromMarkaziyBank() {
        with(binding) {
            viewModel.getMarkaziyBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    val cbuUsd = elements[0].text()
                    val cbuEur = elements[1].text()
                    val cbuRub = elements[2].text()
                    val cbuGbp = elements[3].text()
                    val cbuJpy = elements[4].text()
                    val cbuChf = elements[5].text()
                    with(binding) {
                        tvCbuUsd.text = cbuUsd
                        tvCbuChf.text = cbuChf
                        tvCbuEur.text = cbuEur
                        tvCbuJpy.text = cbuJpy
                        tvCbuGbp.text = cbuGbp
                        tvCbuRub.text = cbuRub
                        ivDropDown.setOnClickListener {
                            isDown = !isDown
                            if (isDown) {
                                ivDropDown.setImageResource(R.drawable.ic_arrow_up)
                                llValyutaCbu.visible(true)
                            } else {
                                ivDropDown.setImageResource(R.drawable.ic_arrow_down)
                                llValyutaCbu.visible(false)
                            }
                        }
                    }
                    rv.layoutManager?.scrollToPosition(0)
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
            ivQqb.setOnClickListener {
                startActivity(Intent(this@BanksActivity, QQBBankActivity::class.java))
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
        val intent = Intent(this, ItemBankActivity::class.java)
        intent.putExtra("data_intent", bundleOf("data" to data))
        startActivity(intent)
    }

    override fun itemBuyOrSaleValyutaDialogClick(buyOrSale: String) {
        dialogs.showBuyAndSaleAllValyutaDialog(this, buyOrSale)
    }

    override fun itemAllKursTypeDialogClick(buyOrSale: String, typeKursValyuta: String) {
        when (buyOrSale) {
            "buy" -> {
                when (typeKursValyuta) {
                    "usd" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyUsd.toInt() })
                        //banksAdapter.notifyDataSetChanged()

                    }
                    "eur" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyEur.toInt() })
                        //banksAdapter.notifyDataSetChanged()

                    }
                    "gbp" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyGbp.toInt() })
                        //banksAdapter.notifyDataSetChanged()

                    }
                    "jpy" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyJpy.toInt() })
                        //banksAdapter.notifyDataSetChanged()
                        println(dataList)

                    }
                    "rub" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyRub.toInt() })
                        //banksAdapter.notifyDataSetChanged()
                    }
                }
            }
            "sale" -> {
                when (typeKursValyuta) {
                    "usd" -> {
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleUsd.toInt() })
                        //  banksAdapter.notifyDataSetChanged()
                    }
                    "eur" -> {
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleEur.toInt() })
                        //  banksAdapter.notifyDataSetChanged()
                    }
                    "gbp" -> {
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleGbp.toInt() })
                        // banksAdapter.notifyDataSetChanged()
                    }
                    "jpy" -> {
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleJpy.toInt() })
                        // banksAdapter.notifyDataSetChanged()
                    }
                    "rub" -> {
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleRub.toInt() })
                        // banksAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

//    override fun onRefresh() {
//        if (networkLiveData.value!!) {
//            dataList.clear()
//            isNetwork()
//        } else {
//            binding.swipeRefreshLayout.isRefreshing = false
//            dialogs.showNoConnectionDialog(this, lifecycleScope)
//            binding.root.snackbar("Iltimos internetni yoqib keyin urinib ko'ring!")
//        }
//    }
}

/*
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
                        elements[0].getElementsByClass("purchase")[0].getElementsByClass("corrupt")[0]
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
                    println(elements.text())
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

    private fun observeSaderatBank() {
        with(binding) {
            stateProgressAndLoadedData(true)
            viewModel.getSaderatBankValyuta().observe(this@BanksActivity, { element ->
                if (element != null) {
                    val buyUsd = element[0].getElementsByTag("td")[1].text()
                    val saleUsd = element[0].getElementsByTag("td")[6].text()
                    val buyEur = element[0].getElementsByTag("td")[2].text()
                    val saleEur = element[0].getElementsByTag("td")[7].text()
                    val buyGbp = element[0].getElementsByTag("td")[3].text()
                    val saleGbp = element[0].getElementsByTag("td")[8].text()
                    val buyJpy = element[0].getElementsByTag("td")[4].text()
                    val saleJpy = element[0].getElementsByTag("td")[9].text()
//                    val buyChf = element.getElementsByTag("td")[17].text()
//                    val saleChf = element.getElementsByTag("td")[18].text()
                    dataList.add(
                        BanksModel(
                            bankId = 8,
                            banksLogo = R.drawable.sadertat_bank_logo,
                            bankName = "Saderat bank",
                            buyUsd = buyUsd.substring(1),
                            saleUsd = saleUsd.substring(1),
                            buyEur = buyEur.substring(1),
                            saleEur = saleEur.substring(1),
                            buyGbp = buyGbp.substring(1),
                            saleGbp = saleGbp.substring(1),
                            buyJpy = buyJpy.substring(1, 3),
                            saleJpy = saleJpy.substring(1, 3)
                        )
                    )
                    println(dataList.find { it.bankName == "Saderat bank" })
                    banksAdapter.submitList(dataList.sortedBy { it.bankId })
                    banksAdapter.notifyDataSetChanged()
                    rv.layoutManager?.scrollToPosition(0)
                    stateProgressAndLoadedData(false)
                } else stateProgressAndLoadedData(false)
            })
        }
    }

    private fun observeXalqBank() {
        with(binding) {
            stateProgressAndLoadedData(true)
            viewModel.getXalqBankValyuta().observe(this@BanksActivity, { elements ->
                if (elements != null) {
                    val buyUsd =
                        elements[0].getElementsByClass("row currency__amount no-gutters")[0].getElementsByClass(
                            "col-4 col-sm-2 inner__rate"
                        )[1].text()

                    val saleUsd =
                        elements[0].getElementsByClass("row currency__amount no-gutters")[1].getElementsByClass(
                            "col-4 col-sm-2 inner__rate buying__rate"
                        )[1].text()
                    val buyEur =
                        elements[0].getElementsByClass("row currency__amount no-gutters")[0].getElementsByClass(
                            "col-4 col-sm-2 inner__rate"
                        )[0].text()
                    val saleEur =
                        elements[0].getElementsByClass("row currency__amount no-gutters")[1].getElementsByClass(
                            "col-4 col-sm-2 inner__rate buying__rate"
                        )[0].text()
                    val buyGbp =
                        elements[0].getElementsByClass("row currency__amount no-gutters")[0].getElementsByClass(
                            "col-4 col-sm-2 inner__rate"
                        )[2].text()

                    val saleGbp =
                        elements[0].getElementsByClass("row currency__amount no-gutters")[1].getElementsByClass(
                            "col-4 col-sm-2 inner__rate buying__rate"
                        )[2].text()
                    val buyChf =
                        elements[0].getElementsByClass("row currency__amount no-gutters")[0].getElementsByClass(
                            "col-4 col-sm-2 inner__rate"
                        )[3].text()

                    val saleChf =
                        elements[0].getElementsByClass("row currency__amount no-gutters")[1].getElementsByClass(
                            "col-4 col-sm-2 inner__rate buying__rate"
                        )[3].text()
                    val buyRub =
                        elements[0].getElementsByClass("row currency__amount no-gutters")[0].getElementsByClass(
                            "col-4 col-sm-2 inner__rate"
                        )[4].text()

                    val saleRub =
                        elements[0].getElementsByClass("row currency__amount no-gutters")[1].getElementsByClass(
                            "col-4 col-sm-2 inner__rate buying__rate"
                        )[4].text()
                    println("$buyUsd | $saleEur | $saleUsd | $buyEur")
                    dataList.add(
                        BanksModel(
                            bankId = 9,
                            banksLogo = R.drawable.xalq_banki_logo,
                            bankName = "Xalq bank",
                            buyUsd = buyUsd.substring(0, 6).replace(" ", ""),
                            saleUsd = saleUsd.substring(0, 6).replace(" ", ""),
                            buyEur = buyEur.substring(0, 6).replace(" ", ""),
                            saleEur = saleEur.substring(0, 6).replace(" ", ""),
                            buyGbp = buyGbp.substring(0, 6).replace(" ", ""),
                            saleGbp = saleGbp.substring(0, 6).replace(" ", ""),
                            buyChf = buyChf.substring(0, 6).replace(" ", ""),
                            saleChf = saleChf.substring(0, 6).replace(" ", ""),
                            buyRub = buyRub.substring(0, 2).replace(" ", ""),
                            saleRub = saleRub.substring(0, 3).replace(" ", "")
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
 */
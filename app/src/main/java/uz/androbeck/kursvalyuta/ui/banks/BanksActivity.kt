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
                        binding.rv.smoothScrollToPosition(0)
                    }
                    "eur" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyEur.toInt() })
                        binding.rv.smoothScrollToPosition(0)

                    }
                    "gbp" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyGbp.toInt() })
                        binding.rv.smoothScrollToPosition(0)
                    }
                    "jpy" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyJpy.toInt() })
                        binding.rv.smoothScrollToPosition(0)

                    }
                    "rub" -> {
                        banksAdapter.submitList(dataList.sortedBy { it.buyRub.toInt() })
                        binding.rv.smoothScrollToPosition(0)
                    }
                }
            }
            "sale" -> {
                when (typeKursValyuta) {
                    "usd" -> {
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleUsd.toInt() })
                        binding.rv.smoothScrollToPosition(0)
                    }
                    "eur" -> {
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleEur.toInt() })
                        binding.rv.smoothScrollToPosition(0)
                    }
                    "gbp" -> {
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleGbp.toInt() })
                        binding.rv.smoothScrollToPosition(0)
                    }
                    "jpy" -> {
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleJpy.toInt() })
                        binding.rv.smoothScrollToPosition(0)
                    }
                    "rub" -> {
                        banksAdapter.submitList(dataList.sortedByDescending { it.saleRub.toInt() })
                        binding.rv.smoothScrollToPosition(0)
                    }
                }
            }
        }
    }
}
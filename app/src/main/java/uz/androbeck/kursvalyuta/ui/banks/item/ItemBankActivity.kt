package uz.androbeck.kursvalyuta.ui.banks.item

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.databinding.ActivityItemBankBinding
import uz.androbeck.kursvalyuta.ui.dialogs.Dialogs
import uz.androbeck.kursvalyuta.visible

@SuppressLint("SetTextI18n")
class ItemBankActivity : AppCompatActivity() {

    private val binding: ActivityItemBankBinding by viewBinding()

    private lateinit var data: BanksModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

        setData()

        clicks()
    }

    private fun init() {
        if (intent != null) {
            val dataIntent = intent!!.getBundleExtra("data_intent")
            data = dataIntent!!.getParcelable("data")!!
        }
        with(binding) {
            if (data.buyUsd == "" || data.saleUsd == "")
                rlUsd.visible(false)
            if (data.buyEur == "" || data.saleEur == "")
                rlEur.visible(false)
            if (data.buyGbp == "" || data.saleGbp == "")
                rlGbp.visible(false)
            if (data.buyChf == "" || data.saleChf == "")
                rlChf.visible(false)
            if (data.buyJpy == "" || data.saleJpy == "")
                rlJpy.visible(false)
            if (data.buyRub == "" || data.saleRub == "")
                rlRub.visible(false)
        }

    }

    private fun setData() {
        with(binding) {
            tvBankName.text = data.bankName

            tvUsdBuy.text = "Olish : ${data.buyUsd}"
            tvUsdSale.text = "Sotish : ${data.saleUsd}"

            tvEurBuy.text = "Olish : ${data.buyEur}"
            tvEurSale.text = "Sotish : ${data.saleEur}"

            tvGbpBuy.text = "Olish : ${data.buyGbp}"
            tvGbpSale.text = "Sotish : ${data.saleGbp}"

            tvChfBuy.text = "Olish : ${data.buyChf}"
            tvChfSale.text = "Sotish : ${data.saleChf}"

            tvJpyBuy.text = "Olish : ${data.buyJpy}"
            tvJpySale.text = "Sotish : ${data.saleJpy}"

            tvRubBuy.text = "Olish : ${data.buyRub}"
            tvRubSale.text = "Sotish : ${data.saleRub}"
        }
    }

    private fun clicks() {
        with(binding) {
            viewBackPressed.setOnClickListener {
                onBackPressed()
            }
            viewShare.setOnClickListener {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                if (data.buyUsd != "" && data.saleUsd != "")
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}\nUSD: ${data.buyUsd} - ${data.saleUsd}"
                    )
                if (data.buyUsd != "" && data.saleUsd != "" && data.buyEur != "" && data.saleEur != "")
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}\nUSD: ${data.buyUsd} - ${data.saleUsd}\nEUR: ${data.buyEur} - ${data.saleEur}"
                    )
                if (data.buyUsd != "" && data.saleUsd != "" && data.buyEur != "" && data.saleEur != "" && data.buyGbp != "" && data.saleGbp != "")
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}\nUSD: ${data.buyUsd} - ${data.saleUsd}\nEUR: ${data.buyEur} - ${data.saleEur}\nGBP: ${data.buyGbp} - ${data.saleGbp}"
                    )
                if (data.buyUsd != "" && data.saleUsd != "" && data.buyEur != "" && data.saleEur != "" && data.buyGbp != ""
                    && data.saleGbp != "" && data.buyChf != "" && data.saleChf != ""
                )
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}\nUSD: ${data.buyUsd} - ${data.saleUsd}\nEUR: ${data.buyEur} - ${data.saleEur}\nGBP: ${data.buyGbp} - ${data.saleGbp}\nCHF: ${data.buyChf} - ${data.saleChf}"
                    )
                if (data.buyUsd != "" && data.saleUsd != "" && data.buyEur != "" && data.saleEur != "" && data.buyGbp != ""
                    && data.saleGbp != "" && data.buyChf != "" && data.saleChf != "" && data.buyJpy != "" && data.saleJpy != ""
                )
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}\nUSD: ${data.buyUsd} - ${data.saleUsd}\nEUR: ${data.buyEur} - ${data.saleEur}\nGBP: ${data.buyGbp} - ${data.saleGbp}\nCHF: ${data.buyChf} - ${data.saleChf}\nJPY: ${data.buyJpy} - ${data.saleJpy}"
                    )
                if (data.buyUsd != "" && data.saleUsd != "" && data.buyEur != "" && data.saleEur != "" && data.buyGbp != ""
                    && data.saleGbp != "" && data.buyChf != "" && data.saleChf != "" && data.buyJpy != "" && data.saleJpy != ""
                    && data.buyRub != "" && data.saleRub != ""
                )
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}\nUSD: ${data.buyUsd} - ${data.saleUsd}\nEUR: ${data.buyEur} - ${data.saleEur}\nGBP: ${data.buyGbp} - ${data.saleGbp}\nCHF: ${data.buyChf} - ${data.saleChf}\nJPY: ${data.buyJpy} - ${data.saleJpy}\nRUB: ${data.buyRub} - ${data.saleRub}"
                    )
                startActivity(Intent.createChooser(shareIntent, "Hello"))
            }
            viewCalculator.setOnClickListener {
                Dialogs().calculateDialog(this@ItemBankActivity,data)
            }
        }
    }
}
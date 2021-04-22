package uz.androbeck.kursvalyuta.ui.banks.item

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.databinding.ActivityItemBankBinding
import uz.androbeck.kursvalyuta.ui.dialogs.Dialogs
import uz.androbeck.kursvalyuta.utils.Helper
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
            if (data.buyUsd == "0" || data.saleUsd == "0")
                rlUsd.visible(false)
            if (data.buyEur == "0" || data.saleEur == "0")
                rlEur.visible(false)
            if (data.buyGbp == "0" || data.saleGbp == "0")
                rlGbp.visible(false)
            if (data.buyChf == "0" || data.saleChf == "0")
                rlChf.visible(false)
            if (data.buyJpy == "0" || data.saleJpy == "0")
                rlJpy.visible(false)
            if (data.buyRub == "0" || data.saleRub == "0")
                rlRub.visible(false)
        }

    }

    private fun setData() {
        with(binding) {
            tvBankName.text = data.bankName

            tvUsdBuy.text = "Olish : ${Helper.formatNumber(data.buyUsd.toInt())} so'm"
            tvUsdSale.text = "Sotish : ${Helper.formatNumber(data.saleUsd.toInt())} so'm"

            tvEurBuy.text = "Olish : ${Helper.formatNumber(data.buyEur.toInt())} so'm"
            tvEurSale.text = "Sotish : ${Helper.formatNumber(data.saleEur.toInt())} so'm"

            tvGbpBuy.text = "Olish : ${Helper.formatNumber(data.buyGbp.toInt())} so'm"
            tvGbpSale.text = "Sotish : ${Helper.formatNumber(data.saleGbp.toInt())} so'm"

            tvChfBuy.text = "Olish : ${Helper.formatNumber(data.buyChf.toInt())} so'm"
            tvChfSale.text = "Sotish : ${Helper.formatNumber(data.saleChf.toInt())} so'm"

            tvJpyBuy.text = "Olish : ${data.buyJpy} so'm"
            tvJpySale.text = "Sotish : ${data.saleJpy} so'm"

            tvRubBuy.text = "Olish : ${data.buyRub} so'm"
            tvRubSale.text = "Sotish : ${data.saleRub} so'm"
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
                if (data.buyUsd != "0" && data.saleUsd != "0")
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}  | so'mda\nUSD: ${data.buyUsd} - ${data.saleUsd}"
                    )
                if (data.buyUsd != "0" && data.saleUsd != "0" && data.buyEur != "" && data.saleEur != "0")
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}  | so'mda\nUSD: ${data.buyUsd} - ${data.saleUsd}\nEUR: ${data.buyEur} - ${data.saleEur}"
                    )
                if (data.buyUsd != "0" && data.saleUsd != "0" && data.buyEur != "0" && data.saleEur != "0" && data.buyGbp != "0" && data.saleGbp != "0")
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}  | so'mda\nUSD: ${data.buyUsd} - ${data.saleUsd}\nEUR: ${data.buyEur} - ${data.saleEur}\nGBP: ${data.buyGbp} - ${data.saleGbp}"
                    )
                if (data.buyUsd != "0" && data.saleUsd != "0" && data.buyEur != "0" && data.saleEur != "0" && data.buyGbp != "0"
                    && data.saleGbp != "0" && data.buyChf != "0" && data.saleChf != "0"
                )
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}  | so'mda\nUSD: ${data.buyUsd} - ${data.saleUsd}\nEUR: ${data.buyEur} - ${data.saleEur}\nGBP: ${data.buyGbp} - ${data.saleGbp}\nCHF: ${data.buyChf} - ${data.saleChf}"
                    )
                if (data.buyUsd != "0" && data.saleUsd != "0" && data.buyEur != "0" && data.saleEur != "0" && data.buyGbp != "0"
                    && data.saleGbp != "0" && data.buyChf != "0" && data.saleChf != "0" && data.buyJpy != "0" && data.saleJpy != "0"
                )
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}  | so'mda\nUSD: ${data.buyUsd} - ${data.saleUsd}\nEUR: ${data.buyEur} - ${data.saleEur}\nGBP: ${data.buyGbp} - ${data.saleGbp}\nCHF: ${data.buyChf} - ${data.saleChf}\nJPY: ${data.buyJpy} - ${data.saleJpy}"
                    )
                if (data.buyUsd != "0" && data.saleUsd != "0" && data.buyEur != "0" && data.saleEur != "0" && data.buyGbp != "0"
                    && data.saleGbp != "0" && data.buyChf != "0" && data.saleChf != "0" && data.buyJpy != "0" && data.saleJpy != "0"
                    && data.buyRub != "0" && data.saleRub != "0"
                )
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "${data.bankName}  | so'mda\nUSD: ${data.buyUsd} - ${data.saleUsd}\nEUR: ${data.buyEur} - ${data.saleEur}\nGBP: ${data.buyGbp} - ${data.saleGbp}\nCHF: ${data.buyChf} - ${data.saleChf}\nJPY: ${data.buyJpy} - ${data.saleJpy}\nRUB: ${data.buyRub} - ${data.saleRub}"
                    )
                startActivity(Intent.createChooser(shareIntent, "Kurslarni ulashish"))
            }
            viewCalculator.setOnClickListener {
                Dialogs().calculateDialog(this@ItemBankActivity, data)
            }
        }
    }
}
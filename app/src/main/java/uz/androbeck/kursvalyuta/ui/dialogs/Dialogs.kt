package uz.androbeck.kursvalyuta.ui.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.androbeck.kursvalyuta.DialogListener
import uz.androbeck.kursvalyuta.R
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.hideKeyBoard
import uz.androbeck.kursvalyuta.toast
import uz.androbeck.kursvalyuta.utils.Helper

@SuppressLint("SetTextI18n")
class Dialogs(private val listener: DialogListener? = null) {

    // Calculate valyuta /////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("UseCompatLoadingForDrawables")
    fun calculateDialog(
        activity: Activity,
        data: BanksModel
    ) {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_calculator, null)
        builder.setView(view)
        val kursTypeDialog = builder.create()
        kursTypeDialog.setCancelable(false)
        kursTypeDialog.show()
        val autoComplete = view.findViewById<AutoCompleteTextView>(R.id.auto_complete_tv)
        val etInputSum = view.findViewById<TextInputEditText>(R.id.et_input_sum)
        val btnClose = view.findViewById<MaterialButton>(R.id.btn_close)
        val btnCalculate = view.findViewById<MaterialButton>(R.id.btn_calculate)
        val tvResult = view.findViewById<AppCompatTextView>(R.id.tv_result)
        val ivFlag = view.findViewById<AppCompatImageView>(R.id.iv_flag)
        val arrayAdapter = ArrayAdapter(
            activity,
            R.layout.drop_down_item,
            stringArray(data)
        )
        autoComplete.setAdapter(arrayAdapter)
        autoComplete.setOnItemClickListener { _, _, position, _ ->
            setEndDrawable(position, ivFlag)
        }
        autoComplete.threshold = 1
        btnClose.setOnClickListener {
            kursTypeDialog.dismiss()
        }
        autoComplete.setOnClickListener {
            activity.hideKeyBoard(it)
        }
        btnCalculate.setOnClickListener {
            val kurs = autoComplete.text.toString().substring(13, autoComplete.text.lastIndex - 4)
                .replace(" ", "")
            val inputSum = etInputSum.text.toString().trim()
            if (inputSum.isNotEmpty() && autoComplete.text.toString() != "Valyuta kursini tanlang") {
                val inputSumInt = inputSum.toInt()
                val kursInt = kurs.toInt()
                val result = kursInt * inputSumInt
                tvResult.text = "Qiymat : ${Helper.formatNumber(result)} so'm"
            } else
                activity.toast("Iltimos maydonni to'ldiring")

        }
    }

    private fun setEndDrawable(
        position: Int,
        ivFlag: AppCompatImageView
    ) {
        when (position) {
            0 -> ivFlag.setImageResource(R.drawable.ic_united_states_flag)
            1 -> ivFlag.setImageResource(R.drawable.ic_united_states_flag)
            2 -> ivFlag.setImageResource(R.drawable.ic_europe_flag)
            3 -> ivFlag.setImageResource(R.drawable.ic_europe_flag)
            4 -> ivFlag.setImageResource(R.drawable.ic_england_flag)
            5 -> ivFlag.setImageResource(R.drawable.ic_england_flag)
            6 -> ivFlag.setImageResource(R.drawable.ic_switzerland_flag)
            7 -> ivFlag.setImageResource(R.drawable.ic_switzerland_flag)
            8 -> ivFlag.setImageResource(R.drawable.ic_japan_flag)
            9 -> ivFlag.setImageResource(R.drawable.ic_japan_flag)
            10 -> ivFlag.setImageResource(R.drawable.ic_russia_flag)
            11 -> ivFlag.setImageResource(R.drawable.ic_russia_flag)
        }
    }

    private fun stringArray(data: BanksModel): ArrayList<String> {
        val stringArray = ArrayList<String>()
        if (data.buyUsd != "0" && data.saleUsd != "0") {
            stringArray.add("USD olish  : ${data.buyUsd} so'm")
            stringArray.add("USD sotish : ${data.saleUsd} so'm")
        }
        if (data.buyEur != "0" && data.saleEur != "0") {
            stringArray.add("EUR olish  : ${data.buyEur} so'm")
            stringArray.add("EUR sotish : ${data.saleEur} so'm")
        }
        if (data.buyGbp != "0" && data.saleGbp != "0") {
            stringArray.add("GBP olish  : ${data.buyGbp} so'm")
            stringArray.add("GBP sotish : ${data.saleGbp} so'm")
        }
        if (data.buyChf != "0" && data.saleChf != "0") {
            stringArray.add("CHF olish  : ${data.buyChf} so'm")
            stringArray.add("CHF sotish : ${data.saleChf} so'm")
        }
        if (data.buyJpy != "0" && data.saleJpy != "0") {
            stringArray.add("JPY olish  : ${data.buyJpy} so'm")
            stringArray.add("JPY sotish : ${data.saleJpy} so'm")
        }
        if (data.buyRub != "0" && data.saleRub != "0") {
            stringArray.add("RUB olish  : ${data.buyRub} so'm")
            stringArray.add("RUB sotish : ${data.saleRub} so'm")
        }
        return stringArray
    }
    // Calculate valyuta /////////////////////////////////////////////////////////////////////////////////////////////////////

    // Choose buy and sale valyuta //////////////////////////////////////////////////////////////////////////////////////////
    fun showDialog(
        activity: Activity
    ) {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_kurs_type_fragment, null)
        builder.setView(view)
        val kursTypeDialog = builder.create()
        kursTypeDialog.show()
        val llBuy = view.findViewById<LinearLayout>(R.id.ll_buy)
        val llSale = view.findViewById<LinearLayout>(R.id.ll_sale)
        llBuy.setOnClickListener {
            listener?.itemBuyOrSaleValyutaDialogClick("buy")
            kursTypeDialog.dismiss()
        }
        llSale.setOnClickListener {
            listener?.itemBuyOrSaleValyutaDialogClick("sale")
            kursTypeDialog.dismiss()
        }
    }
    // Choose buy and sale valyuta //////////////////////////////////////////////////////////////////////////////////////////

    // Choose buy and sale all type valyuta //////////////////////////////////////////////////////////////////////////////////////////
    fun showBuyAndSaleAllValyutaDialog(activity: Activity, buyOrSale: String) {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_all_kurs_type_fragment, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        val llUsd = view.findViewById<LinearLayout>(R.id.ll_usd)
        val llEur = view.findViewById<LinearLayout>(R.id.ll_eur)
        val llGbp = view.findViewById<LinearLayout>(R.id.ll_gbp)
        val llChf = view.findViewById<LinearLayout>(R.id.ll_chf)
        val llJpy = view.findViewById<LinearLayout>(R.id.ll_jpy)
        val llRub = view.findViewById<LinearLayout>(R.id.ll_rub)
        llUsd.setOnClickListener {
            listener?.itemAllKursTypeDialogClick(buyOrSale, "usd")
            dialog.dismiss()
        }
        llEur.setOnClickListener {
            listener?.itemAllKursTypeDialogClick(buyOrSale, "eur")
            dialog.dismiss()
        }
        llGbp.setOnClickListener {
            listener?.itemAllKursTypeDialogClick(buyOrSale, "gbp")
            dialog.dismiss()
        }
        llChf.setOnClickListener {
            listener?.itemAllKursTypeDialogClick(buyOrSale, "chf")
            dialog.dismiss()
        }
        llJpy.setOnClickListener {
            listener?.itemAllKursTypeDialogClick(buyOrSale, "jpy")
            dialog.dismiss()
        }
        llRub.setOnClickListener {
            listener?.itemAllKursTypeDialogClick(buyOrSale, "rub")
            dialog.dismiss()
        }
    }
    // Choose buy and sale all type valyuta //////////////////////////////////////////////////////////////////////////////////////////

    // No connection dialog //////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun showNoConnectionDialog(activity: Activity, lifecycleScope: CoroutineScope) {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_no_connection, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        lifecycleScope.launch {
            delay(5000L)
            dialog.dismiss()
        }
    }
    // No connection dialog //////////////////////////////////////////////////////////////////////////////////////////////////////////
}
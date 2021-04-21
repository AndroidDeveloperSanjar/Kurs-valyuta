package uz.androbeck.kursvalyuta.ui.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import uz.androbeck.kursvalyuta.DialogListener
import uz.androbeck.kursvalyuta.R
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.db.preferences.PreferencesManager
import uz.androbeck.kursvalyuta.toast

@SuppressLint("SetTextI18n")
class Dialogs(private val listener: DialogListener? = null) {

    // Calculate valyuta
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
        val arrayAdapter = ArrayAdapter(
            activity,
            R.layout.drop_down_item,
            stringArray(data)
        )
        autoComplete.setAdapter(arrayAdapter)
        autoComplete.setOnItemClickListener { _, _, position, _ ->
            setEndDrawable(position, activity, etInputSum)
        }
        btnClose.setOnClickListener {
            kursTypeDialog.dismiss()
        }
        btnCalculate.setOnClickListener {
            var kurs = ""
            when (data.bankName) {
                "Asaka bank" -> {
                    kurs =
                        autoComplete.text.toString().substring(13, autoComplete.text.lastIndex - 7)
                            .replace(" ", "")
                }
                else -> {
                    kurs =
                        autoComplete.text.toString().substring(13, autoComplete.text.lastIndex - 4)
                }
            }
            val inputSum = etInputSum.text.toString().trim()
            //val result = kurs.toInt() * inputSum
            if (inputSum.isNotEmpty()) {
                val inputSumInt = inputSum.toInt()
                val kursInt = kurs.toInt()
                val result = kursInt * inputSumInt
                tvResult.text = "Qiymat : $result so'm"
            } else
                activity.toast("Iltimos maydonni to'ldiring")
            //println(inputSum)
        }
    }

    private fun setEndDrawable(position: Int, activity: Activity, etInputSum: TextInputEditText) {
        when (position) {
            0 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_dollar)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
            1 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_dollar)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
            2 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_svg_euro_24)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
            3 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_svg_euro_24)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
            4 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_gmp_svg)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
            5 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_gmp_svg)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
            6 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_chf_svg)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
            7 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_chf_svg)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
            8 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_jpy_svg)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
            9 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_jpy_svg)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
            10 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_rub_svg)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
            11 -> {
                val drawable = ContextCompat.getDrawable(activity, R.drawable.ic_rub_svg)
                etInputSum.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    drawable,
                    null
                )
            }
        }
    }

    private fun stringArray(data: BanksModel): ArrayList<String> {
        val stringArray = ArrayList<String>()
        if (data.buyUsd != "" && data.saleUsd != "") {
            stringArray.add("USD olish  : ${data.buyUsd}")
            stringArray.add("USD sotish : ${data.saleUsd}")
        }
        if (data.buyEur != "" && data.saleEur != "") {
            stringArray.add("EUR olish  : ${data.buyEur}")
            stringArray.add("EUR sotish : ${data.saleEur}")
        }
        if (data.buyGbp != "" && data.saleGbp != "") {
            stringArray.add("GBP  olish  : ${data.buyGbp}")
            stringArray.add("GBP sotish : ${data.saleGbp}")
        }
        if (data.buyChf != "" && data.saleChf != "") {
            stringArray.add("CHF olish  : ${data.buyChf}")
            stringArray.add("CHF sotish : ${data.saleChf}")
        }
        if (data.buyJpy != "" && data.saleJpy != "") {
            stringArray.add("JPY olish  : ${data.buyJpy}")
            stringArray.add("JPY sotish : ${data.saleJpy}")
        }
        if (data.buyRub != "" && data.saleRub != "") {
            stringArray.add("RUB olish  : ${data.buyRub}")
            stringArray.add("RUB sotish : ${data.saleRub}")
        }
        return stringArray
    }
    // Calculate valyuta

    // Choose kurs type dialog logic //
    fun showDialog(
        activity: Activity,
        preferenceManager: PreferencesManager
    ) {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_kurs_type_fragment, null)
        builder.setView(view)
        val kursTypeDialog = builder.create()
        kursTypeDialog.show()
        val llDollar = view.findViewById<LinearLayout>(R.id.ll_dollar)
        val llRuble = view.findViewById<LinearLayout>(R.id.ll_ruble)
        val llEuro = view.findViewById<LinearLayout>(R.id.ll_euro)
        setBackgroundLL(preferenceManager, llDollar, llEuro, llRuble)
        llDollar.setOnClickListener {
            preferenceManager.kursDialogValyuta = 0
            setBackgroundLL(preferenceManager, llDollar, llEuro, llRuble)
            //   setKursTypeToTV(preferenceManager)
            listener?.itemKursTypeDialogClick()
            kursTypeDialog.dismiss()
        }
        llRuble.setOnClickListener {
            preferenceManager.kursDialogValyuta = 1
            setBackgroundLL(preferenceManager, llDollar, llEuro, llRuble)
            //   setKursTypeToTV(preferenceManager)
            listener?.itemKursTypeDialogClick()
            kursTypeDialog.dismiss()
        }
        llEuro.setOnClickListener {
            preferenceManager.kursDialogValyuta = 2
            setBackgroundLL(preferenceManager, llDollar, llEuro, llRuble)
            listener?.itemKursTypeDialogClick()
            // setKursTypeToTV(preferenceManager)
            kursTypeDialog.dismiss()
        }
    }

    private fun setBackgroundLL(
        preferenceManager: PreferencesManager,
        llDollar: LinearLayout,
        llEuro: LinearLayout,
        llRuble: LinearLayout
    ) {
        when (preferenceManager.kursDialogValyuta) {
            0 -> {
                llDollar.setBackgroundResource(R.color.m_color)
                llEuro.setBackgroundResource(R.color.white)
                llRuble.setBackgroundResource(R.color.white)
            }
            1 -> {
                llRuble.setBackgroundResource(R.color.m_color)
                llDollar.setBackgroundResource(R.color.white)
                llEuro.setBackgroundResource(R.color.white)
            }
            2 -> {
                llEuro.setBackgroundResource(R.color.m_color)
                llRuble.setBackgroundResource(R.color.white)
                llDollar.setBackgroundResource(R.color.white)
            }
        }
    }

    // Choose kurs type dialog logic //
}
package uz.androbeck.kursvalyuta.ui.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import uz.androbeck.kursvalyuta.DialogListener
import uz.androbeck.kursvalyuta.R
import uz.androbeck.kursvalyuta.adapter.model.BanksModel
import uz.androbeck.kursvalyuta.db.preferences.PreferencesManager

@SuppressLint("SetTextI18n")
class Dialogs(private val listener: DialogListener? = null) {

    fun calculateDialog(
        activity: Activity,
        data: BanksModel
    ) {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_calculator, null)
        builder.setView(view)
        val kursTypeDialog = builder.create()
        kursTypeDialog.show()
        val autoComplete = view.findViewById<AutoCompleteTextView>(R.id.auto_complete_tv)
        val arrayAdapter = ArrayAdapter(
            activity,
            R.layout.drop_down_item,
            arrayOf(data.buyUsd, data.saleUsd, data.buyEur, data.saleEur)
        )
        autoComplete.setAdapter(arrayAdapter)
    }

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
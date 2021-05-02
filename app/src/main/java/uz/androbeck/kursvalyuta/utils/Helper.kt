package uz.androbeck.kursvalyuta.utils

import uz.androbeck.kursvalyuta.R
import java.text.DecimalFormat

object Helper {
    fun formatNumber(result: Int): String? {
        var decimal: DecimalFormat? = null
        when (result) {
            in 10000..99999 -> decimal = DecimalFormat("##,###")
            in 100000..999999 -> decimal = DecimalFormat("###,###")
            in 1000000..9999999 -> decimal = DecimalFormat("#,###,###")
            in 10000000..99999999 -> decimal = DecimalFormat("##,###,###")
            in 100000000..999999999 -> decimal = DecimalFormat("###,###,###")
            in 10000000000..99999999999 -> decimal = DecimalFormat("#,###,###,###")
        }
        return if (decimal != null)
            decimal.format(result)
        else
            "$result"
    }

    fun bankLogoList(): ArrayList<LogoModel> {
        val bankLogoList = ArrayList<LogoModel>()
        bankLogoList.add(LogoModel("Ipoteka bank", R.drawable.ipoteka_bank_logo))
        bankLogoList.add(LogoModel("Infinbank", R.drawable.infin_bank_logo))
        bankLogoList.add(LogoModel("Davr bank", R.drawable.davrbank_logo))
        bankLogoList.add(LogoModel("Poytaxt bank", R.drawable.poytaxt_bank_logo))
        bankLogoList.add(LogoModel("Tenge Bank", R.drawable.tenge_bank))
        bankLogoList.add(LogoModel("Asaka bank", R.drawable.asaka_bank_logo))
        bankLogoList.add(LogoModel("Savdogar bank", R.drawable.savdogarbank_logo))
        bankLogoList.add(LogoModel("Universal bank", R.drawable.universal_bank_logo))
        bankLogoList.add(LogoModel("Hamkorbank", R.drawable.hamkor_bank_logo))
        bankLogoList.add(LogoModel("Saderat Bank", R.drawable.sadertat_bank_logo))
        bankLogoList.add(LogoModel("Madad Invest Bank", R.drawable.madad_invest_bank_logo))
        bankLogoList.add(LogoModel("Uzagroeksportbank", R.drawable.uzagroexport_bank))
        bankLogoList.add(LogoModel("Turkiston bank", R.drawable.turkiston_bank_logo))
        bankLogoList.add(LogoModel("Mikrokreditbank", R.drawable.mikro_kredit_bank_logo))
        bankLogoList.add(LogoModel("Trastbank", R.drawable.trast_bank_logo))
        bankLogoList.add(LogoModel("Turon bank", R.drawable.turon_bank_logo))
        bankLogoList.add(LogoModel("Xalq banki", R.drawable.xalq_banki_logo))
        bankLogoList.add(LogoModel("Aloqabank", R.drawable.aloqa_bank_logo))
        bankLogoList.add(LogoModel("NBU", R.drawable.nbu_logo))
        bankLogoList.add(LogoModel("Kapitalbank", R.drawable.kapital_bank_logo))
        bankLogoList.add(LogoModel("Ravnaq-bank", R.drawable.ravnaq_bank_logo))
        bankLogoList.add(LogoModel("Agrobank", R.drawable.agro_bank_logo))
        bankLogoList.add(LogoModel("Asia Alliance Bank", R.drawable.asia_allience_bank_logo))
        bankLogoList.add(LogoModel("Orient Finans Bank", R.drawable.ofb_logo))
        bankLogoList.add(LogoModel("Anor bank", R.drawable.anb_logo))
        bankLogoList.add(LogoModel("Ziraat Bank", R.drawable.zirat_bank_logo))
        bankLogoList.add(LogoModel("Uzsanoatqurulishbank", R.drawable.sanoat_qurilish_bank_logo))
        return bankLogoList
    }
}

data class LogoModel(
    val bankName: String = "",
    val logo: Int = -1
)
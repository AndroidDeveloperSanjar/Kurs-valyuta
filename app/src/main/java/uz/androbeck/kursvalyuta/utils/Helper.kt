package uz.androbeck.kursvalyuta.utils

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
}
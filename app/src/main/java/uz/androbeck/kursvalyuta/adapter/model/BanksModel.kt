package uz.androbeck.kursvalyuta.adapter.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BanksModel(
    var banksLogo: Int = -1,
    var bankName: String = "",
    var buyUsd: String = "",
    var saleUsd: String = "",
    var buyEur: String = "",
    var saleEur: String = "",
    var buyGbp: String = "",
    var saleGbp: String = "",
    var buyChf: String = "",
    var saleChf: String = "",
    var buyJpy: String = "",
    var saleJpy: String = "",
    var buyRub: String = "",
    var saleRub: String = "",
    var buyUsdAtm: String = "",
    var saleUsdAtm: String = "",
) : Parcelable

package uz.androbeck.kursvalyuta.adapter.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BanksModel(
    var banksLogo: Int = -1,
    var bankName: String = "0",
    var buyUsd: String = "0",
    var saleUsd: String = "0",
    var buyEur: String = "0",
    var saleEur: String = "0",
    var buyGbp: String = "0",
    var saleGbp: String = "0",
    var buyJpy: String = "0",
    var saleJpy: String = "0",
    var buyRub: String = "0",
    var saleRub: String = "0",
) : Parcelable

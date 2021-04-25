package uz.androbeck.kursvalyuta.adapter.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BanksModel(
    var bankId: Int = -1,
    var banksLogo: Int = -1,
    var bankName: String = "0",
    var buyUsd: String = "0",
    var saleUsd: String = "0",
    var buyEur: String = "0",
    var saleEur: String = "0",
    var buyGbp: String = "0",
    var saleGbp: String = "0",
    var buyChf: String = "0",
    var saleChf: String = "0",
    var buyJpy: String = "0",
    var saleJpy: String = "0",
    var buyRub: String = "0",
    var saleRub: String = "0",
    var buyUsdAtm: String = "0",
    var saleUsdAtm: String = "0",
    var cbuUsd: String = "0",
    var cbuEur: String = "0",
    var cbuRub: String = "0",
    var cbuGbp: String = "0",
    var cbuJpy: String = "0",
    var cbuChf: String = "0",
) : Parcelable

package uz.androbeck.kursvalyuta.adapter.model

data class BanksModel(
    var banksLogo: Int = -1,
    var bankName: String = "",
    var buyUsd: String = "",
    var saleUsd: String = "",
    var buyEur: String = "",
    var saleEur: String = "",
    var buyGbp: String = "",
    var saleGbp: String = "",
)

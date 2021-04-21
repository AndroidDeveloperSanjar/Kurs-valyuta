package uz.androbeck.kursvalyuta

interface DialogListener {
    fun itemBuyOrSaleValyutaDialogClick(buyOrSale: String)
    fun itemAllKursTypeDialogClick(buyOrSale: String, typeKursValyuta: String)
}
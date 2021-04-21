package uz.androbeck.kursvalyuta

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snacbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snacbar.setAction("Retry") {
            it()
        }
    }
    snacbar.show()
}

fun Activity.toast(message: String, isLong: Boolean = false) {
    if (!isLong)
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    else
        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
}

fun Activity.hideKeyBoard(v: View) {
    val imm =
        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(v.windowToken, 0)
}
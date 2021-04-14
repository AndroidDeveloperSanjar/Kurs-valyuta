package uz.androbeck.kursvalyuta.db.preferences

import android.content.Context
import androidx.preference.PreferenceManager

class PreferencesManager(
    private val context: Context
) {

    private val preferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(
            context
        )
    }

    var kursDialogValyuta by PreferencesDelegate(preferences, KURS_DIALOG_VALYUTA, 0)

    companion object {
        private const val KURS_DIALOG_VALYUTA = "kurs_dialog_valyuta"
    }

}
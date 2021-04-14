package uz.androbeck.kursvalyuta.ui.dialogs.kurs_type

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import uz.androbeck.kursvalyuta.R
import uz.androbeck.kursvalyuta.databinding.DialogKursTypeFragmentBinding
import uz.androbeck.kursvalyuta.db.preferences.PreferencesManager

class KursTypeDialogFragment : DialogFragment(R.layout.dialog_kurs_type_fragment) {

    private val binding: DialogKursTypeFragmentBinding by viewBinding()

    private lateinit var preferenceManager: PreferencesManager

    override fun onStart() {
        super.onStart()
        val customWidth = resources.getDimensionPixelSize(R.dimen._240sdp)
        val customHeight = LinearLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(
            customWidth,
            customHeight
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        clicks()
    }

    private fun init() {
        preferenceManager = PreferencesManager(requireContext())
        setBackgroundLL()
    }

    private fun clicks() {
        with(binding) {
            llDollar.setOnClickListener {
                preferenceManager.kursDialogValyuta = 0
                setBackgroundLL()
                dismiss()
            }
            llRuble.setOnClickListener {
                preferenceManager.kursDialogValyuta = 1
                setBackgroundLL()
                dismiss()
            }
            llEuro.setOnClickListener {
                preferenceManager.kursDialogValyuta = 2
                setBackgroundLL()
                dismiss()
            }
        }
    }

    private fun setBackgroundLL() {
        with(binding) {
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
    }
}
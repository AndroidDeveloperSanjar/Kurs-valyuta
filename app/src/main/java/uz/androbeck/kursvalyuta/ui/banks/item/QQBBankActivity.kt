package uz.androbeck.kursvalyuta.ui.banks.item

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import uz.androbeck.kursvalyuta.databinding.ActivityQQBBankBinding

class QQBBankActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQQBBankBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQQBBankBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url!!)
                    return true
                }
            }
            webView.loadUrl("https://qishloqqurilishbank.uz/currency-rates/")
        }
    }
}
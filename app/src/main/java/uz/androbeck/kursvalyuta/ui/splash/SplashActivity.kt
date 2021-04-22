package uz.androbeck.kursvalyuta.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.androbeck.kursvalyuta.databinding.ActivitySplashBinding
import uz.androbeck.kursvalyuta.ui.banks.BanksActivity

class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)
        lifecycleScope.launch {
            delay(3000L)
            startActivity(Intent(this@SplashActivity, BanksActivity::class.java))
            finish()
        }
    }
}
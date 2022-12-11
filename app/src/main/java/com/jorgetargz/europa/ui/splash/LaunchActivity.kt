package com.jorgetargz.europa.ui.splash

import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.jorgetargz.europa.databinding.ActivityLaunchBinding
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.main.MainActivity
import com.jorgetargz.europa.ui.utils.loadUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.ivLogo1.loadUrl(Constantes.SPLASH_IMAGE_1)
        binding.ivLogo2.loadUrl(Constantes.SPLASH_IMAGE_2)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}

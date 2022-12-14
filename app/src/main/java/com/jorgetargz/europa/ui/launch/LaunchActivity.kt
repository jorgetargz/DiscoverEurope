package com.jorgetargz.europa.ui.launch

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

        with(binding) {
            ivLogo.loadUrl(Constantes.SPLASH_IMAGE)
        }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }
}

package com.example.asf_jets.splash_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.asf_jets.MainActivity
import com.example.asf_jets.auth.LoginActivity
import com.example.asf_jets.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan Splash Screen (Layout 1-A) selama 2 detik
        Handler(Looper.getMainLooper()).postDelayed({
            checkSession()
        }, 2000)
    }

    private fun checkSession() {
        val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // Pengguna sudah login -> langsung ke Main
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            // Pengguna belum login -> arahkan ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}
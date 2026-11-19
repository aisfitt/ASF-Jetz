package com.example.asf_jets.splash_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.asf_jets.BaseActivity
import com.example.asf_jets.auth.LoginActivity
import com.example.asf_jets.databinding.ActivitySplashScreenBinding
import com.example.asf_jets.onboarding.OnboardingActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan Splash Screen selama 2 detik
        Handler(Looper.getMainLooper()).postDelayed({
            checkSession()
        }, 2000)
    }

    private fun checkSession() {
        val appPref = getSharedPreferences("AppPref", Context.MODE_PRIVATE)
        val isOnboardingDone = appPref.getBoolean("isOnboardingDone", false)

        val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (!isOnboardingDone) {
            // Pertama kali membuka aplikasi -> tampilkan Onboard Screen
            startActivity(Intent(this, OnboardingActivity::class.java))
        } else if (isLoggedIn) {
            // Sudah login -> langsung ke BaseActivity
            startActivity(Intent(this, BaseActivity::class.java))
        } else {
            // Belum login -> LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}
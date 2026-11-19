package com.example.asf_jets.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asf_jets.BaseActivity
import com.example.asf_jets.auth.LoginActivity
import com.example.asf_jets.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup ViewPager dengan adapter
        val fragmentsList = listOf(
            OnboardingFragment.newInstance(
                "Lihat Katalog Bibit",
                "Lihat varietas, harga, umur, dan stok bibit sebelum memesan."
            ),
            OnboardingFragment.newInstance(
                "Pesan Bibit Sawit",
                "Pilih bibit, jumlah, dan isi data pemesanan dengan mudah."
            ),
            OnboardingFragment.newInstance(
                "Pantau dan Beri Ulasan",
                "Pantau perkembangan pesanan, lalu beri rating dan ulasan setelah pembelian.",
                true
            )
        )
        val adapter = OnboardingFragmentAdapter(this, fragmentsList)
        binding.onboardingViewPager.adapter = adapter

        // Hubungkan DotsIndicator dengan ViewPager
        binding.dotIndicator.attachTo(binding.onboardingViewPager)

        // Tombol Lewati
        binding.tvLewati.setOnClickListener {
            selesaiOnboarding()
        }
    }

    // Dipanggil dari tombol Lewati dan tombol Mulai (di OnboardingFragment)
    fun selesaiOnboarding() {
        // Simpan penanda: onboarding sudah pernah dilihat
        val appPref = getSharedPreferences("AppPref", Context.MODE_PRIVATE)
        val editor = appPref.edit()
        editor.putBoolean("isOnboardingDone", true)
        editor.apply()

        // Lanjut: sudah login -> BaseActivity, belum -> LoginActivity
        val userPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val isLoggedIn = userPref.getBoolean("isLoggedIn", false)

        val intent = if (isLoggedIn) {
            Intent(this, BaseActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        finish() // Tutup onboarding agar tidak bisa kembali dengan tombol Back
    }
}
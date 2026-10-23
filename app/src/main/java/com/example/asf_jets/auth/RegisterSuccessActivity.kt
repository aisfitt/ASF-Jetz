package com.example.asf_jets.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asf_jets.databinding.ActivityRegisterSuccessBinding

class RegisterSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil nama yang dikirim dari RegisterActivity
        val nama = intent.getStringExtra("EXTRA_NAMA")
        binding.tvSalam.text = "Halo, $nama!"

        binding.btnKeLogin.setOnClickListener {
            keLogin()
        }

        // Tombol Back sistem juga diarahkan ke Login
        onBackPressedDispatcher.addCallback(this) {
            keLogin()
        }
    }

    private fun keLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        // Hapus halaman Register Gmail, Registrasi, dan Success dari stack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}
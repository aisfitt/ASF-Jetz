package com.example.asf_jets.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asf_jets.BaseActivity
import com.example.asf_jets.MainActivity
import com.example.asf_jets.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty()) {
                binding.etUsername.error = "Username nggak boleh kosong!"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Password nggak boleh kosong!"
                return@setOnClickListener
            }

            // === SIMPAN SESI LOGIN ===
            val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("isLoggedIn", true)
            editor.putString("username", username)
            editor.apply()

            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()

            // Pindah ke MainActivity
            val intent = Intent(this@LoginActivity, BaseActivity::class.java)
            intent.putExtra("EXTRA_USERNAME", username)
            startActivity(intent)
            finish() // Tutup LoginActivity agar pengguna tidak bisa kembali ke login pakai tombol Back
        }


    }
}
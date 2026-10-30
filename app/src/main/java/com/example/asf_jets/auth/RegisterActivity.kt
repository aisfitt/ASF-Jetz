package com.example.asf_jets.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asf_jets.R
import com.example.asf_jets.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar dengan tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Registrasi"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        // Ambil email yang dikirim dari RegisterEmailActivity
        val emailDariIntent = intent.getStringExtra("EXTRA_EMAIL")
        Log.e("Data Intent", "Email: $emailDariIntent")
        binding.etEmail.setText(emailDariIntent)

        binding.btnRegistrasi.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val username = binding.etUsername.text.toString() // tidak di-trim, supaya spasi ikut terdeteksi
            val password = binding.etPassword.text.toString().trim()

            // === VALIDASI REGISTRASI ===
            if (nama.isEmpty()) {
                binding.etNama.error = "Nama tidak boleh kosong!"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                Toast.makeText(
                    this,
                    "Email tidak ditemukan, ulangi dari Register With Gmail",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (username.isEmpty()) {
                binding.etUsername.error = "Username tidak boleh kosong!"
                return@setOnClickListener
            }

            if (username.any { it.isWhitespace() }) {
                binding.etUsername.error = "Username tidak boleh mengandung spasi!"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Password tidak boleh kosong!"
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.etPassword.error = "Password minimal 6 karakter!"
                return@setOnClickListener
            }

            // === SIMPAN DATA REGISTRASI ===
            // Pakai file "RegisterPref" (bukan "UserPref") supaya tidak ikut terhapus saat logout
            val registerPref = getSharedPreferences("RegisterPref", Context.MODE_PRIVATE)
            val editor = registerPref.edit()
            editor.putString("nama", nama)
            editor.putString("email", email)
            editor.putString("username", username)
            editor.putString("password", password)
            editor.apply()

            Log.d("RegisterActivity", "Registrasi berhasil: $username ($email)")

            // Tampilkan halaman informasi "Registrasi Berhasil"
            val intent = Intent(this, RegisterSuccessActivity::class.java)
            intent.putExtra("EXTRA_NAMA", nama)
            startActivity(intent)
            finish() // Tutup halaman registrasi supaya tidak bisa kembali ke form
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
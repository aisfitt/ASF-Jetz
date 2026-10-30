package com.example.asf_jets.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asf_jets.R
import com.example.asf_jets.databinding.ActivityRegisterEmailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegisterEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar dengan tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Register With Gmail"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        binding.btnLanjut.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            // Validasi 1: email tidak boleh kosong
            if (email.isEmpty()) {
                tampilkanError("Email tidak boleh kosong!")
                return@setOnClickListener
            }

            // Validasi 2: email harus berdomain @gmail.com
            if (!isGmailValid(email)) {
                tampilkanError("Email harus menggunakan domain @gmail.com")
                return@setOnClickListener
            }

            // Valid -> ke halaman Registrasi, email dibawa lewat Intent
            Log.d("RegisterEmailActivity", "Email valid: $email")
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("EXTRA_EMAIL", email)
            startActivity(intent)
        }
    }

    private fun isGmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                email.endsWith("@gmail.com", ignoreCase = true)
    }

    private fun tampilkanError(pesan: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Email Tidak Valid")
            .setMessage(pesan)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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
package com.example.asf_jets

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asf_jets.auth.LoginActivity
import com.example.asf_jets.databinding.ActivityMainBinding
import com.example.asf_jets.pofile.ProfileActivity
import kotlin.jvm.java
import androidx.appcompat.app.AlertDialog
import com.example.asf_jets.home.WebActivity
import com.example.asf_jets.pofile.MainActivityresult

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Tombol My Project (Ke halaman Informasi Gizi)
        binding.btnProjek.setOnClickListener {
            Log.d("MainActivity", "Tombol My Project berhasil ditekan")
            val intent = Intent(this, MainActivityresult::class.java)
            startActivity(intent)
        }

        // 2. Tombol Kunjungi Web (Sesuai tugas Pertemuan 5)
        binding.btnWeb.setOnClickListener {
            Log.d("MainActivity", "Tombol Kunjungi Web berhasil ditekan")
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
        }

        // 3. Tombol Profil Developer
        binding.btnProfile.setOnClickListener {
            Log.d("MainActivity", "Tombol Profil Developer berhasil ditekan")
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // 4. Tombol Logout dengan AlertDialog
        val sharedPref = getSharedPreferences("UserPref", MODE_PRIVATE)

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Apakah kamu yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    // Hapus sesi di SharedPreferences
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()

                    // Pindah ke LoginActivity
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }
}
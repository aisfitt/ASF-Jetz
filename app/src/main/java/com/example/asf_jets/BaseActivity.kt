package com.example.asf_jets

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.asf_jets.databinding.ActivityBaseBinding
import com.example.asf_jets.home.HomeFragment
import com.example.asf_jets.katalog.KatalogFragment
import com.example.asf_jets.pesanan.PesananFragment
import com.example.asf_jets.profil.ProfileFragment
import com.example.asf_jets.ulasan.UlasanFragment

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        /** HomeFragment sebagai fragment default */
        replaceFragment(HomeFragment())

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.katalog -> {
                    replaceFragment(KatalogFragment())
                    true
                }
                R.id.pesanan -> {
                    replaceFragment(PesananFragment())
                    true
                }
                R.id.ulasan -> {
                    replaceFragment(UlasanFragment())
                    true
                }
                R.id.profil -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false // return false jika item tidak ada yang di klik
            }
        }
    }
    // Dipanggil dari fragment untuk pindah tab (otomatis memicu setOnItemSelectedListener)
    fun pindahTab(menuId: Int) {
        binding.bottomNavView.selectedItemId = menuId
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            //.addToBackStack(null) -> dinonaktifkan agar saat back langsung keluar aplikasi
            .commit()
    }
}
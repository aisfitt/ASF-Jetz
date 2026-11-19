package com.example.asf_jets.pesanan

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asf_jets.BaseActivity
import com.example.asf_jets.databinding.ActivityPesananDibuatBinding
import com.example.asf_jets.katalog.DataBibit

class PesananDibuatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPesananDibuatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPesananDibuatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pesanan = PesananStore.cari(this, intent.getStringExtra(EXTRA_ID).orEmpty())
        if (pesanan == null) {
            finish()
            return
        }

        binding.tvIdPesanan.text = "No. Pesanan: ${pesanan.id}"
        binding.tvRingkasan.text = "${pesanan.namaBibit}\n" +
                "${pesanan.jumlah} bibit · ${DataBibit.formatRupiah(pesanan.total)}\n" +
                "Penerima: ${pesanan.penerima}\n" +
                "Alamat: ${pesanan.alamat}"

        binding.btnLihatStatus.setOnClickListener {
            startActivity(Intent(this, StatusPesananActivity::class.java))
            finish()
        }
        binding.btnHome.setOnClickListener { kembaliKeHome() }

        // Tombol Back diarahkan ke Home
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                kembaliKeHome()
            }
        })
    }

    private fun kembaliKeHome() {
        val intent = Intent(this, BaseActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
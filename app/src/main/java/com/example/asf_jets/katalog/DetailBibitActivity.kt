package com.example.asf_jets.katalog

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asf_jets.R
import com.example.asf_jets.databinding.ActivityDetailBibitBinding
import com.example.asf_jets.pesanan.PemesananActivity

class DetailBibitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBibitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBibitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Detail Bibit"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        val bibit = DataBibit.cariByNama(intent.getStringExtra(EXTRA_NAMA).orEmpty())
        if (bibit == null) {
            finish()
            return
        }

        binding.tvNama.text = bibit.nama
        binding.tvVarietas.text = bibit.varietas
        binding.tvUmur.text = bibit.umur
        binding.tvHarga.text = "${bibit.harga} / bibit"
        binding.tvStok.text = "Stok: ${bibit.stok} bibit"
        binding.tvDeskripsi.text = buatDeskripsi(bibit)

        binding.btnPesan.setOnClickListener {
            val intent = Intent(this, PemesananActivity::class.java)
            intent.putExtra(PemesananActivity.EXTRA_NAMA, bibit.nama)
            startActivity(intent)
        }
    }

    // Deskripsi sementara, bisa diganti dengan data asli dari web Berkah Palma
    private fun buatDeskripsi(bibit: Bibit): String {
        return "Bibit kelapa sawit varietas ${bibit.varietas} berumur ${bibit.umur}, " +
                "tersedia ${bibit.stok} bibit. Cocok untuk kebun sawit swadaya yang ingin " +
                "memulai penanaman atau peremajaan dengan bibit berkualitas.\n\n" +
                "Pilih jumlah bibit dan isi data pengiriman pada halaman pemesanan. " +
                "Status pesanan dapat dipantau di menu Pesanan."
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

    companion object {
        const val EXTRA_NAMA = "extra_nama"
    }
}
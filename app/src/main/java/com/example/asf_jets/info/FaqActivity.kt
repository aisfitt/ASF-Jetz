package com.example.asf_jets.info

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asf_jets.R
import com.example.asf_jets.databinding.ActivityFaqBinding

class FaqActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "FAQ"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        val daftarFaq = listOf(
            Faq(
                "Bagaimana cara memesan bibit?",
                "Buka menu Katalog, pilih bibit, lihat detailnya, lalu tekan Pesan Sekarang. " +
                        "Isi jumlah dan data pengiriman, kemudian tekan Buat Pesanan."
            ),
            Faq(
                "Bagaimana memantau status pesanan?",
                "Buka menu Pesanan lalu pilih Status Pesanan, atau tekan Lihat Status di Home. " +
                        "Tahapnya: Diproses, Dikirim, lalu Selesai."
            ),
            Faq(
                "Bagaimana memberi ulasan?",
                "Setelah pesanan berstatus Selesai, tekan Beri Ulasan atau buka menu Ulasan, " +
                        "lalu isi rating dan komentar."
            ),
            Faq(
                "Bagaimana menghubungi Berkah Palma?",
                "Tekan WhatsApp di bagian Info Perusahaan pada Home. Anda akan diarahkan " +
                        "langsung ke chat WhatsApp Berkah Palma."
            )
        )

        binding.rvFaq.layoutManager = LinearLayoutManager(this)
        binding.rvFaq.adapter = FaqAdapter(daftarFaq)
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
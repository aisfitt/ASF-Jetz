package com.example.asf_jets.pesanan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asf_jets.R
import com.example.asf_jets.databinding.ActivityPemesananBinding
import com.example.asf_jets.katalog.Bibit
import com.example.asf_jets.katalog.DataBibit

class PemesananActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPemesananBinding
    private lateinit var bibit: Bibit
    private var jumlah = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPemesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Pemesanan Bibit"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        // Ambil bibit yang dipilih dari Detail Bibit
        val dipilih = DataBibit.cariByNama(intent.getStringExtra(EXTRA_NAMA).orEmpty())
        if (dipilih == null) {
            finish()
            return
        }
        bibit = dipilih

        binding.tvNamaBibit.text = bibit.nama
        binding.tvHargaSatuan.text = "${bibit.harga} / bibit"
        binding.tvStokInfo.text = "Stok tersedia: ${bibit.stok}"

        // Nama penerima diisi dari username saat login
        val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        binding.etNama.setText(sharedPref.getString("username", ""))

        perbaruiJumlah()

        binding.btnKurang.setOnClickListener {
            if (jumlah > 1) {
                jumlah--
                perbaruiJumlah()
            }
        }
        binding.btnTambah.setOnClickListener {
            if (jumlah < bibit.stok) {
                jumlah++
                perbaruiJumlah()
            } else {
                Toast.makeText(this, "Jumlah melebihi stok", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBuat.setOnClickListener { buatPesanan() }
    }

    private fun perbaruiJumlah() {
        binding.tvJumlah.text = jumlah.toString()
        binding.tvTotal.text = DataBibit.formatRupiah(DataBibit.hargaAngka(bibit) * jumlah)
    }

    private fun buatPesanan() {
        val nama = binding.etNama.text.toString().trim()
        val nomor = binding.etNomor.text.toString().trim()
        val alamat = binding.etAlamat.text.toString().trim()

        // Validasi
        if (nama.isEmpty()) {
            binding.etNama.error = "Nama penerima wajib diisi"
            return
        }
        if (nomor.length < 9) {
            binding.etNomor.error = "Nomor WhatsApp tidak valid"
            return
        }
        if (alamat.isEmpty()) {
            binding.etAlamat.error = "Alamat pengiriman wajib diisi"
            return
        }

        val pesanan = Pesanan(
            id = PesananStore.buatId(),
            namaBibit = bibit.nama,
            jumlah = jumlah,
            total = DataBibit.hargaAngka(bibit) * jumlah,
            penerima = nama,
            nomorWa = nomor,
            alamat = alamat,
            catatan = binding.etCatatan.text.toString().trim(),
            status = Pesanan.DIPROSES,
            waktu = System.currentTimeMillis()
        )
        PesananStore.simpan(this, pesanan)

        val intent = Intent(this, PesananDibuatActivity::class.java)
        intent.putExtra(PesananDibuatActivity.EXTRA_ID, pesanan.id)
        startActivity(intent)
        finish()
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
package com.example.asf_jets.pesanan

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asf_jets.BaseActivity
import com.example.asf_jets.R
import com.example.asf_jets.databinding.ActivityStatusPesananBinding

class StatusPesananActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatusPesananBinding
    private lateinit var statusAdapter: StatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityStatusPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Status Pesanan"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        val daftar = PesananStore.semua(this)
        binding.tvKosong.visibility = if (daftar.isEmpty()) View.VISIBLE else View.GONE

        statusAdapter = StatusAdapter(daftar) { pesanan ->
            if (pesanan.status == Pesanan.SELESAI) {
                // Pesanan selesai -> buka tab Ulasan
                val intent = Intent(this, BaseActivity::class.java)
                intent.putExtra(BaseActivity.EXTRA_TAB, R.id.ulasan)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } else {
                // Simulasi perubahan status (belum ada backend)
                val statusBaru = pesanan.status + 1
                PesananStore.ubahStatus(this, pesanan.id, statusBaru)
                statusAdapter.perbarui(pesanan.copy(status = statusBaru))
            }
        }
        binding.rvStatus.layoutManager = LinearLayoutManager(this)
        binding.rvStatus.adapter = statusAdapter
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
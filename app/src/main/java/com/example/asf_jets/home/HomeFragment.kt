package com.example.asf_jets.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.asf_jets.BaseActivity
import com.example.asf_jets.R
import com.example.asf_jets.databinding.FragmentHomeBinding
import com.example.asf_jets.info.FaqActivity
import com.example.asf_jets.info.InfoPerusahaanActivity
import com.example.asf_jets.katalog.Bibit
import com.example.asf_jets.katalog.DataBibit
import com.example.asf_jets.katalog.DetailBibitActivity
import com.example.asf_jets.pesanan.StatusPesananActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Home"
        }

        // Sapaan memakai username yang disimpan saat login
        val sharedPref = requireContext().getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna")
        binding.tvGreeting.text = "Hi, $username"

        // Grid Layanan Utama (RecyclerView + GridLayoutManager)
        val daftarLayanan = listOf(
            LayananModel("Katalog Bibit", "Melihat varietas, harga, umur, dan stok.", R.drawable.ic_catalog_hitam, R.id.katalog),
            LayananModel("Pemesanan Bibit", "Memilih bibit, jumlah, dan mengisi data pemesanan.", R.drawable.ic_pesanan_hitam, R.id.pesanan),
            LayananModel("Ulasan Customer", "Memberikan rating dan ulasan setelah pembelian.", R.drawable.ic_ulasan_hitam, R.id.ulasan),
            LayananModel("Profil", "Akun, profil developer, dan logout.", R.drawable.ic_profil_hitam, R.id.profil)
        )

        val layananAdapter = LayananAdapter(daftarLayanan) { layanan ->
            Log.d("HomeFragment", "Layanan dipilih: ${layanan.judul}")
            pindahTab(layanan.tujuan)
        }

        binding.rvLayanan.apply {
            /** Mode Grid **/
            layoutManager = GridLayoutManager(requireContext(), 2)

            /** Jika ingin model Linear **/
            //layoutManager = LinearLayoutManager(requireContext())

            this.adapter = layananAdapter
        }

        // Tombol cari -> tab Katalog
        binding.cvSearch.setOnClickListener {
            pindahTab(R.id.katalog)
        }

        // Bibit pilihan -> Detail Bibit
        binding.cvBibit1.setOnClickListener {
            bukaDetail(DataBibit.semua[0])
        }
        binding.cvBibit2.setOnClickListener {
            bukaDetail(DataBibit.semua[1])
        }

        // Pesan sekarang -> tab Katalog (pilih bibit dulu), lihat status -> Status Pesanan
        binding.btnPesanSekarang.setOnClickListener {
            pindahTab(R.id.katalog)
        }
        binding.btnLihatStatus.setOnClickListener {
            startActivity(Intent(requireContext(), StatusPesananActivity::class.java))
        }

        // Info Perusahaan (fitur pendukung)
        binding.cvInfoArtikel.setOnClickListener {
            startActivity(Intent(requireContext(), WebActivity::class.java))
        }
        binding.cvInfoProfil.setOnClickListener {
            startActivity(Intent(requireContext(), InfoPerusahaanActivity::class.java))
        }
        binding.cvInfoFaq.setOnClickListener {
            startActivity(Intent(requireContext(), FaqActivity::class.java))
        }
        binding.cvInfoWa.setOnClickListener {
            bukaWhatsApp()
        }
    }

    private fun pindahTab(menuId: Int) {
        (requireActivity() as BaseActivity).pindahTab(menuId)
    }

    private fun bukaDetail(bibit: Bibit) {
        val intent = Intent(requireContext(), DetailBibitActivity::class.java)
        intent.putExtra(DetailBibitActivity.EXTRA_NAMA, bibit.nama)
        startActivity(intent)
    }

    // Membuka chat WhatsApp Berkah Palma (lewat link wa.me)
    private fun bukaWhatsApp() {
        val pesan = Uri.encode("Halo Berkah Palma, saya ingin bertanya tentang bibit sawit.")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$NOMOR_WA?text=$pesan"))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "WhatsApp tidak dapat dibuka", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        // GANTI dengan nomor WhatsApp asli Berkah Palma (format 62..., tanpa + dan tanpa spasi)
        private const val NOMOR_WA = "6281234567890"
    }
}
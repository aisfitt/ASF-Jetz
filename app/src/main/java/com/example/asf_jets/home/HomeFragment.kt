package com.example.asf_jets.home

import android.content.Context
import android.content.Intent
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

        // Bibit pilihan -> tab Katalog
        binding.cvBibit1.setOnClickListener {
            pindahTab(R.id.katalog)
        }
        binding.cvBibit2.setOnClickListener {
            pindahTab(R.id.katalog)
        }

        // Pesan dan status -> tab Pesanan
        binding.btnPesanSekarang.setOnClickListener {
            pindahTab(R.id.pesanan)
        }
        binding.btnLihatStatus.setOnClickListener {
            pindahTab(R.id.pesanan)
        }

        // Info Perusahaan (fitur pendukung)
        binding.cvInfoArtikel.setOnClickListener {
            startActivity(Intent(requireContext(), WebActivity::class.java))
        }
        binding.cvInfoProfil.setOnClickListener {
            tampilkanToast("Profil Perusahaan")
        }
        binding.cvInfoFaq.setOnClickListener {
            tampilkanToast("FAQ")
        }
        binding.cvInfoWa.setOnClickListener {
            tampilkanToast("WhatsApp")
        }
    }

    private fun pindahTab(menuId: Int) {
        (requireActivity() as BaseActivity).pindahTab(menuId)
    }

    private fun tampilkanToast(menu: String) {
        Toast.makeText(requireContext(), "$menu diklik", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
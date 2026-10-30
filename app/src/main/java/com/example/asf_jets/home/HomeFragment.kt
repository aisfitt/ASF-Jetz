package com.example.asf_jets.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
        // Sapaan memakai username yang disimpan saat login
        val sharedPref = requireContext().getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna")
        binding.tvGreeting.text = "Hi, $username"

        // Pintasan ke tab Katalog
        binding.cvSearch.setOnClickListener { pindahTab(R.id.katalog) }
        binding.cvLayananKatalog.setOnClickListener { pindahTab(R.id.katalog) }
        binding.cvBibit1.setOnClickListener { pindahTab(R.id.katalog) }
        binding.cvBibit2.setOnClickListener { pindahTab(R.id.katalog) }

        // Pintasan ke tab Pesanan
        binding.btnPesanSekarang.setOnClickListener { pindahTab(R.id.pesanan) }
        binding.cvLayananPesan.setOnClickListener { pindahTab(R.id.pesanan) }
        binding.btnLihatStatus.setOnClickListener { pindahTab(R.id.pesanan) }

        // Pintasan ke tab Ulasan
        binding.cvLayananUlasan.setOnClickListener { pindahTab(R.id.ulasan) }

        // Info Perusahaan (fitur pendukung)
        binding.cvInfoArtikel.setOnClickListener {
            startActivity(Intent(requireContext(), WebActivity::class.java))
        }
        binding.cvInfoProfil.setOnClickListener { tampilkanToast("Profil Perusahaan") }
        binding.cvInfoFaq.setOnClickListener { tampilkanToast("FAQ") }
        binding.cvInfoWa.setOnClickListener { tampilkanToast("WhatsApp") }
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
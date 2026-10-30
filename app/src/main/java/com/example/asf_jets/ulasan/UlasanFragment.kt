package com.example.asf_jets.ulasan

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asf_jets.databinding.FragmentUlasanBinding

class UlasanFragment : Fragment() {

    private var _binding: FragmentUlasanBinding? = null
    private val binding get() = _binding!!

    private lateinit var ulasanAdapter: UlasanAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUlasanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Ulasan"
        }

        // Daftar ulasan: data awal (CONTOH), adapter, dan RecyclerView
        val dataAwal = listOf(
            Ulasan("Pengguna A", 5f, "Contoh ulasan: pelayanan ramah dan pengiriman cepat."),
            Ulasan("Pengguna B", 4f, "Contoh ulasan: bibit sesuai pesanan."),
            Ulasan("Pengguna C", 3f, "Contoh ulasan: proses pemesanan cukup mudah.")
        )
        ulasanAdapter = UlasanAdapter(dataAwal)
        binding.rvUlasan.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUlasan.adapter = ulasanAdapter

        // rating bar (urutan sesuai video):
        // ViewBinding menggantikan findViewById untuk ratingBar dan tombol
        binding.btnKirimUlasan.setOnClickListener {
            // baca jumlah bintang dan rating yang dipilih pengguna
            val totStar = binding.ratingBar.numStars
            val getRating = binding.ratingBar.rating
            val komentar = binding.etUlasan.text.toString().trim()

            // Validasi
            if (getRating == 0f) {
                Toast.makeText(requireContext(), "Pilih rating terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (komentar.isEmpty()) {
                binding.etUlasan.error = "Ulasan tidak boleh kosong!"
                return@setOnClickListener
            }

            // Nama diambil dari username yang disimpan saat login
            val sharedPref = requireContext().getSharedPreferences("UserPref", Context.MODE_PRIVATE)
            val username = sharedPref.getString("username", "Pengguna") ?: "Pengguna"

            // Tambah ke daftar
            ulasanAdapter.tambahUlasan(Ulasan(username, getRating, komentar))
            binding.rvUlasan.scrollToPosition(0)

            Log.d("UlasanFragment", "Rating: $getRating/$totStar, Ulasan: $komentar")
            Toast.makeText(requireContext(), "Rating: $getRating/$totStar", Toast.LENGTH_LONG).show()

            // Kosongkan form
            binding.etUlasan.setText("")
            binding.ratingBar.rating = 0f
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
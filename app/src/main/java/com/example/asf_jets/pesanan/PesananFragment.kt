package com.example.asf_jets.pesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.asf_jets.BaseActivity
import com.example.asf_jets.R
import com.example.asf_jets.databinding.FragmentPesananBinding
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.asf_jets.pesanan.tutorial.TutorialPesananActivity

class PesananFragment : Fragment() {

    private var _binding: FragmentPesananBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Pesanan"
        }
        setHasOptionsMenu(true)

        // Data alur pemesanan (sesuai user flow di grand design)
        val daftarAlur = listOf(
            AlurModel(1, "Katalog Bibit", "Melihat varietas, harga, umur, dan stok.", R.id.katalog),
            AlurModel(2, "Detail Bibit", "Melihat informasi lengkap bibit sebelum memesan.", null),
            AlurModel(3, "Pemesanan Bibit", "Memilih bibit, jumlah, dan mengisi data pemesanan.", null),
            AlurModel(4, "Pesanan Dibuat", "Pesanan berhasil dibuat.", null),
            AlurModel(5, "Status Pesanan", "Melihat perkembangan pesanan.", null),
            AlurModel(6, "Ulasan Customer", "Memberikan rating dan ulasan setelah pembelian.", R.id.ulasan)
        )

        // Pasang CustomAdapter ke ListView
        val alurAdapter = AlurAdapter(requireContext(), daftarAlur)
        binding.lvAlur.adapter = alurAdapter

        // Klik item: pindah tab jika sudah tersedia, selain itu tampilkan info
        binding.lvAlur.setOnItemClickListener { _, _, position, _ ->
            val item = daftarAlur[position]
            if (item.tujuan != null) {
                (requireActivity() as BaseActivity).pindahTab(item.tujuan)
            } else {
                Toast.makeText(
                    requireContext(),
                    "${item.judul} belum tersedia",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.pesanan_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_tutorial -> {
                val intent = Intent(requireContext(), TutorialPesananActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
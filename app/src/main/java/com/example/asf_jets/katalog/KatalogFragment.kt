package com.example.asf_jets.katalog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asf_jets.R
import com.example.asf_jets.databinding.FragmentKatalogBinding

class KatalogFragment : Fragment() {

    private var _binding: FragmentKatalogBinding? = null
    private val binding get() = _binding!!

    private lateinit var bibitAdapter: BibitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fragment ini ikut mengisi menu di Toolbar
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Katalog Bibit"
        }

        // 1. Data bibit (CONTOH, ganti dengan data asli dari web Berkah Palma)
        val dataBibit = listOf(
            Bibit("Bibit Sawit PPKS 6 Bulan", "PPKS", "6 Bulan", "Rp 25.000", 150),
            Bibit("Bibit Sawit PPKS 9 Bulan", "PPKS", "9 Bulan", "Rp 35.000", 80),
            Bibit("Bibit Sawit Yangambi 3 Bulan", "Yangambi", "3 Bulan", "Rp 18.000", 200),
            Bibit("Bibit Sawit Yangambi 6 Bulan", "Yangambi", "6 Bulan", "Rp 26.000", 120),
            Bibit("Bibit Sawit Socfindo 6 Bulan", "Socfindo", "6 Bulan", "Rp 28.000", 90),
            Bibit("Bibit Sawit Socfindo 12 Bulan", "Socfindo", "12 Bulan", "Rp 45.000", 40)
        )

        // 2. Pasang LayoutManager dan Adapter ke RecyclerView
        bibitAdapter = BibitAdapter(dataBibit) { bibit ->
            Log.d("KatalogFragment", "Tombol Pesan ditekan: ${bibit.nama}")
            Toast.makeText(requireContext(), "${bibit.nama} dipilih", Toast.LENGTH_SHORT).show()
        }
        binding.rvBibit.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBibit.adapter = bibitAdapter

        // 3. Tampilkan "tidak ditemukan" bila hasil filter kosong
        bibitAdapter.onHasilFilter = { jumlah ->
            _binding?.tvKosong?.visibility = if (jumlah == 0) View.VISIBLE else View.GONE
        }
    }

    // Membuat menu pencarian di Toolbar (sama seperti onCreateOptionsMenu di Activity)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_katalog, menu)

        val searchView = menu.findItem(R.id.action_cari_bibit).actionView as SearchView
        searchView.queryHint = "Cari bibit (ex: PPKS, 6 Bulan)"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                bibitAdapter.cari(newText.orEmpty())
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
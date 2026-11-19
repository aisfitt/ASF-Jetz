package com.example.asf_jets.katalog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
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

        // 1. Pasang LayoutManager dan Adapter ke RecyclerView (data dari DataBibit)
        bibitAdapter = BibitAdapter(DataBibit.semua) { bibit ->
            Log.d("KatalogFragment", "Tombol Pesan ditekan: ${bibit.nama}")
            val intent = Intent(requireContext(), DetailBibitActivity::class.java)
            intent.putExtra(DetailBibitActivity.EXTRA_NAMA, bibit.nama)
            startActivity(intent)
        }
        binding.rvBibit.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBibit.adapter = bibitAdapter

        // 2. Tampilkan "tidak ditemukan" bila hasil filter kosong
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
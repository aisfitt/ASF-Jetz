package com.example.asf_jets.katalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.asf_jets.databinding.FragmentKatalogBinding

class KatalogFragment : Fragment() {

    private var _binding: FragmentKatalogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Event listener saat Card diklik
        binding.cvKatalog1.setOnClickListener {
            Toast.makeText(requireContext(), "Layanan A diklik", Toast.LENGTH_SHORT).show()
        }

        binding.cvKatalog2.setOnClickListener {
            Toast.makeText(requireContext(), "Layanan B diklik", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
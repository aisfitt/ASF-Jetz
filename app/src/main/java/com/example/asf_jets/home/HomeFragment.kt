package com.example.asf_jets.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.asf_jets.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menerapkan data dinamis (Opsional)
        binding.tvUserName.text = "John Doe"

        // Event Listener untuk Search Card
        binding.cvSearch.setOnClickListener {
            Toast.makeText(requireContext(), "Membuka Pencarian...", Toast.LENGTH_SHORT).show()
        }

        // Event Listener untuk Promo Banner
        binding.cvBanner.setOnClickListener {
            Toast.makeText(requireContext(), "Promo Diklik!", Toast.LENGTH_SHORT).show()
        }

        // Event Listener untuk Item Rekomendasi 1 & 2
        binding.cvItem1.setOnClickListener {
            Toast.makeText(requireContext(), "Rekomendasi 1 Diklik", Toast.LENGTH_SHORT).show()
        }

        binding.cvItem2.setOnClickListener {
            Toast.makeText(requireContext(), "Rekomendasi 2 Diklik", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
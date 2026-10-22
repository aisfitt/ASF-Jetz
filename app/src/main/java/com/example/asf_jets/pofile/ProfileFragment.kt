package com.example.asf_jets.profil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.asf_jets.auth.LoginActivity
import com.example.asf_jets.databinding.FragmentProfileBinding
import com.example.asf_jets.home.WebActivity
import com.example.asf_jets.pofile.MainActivityresult
import com.example.asf_jets.pofile.ProfileActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Profil"
        }

        // 1. Tombol My Project
        binding.btnProjek.setOnClickListener {
            Log.d("ProfilFragment", "Tombol My Project berhasil ditekan")
            val intent = Intent(requireContext(), MainActivityresult::class.java)
            startActivity(intent)
        }

        // 2. Tombol Kunjungi Web
        binding.btnWeb.setOnClickListener {
            Log.d("ProfilFragment", "Tombol Kunjungi Web berhasil ditekan")
            val intent = Intent(requireContext(), WebActivity::class.java)
            startActivity(intent)
        }

        // 3. Tombol Profil Developer
        binding.btnProfile.setOnClickListener {
            Log.d("ProfilFragment", "Tombol Profil Developer berhasil ditekan")
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        // 4. Tombol Logout dengan AlertDialog
        val sharedPref = requireContext().getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Apakah kamu yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    // Hapus sesi di SharedPreferences
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()

                    // Pindah ke LoginActivity
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish() // destroy BaseActivity
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
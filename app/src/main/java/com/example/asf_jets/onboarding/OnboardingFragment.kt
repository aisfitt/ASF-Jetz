package com.example.asf_jets.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.asf_jets.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Ambil data yang dikirim lewat arguments
        binding.tvJudul.text = arguments?.getString(ARG_JUDUL)
        binding.tvDeskripsi.text = arguments?.getString(ARG_DESKRIPSI)

        // Tombol Mulai hanya tampil di halaman terakhir
        val isTerakhir = arguments?.getBoolean(ARG_TERAKHIR) ?: false
        if (isTerakhir) {
            binding.btnMulai.visibility = View.VISIBLE
            binding.btnMulai.setOnClickListener {
                (requireActivity() as OnboardingActivity).selesaiOnboarding()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_JUDUL = "judul"
        private const val ARG_DESKRIPSI = "deskripsi"
        private const val ARG_TERAKHIR = "terakhir"

        // Satu fragment dipakai untuk semua halaman, isinya dikirim lewat arguments
        fun newInstance(
            judul: String,
            deskripsi: String,
            terakhir: Boolean = false
        ): OnboardingFragment {
            val fragment = OnboardingFragment()
            val args = Bundle()
            args.putString(ARG_JUDUL, judul)
            args.putString(ARG_DESKRIPSI, deskripsi)
            args.putBoolean(ARG_TERAKHIR, terakhir)
            fragment.arguments = args
            return fragment
        }
    }
}
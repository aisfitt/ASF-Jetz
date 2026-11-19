package com.example.asf_jets.pesanan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.asf_jets.databinding.ItemAlurBinding

class AlurAdapter(
    private val context: Context,
    private val daftarAlur: List<AlurModel>
) : BaseAdapter() {

    // Jumlah item
    override fun getCount(): Int = daftarAlur.size

    // Data pada posisi tertentu
    override fun getItem(position: Int): AlurModel = daftarAlur[position]

    // Id item
    override fun getItemId(position: Int): Long = position.toLong()

    // Dipanggil setiap item perlu ditampilkan (termasuk saat scroll)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // convertView dipakai ulang agar tidak membuat view baru setiap scroll
        val binding = if (convertView == null) {
            ItemAlurBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemAlurBinding.bind(convertView)
        }

        val item = getItem(position)
        binding.tvNomor.text = item.nomor.toString()
        binding.tvJudul.text = item.judul
        binding.tvDeskripsi.text = item.deskripsi

        return binding.root
    }
}
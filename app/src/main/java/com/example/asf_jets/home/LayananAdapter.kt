package com.example.asf_jets.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.asf_jets.databinding.ItemLayananBinding

class LayananAdapter(
    private val daftarLayanan: List<LayananModel>,
    private val onItemClick: (LayananModel) -> Unit
) : RecyclerView.Adapter<LayananAdapter.LayananViewHolder>() {

    inner class LayananViewHolder(val binding: ItemLayananBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayananViewHolder {
        val binding = ItemLayananBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LayananViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LayananViewHolder, position: Int) {
        val item = daftarLayanan[position]
        with(holder.binding) {
            tvJudul.text = item.judul
            tvDeskripsi.text = item.deskripsi

            Glide.with(holder.itemView.context)
                .load(item.ikon)
                .into(imgLayanan)

            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = daftarLayanan.size
}
package com.example.asf_jets.ulasan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asf_jets.databinding.ItemUlasanBinding

class UlasanAdapter(dataAwal: List<Ulasan>) :
    RecyclerView.Adapter<UlasanAdapter.UlasanViewHolder>() {

    private val daftarUlasan = ArrayList(dataAwal)

    // ViewHolder: memegang view satu item (lewat ViewBinding)
    class UlasanViewHolder(val binding: ItemUlasanBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Membuat ViewHolder dari item_ulasan.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UlasanViewHolder {
        val binding = ItemUlasanBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UlasanViewHolder(binding)
    }

    // Mengisi data ke view sesuai posisi
    override fun onBindViewHolder(holder: UlasanViewHolder, position: Int) {
        val ulasan = daftarUlasan[position]
        holder.binding.tvNama.text = ulasan.nama
        holder.binding.ratingItem.rating = ulasan.rating
        holder.binding.tvKomentar.text = ulasan.komentar
    }

    // Jumlah item
    override fun getItemCount(): Int = daftarUlasan.size

    // Menambah ulasan baru di urutan paling atas
    fun tambahUlasan(ulasan: Ulasan) {
        daftarUlasan.add(0, ulasan)
        notifyItemInserted(0)
    }

    // Rata-rata rating semua ulasan (0 jika belum ada)
    fun hitungRataRata(): Float {
        if (daftarUlasan.isEmpty()) return 0f
        return daftarUlasan.map { it.rating }.average().toFloat()
    }
}
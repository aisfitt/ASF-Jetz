package com.example.asf_jets.katalog

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.asf_jets.databinding.ItemBibitBinding

class BibitAdapter(
    dataAwal: List<Bibit>,
    private val onPesanClick: (Bibit) -> Unit
) : RecyclerView.Adapter<BibitAdapter.BibitViewHolder>(), Filterable {

    // Daftar lengkap (sumber data) dan daftar yang sedang ditampilkan (hasil filter)
    private val semuaBibit = ArrayList(dataAwal)
    private var tampilBibit = ArrayList(dataAwal)

    // Dipanggil setelah filter selesai, mengirim jumlah hasil (untuk teks "tidak ditemukan")
    var onHasilFilter: ((Int) -> Unit)? = null

    // ViewHolder: memegang view satu item (lewat ViewBinding)
    class BibitViewHolder(val binding: ItemBibitBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Membuat ViewHolder dari item_bibit.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BibitViewHolder {
        val binding = ItemBibitBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BibitViewHolder(binding)
    }

    // Mengisi data ke view sesuai posisi
    override fun onBindViewHolder(holder: BibitViewHolder, position: Int) {
        val bibit = tampilBibit[position]
        holder.binding.tvNamaBibit.text = bibit.nama
        holder.binding.tvVarietas.text = "${bibit.varietas} · ${bibit.umur}"
        holder.binding.tvHarga.text = bibit.harga
        holder.binding.tvStok.text = "Stok: ${bibit.stok} bibit"
        holder.binding.btnPesan.setOnClickListener {
            onPesanClick(bibit)
        }
    }

    // Jumlah item yang ditampilkan
    override fun getItemCount(): Int = tampilBibit.size

    // Titik masuk pencarian dari SearchView
    fun cari(kata: String) {
        filter.filter(kata)
    }

    // Wajib karena implement Filterable
    override fun getFilter(): Filter = object : Filter() {

        // Berjalan di background thread: menyaring semuaBibit
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val kata = constraint?.toString().orEmpty().trim().lowercase()
            val hasil = ArrayList<Bibit>()

            if (kata.isEmpty()) {
                hasil.addAll(semuaBibit)
            } else {
                for (bibit in semuaBibit) {
                    if (bibit.nama.lowercase().contains(kata) ||
                        bibit.varietas.lowercase().contains(kata) ||
                        bibit.umur.lowercase().contains(kata)
                    ) {
                        hasil.add(bibit)
                    }
                }
            }
            return FilterResults().apply {
                values = hasil
                count = hasil.size
            }
        }

        // Berjalan di main thread: tampilkan hasil ke RecyclerView
        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            tampilBibit = results?.values as? ArrayList<Bibit> ?: ArrayList()
            notifyDataSetChanged()
            onHasilFilter?.invoke(tampilBibit.size)
        }
    }
}
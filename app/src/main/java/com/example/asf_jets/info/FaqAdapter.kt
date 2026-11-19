package com.example.asf_jets.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asf_jets.databinding.ItemFaqBinding

data class Faq(
    val tanya: String,
    val jawab: String,
    var terbuka: Boolean = false
)

class FaqAdapter(
    private val daftar: List<Faq>
) : RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {

    class FaqViewHolder(val binding: ItemFaqBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val binding = ItemFaqBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FaqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val faq = daftar[position]
        with(holder.binding) {
            tvTanya.text = faq.tanya
            tvJawab.text = faq.jawab
            tvJawab.visibility = if (faq.terbuka) View.VISIBLE else View.GONE
            tvPanah.text = if (faq.terbuka) "▴" else "▾"

            // Klik kartu: buka / tutup jawaban
            root.setOnClickListener {
                val pos = holder.bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    faq.terbuka = !faq.terbuka
                    notifyItemChanged(pos)
                }
            }
        }
    }

    override fun getItemCount(): Int = daftar.size
}
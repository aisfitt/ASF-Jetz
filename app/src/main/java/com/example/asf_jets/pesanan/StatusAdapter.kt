package com.example.asf_jets.pesanan

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.asf_jets.R
import com.example.asf_jets.databinding.ItemStatusPesananBinding
import com.example.asf_jets.katalog.DataBibit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StatusAdapter(
    private val daftar: MutableList<Pesanan>,
    private val onAksi: (Pesanan) -> Unit
) : RecyclerView.Adapter<StatusAdapter.StatusViewHolder>() {

    class StatusViewHolder(val binding: ItemStatusPesananBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val binding = ItemStatusPesananBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        val p = daftar[position]
        val ctx = holder.itemView.context
        val format = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.forLanguageTag("id-ID"))

        with(holder.binding) {
            tvIdPesanan.text = p.id
            tvNamaBibit.text = p.namaBibit
            tvDetail.text = "${p.jumlah} bibit · ${DataBibit.formatRupiah(p.total)}"
            tvWaktu.text = format.format(Date(p.waktu))

            // Chip status: warna sesuai tahap
            tvStatus.text = Pesanan.labelStatus(p.status)
            val warnaChip = when (p.status) {
                Pesanan.DIPROSES -> R.color.warning
                Pesanan.DIKIRIM -> R.color.info
                else -> R.color.success
            }
            (tvStatus.background.mutate() as GradientDrawable)
                .setColor(ContextCompat.getColor(ctx, warnaChip))
            tvStatus.setTextColor(
                ContextCompat.getColor(
                    ctx,
                    if (p.status == Pesanan.DIPROSES) R.color.primary else R.color.white
                )
            )

            // Progress 3 tahap
            val aktif = ContextCompat.getColor(ctx, R.color.primary_shade_2)
            val mati = ContextCompat.getColor(ctx, R.color.disabled)
            vProg1.setBackgroundColor(aktif)
            vProg2.setBackgroundColor(if (p.status >= Pesanan.DIKIRIM) aktif else mati)
            vProg3.setBackgroundColor(if (p.status >= Pesanan.SELESAI) aktif else mati)

            tvKeterangan.text = when (p.status) {
                Pesanan.DIPROSES -> "Pesanan sedang diproses Berkah Palma"
                Pesanan.DIKIRIM -> "Bibit sedang dalam pengiriman"
                else -> "Pesanan selesai. Terima kasih!"
            }

            btnAksi.text = when (p.status) {
                Pesanan.DIPROSES -> "Simulasi: Kirim Pesanan"
                Pesanan.DIKIRIM -> "Simulasi: Tandai Selesai"
                else -> "Beri Ulasan"
            }
            btnAksi.setOnClickListener { onAksi(p) }
        }
    }

    override fun getItemCount(): Int = daftar.size

    // Ganti satu item setelah status berubah
    fun perbarui(baru: Pesanan) {
        val index = daftar.indexOfFirst { it.id == baru.id }
        if (index >= 0) {
            daftar[index] = baru
            notifyItemChanged(index)
        }
    }
}
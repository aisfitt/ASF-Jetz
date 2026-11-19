package com.example.asf_jets.pesanan

data class Pesanan(
    val id: String,
    val namaBibit: String,
    val jumlah: Int,
    val total: Long,
    val penerima: String,
    val nomorWa: String,
    val alamat: String,
    val catatan: String,
    val status: Int,
    val waktu: Long
) {
    companion object {
        const val DIPROSES = 0
        const val DIKIRIM = 1
        const val SELESAI = 2

        fun labelStatus(status: Int): String = when (status) {
            DIPROSES -> "Diproses"
            DIKIRIM -> "Dikirim"
            else -> "Selesai"
        }
    }
}
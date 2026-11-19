package com.example.asf_jets.katalog

import java.util.Locale

object DataBibit {

    val semua = listOf(
        Bibit("Bibit Sawit PPKS 6 Bulan", "PPKS", "6 Bulan", "Rp 25.000", 150),
        Bibit("Bibit Sawit PPKS 9 Bulan", "PPKS", "9 Bulan", "Rp 35.000", 80),
        Bibit("Bibit Sawit Yangambi 3 Bulan", "Yangambi", "3 Bulan", "Rp 18.000", 200),
        Bibit("Bibit Sawit Yangambi 6 Bulan", "Yangambi", "6 Bulan", "Rp 26.000", 120),
        Bibit("Bibit Sawit Socfindo 6 Bulan", "Socfindo", "6 Bulan", "Rp 28.000", 90),
        Bibit("Bibit Sawit Socfindo 12 Bulan", "Socfindo", "12 Bulan", "Rp 45.000", 40)
    )

    fun cariByNama(nama: String): Bibit? = semua.firstOrNull { it.nama == nama }

    // "Rp 25.000" -> 25000
    fun hargaAngka(bibit: Bibit): Long = bibit.harga.filter { it.isDigit() }.toLong()

    // 25000 -> "Rp 25.000"
    fun formatRupiah(angka: Long): String =
        "Rp " + String.format(Locale.forLanguageTag("id-ID"), "%,d", angka)
}
package com.example.asf_jets.pesanan

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Penyimpanan pesanan lokal (SharedPreferences + JSON)
object PesananStore {

    private const val PREF = "OrderPref"
    private const val KEY = "daftarPesanan"

    fun semua(context: Context): MutableList<Pesanan> {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val arr = JSONArray(pref.getString(KEY, "[]") ?: "[]")
        val hasil = mutableListOf<Pesanan>()
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            hasil.add(
                Pesanan(
                    id = o.getString("id"),
                    namaBibit = o.getString("namaBibit"),
                    jumlah = o.getInt("jumlah"),
                    total = o.getLong("total"),
                    penerima = o.getString("penerima"),
                    nomorWa = o.getString("nomorWa"),
                    alamat = o.getString("alamat"),
                    catatan = o.getString("catatan"),
                    status = o.getInt("status"),
                    waktu = o.getLong("waktu")
                )
            )
        }
        return hasil
    }

    fun cari(context: Context, id: String): Pesanan? =
        semua(context).firstOrNull { it.id == id }

    // Pesanan terbaru ditaruh paling atas
    fun simpan(context: Context, pesanan: Pesanan) {
        val list = semua(context)
        list.add(0, pesanan)
        tulis(context, list)
    }

    fun ubahStatus(context: Context, id: String, statusBaru: Int) {
        val list = semua(context).map {
            if (it.id == id) it.copy(status = statusBaru) else it
        }
        tulis(context, list)
    }

    fun buatId(): String =
        "PSN-" + SimpleDateFormat("yyMMddHHmmss", Locale.US).format(Date())

    private fun tulis(context: Context, list: List<Pesanan>) {
        val arr = JSONArray()
        for (p in list) {
            arr.put(JSONObject().apply {
                put("id", p.id)
                put("namaBibit", p.namaBibit)
                put("jumlah", p.jumlah)
                put("total", p.total)
                put("penerima", p.penerima)
                put("nomorWa", p.nomorWa)
                put("alamat", p.alamat)
                put("catatan", p.catatan)
                put("status", p.status)
                put("waktu", p.waktu)
            })
        }
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .edit().putString(KEY, arr.toString()).apply()
    }
}
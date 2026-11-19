package com.example.asf_jets.info

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class InfoTabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Jumlah total tab yang ada
    override fun getItemCount(): Int = 2

    // Menentukan Fragment mana yang ditampilkan untuk setiap tab
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TentangFragment()
            1 -> VisiMisiFragment()
            else -> throw IllegalStateException("Posisi tidak valid")
        }
    }
}
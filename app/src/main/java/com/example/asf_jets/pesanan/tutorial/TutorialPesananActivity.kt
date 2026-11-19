package com.example.asf_jets.pesanan.tutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.asf_jets.databinding.ActivityTutorialPesananBinding

class TutorialPesananActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialPesananBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTutorialPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup ViewPager dengan adapter
        val fragmentsList = listOf(Tutorial1Fragment(), Tutorial2Fragment(), Tutorial3Fragment())
        val adapter = TutorialFragmentAdapter(this, fragmentsList)
        binding.tutorialPesananViewPager.adapter = adapter

        // Hubungkan DotsIndicator dengan ViewPager
        binding.dotIndicator.attachTo(binding.tutorialPesananViewPager)
    }
}
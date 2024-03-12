package com.example.yourtime.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yourtime.databinding.ActivityMainBinding
import com.example.yourtime.presentation.boodeelneek.BoodeelneekFragment
import com.example.yourtime.presentation.secoondomer.SecoondomerFragment
import com.example.yourtime.presentation.timer.TimerFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(SecoondomerFragment(), "Secoondomer")
        adapter.addFragment(TimerFragment(), "Timer")
        adapter.addFragment(BoodeelneekFragment(), "Boodeelneek")

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}
package com.example.yourtime.presentation.secoondomer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yourtime.databinding.FragmentSecoondomerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SecoondomerFragment : Fragment() {

    private val binding: FragmentSecoondomerBinding by lazy {
        FragmentSecoondomerBinding.inflate(layoutInflater)
    }
    private lateinit var job: Job
    private var worked = false
    private var seconds = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        job = Job()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBtn()
    }

    private fun initBtn() {
        with(binding) {
            btnToggle.setOnClickListener {
                if (worked) {
                    stopSecoondomer()
                } else {
                    startSecoondomer()
                }
            }

            btnReset.setOnClickListener {
                resetSecoondomer()
            }
        }
    }

    private fun startSecoondomer() {
        binding.btnToggle.text = "Stop"
        job = CoroutineScope(Dispatchers.Main).launch {
            worked = true
            var startTime = System.currentTimeMillis()
            while (worked) {
                delay(1000)
                val currentTime = System.currentTimeMillis()
                seconds += TimeUnit.MILLISECONDS.toSeconds(currentTime - startTime)
                startTime = currentTime
                secoondomerSetting(seconds)
            }
        }
    }

    private fun stopSecoondomer() {
        binding.btnToggle.text = "Start"
        worked = false
        job.cancel()
    }

    private fun resetSecoondomer() {
        stopSecoondomer()
        seconds = 0
        secoondomerSetting(seconds)
    }

    private fun secoondomerSetting(timeInSeconds: Long) {
        val hour = TimeUnit.SECONDS.toHours(timeInSeconds)
        val minute = TimeUnit.SECONDS.toMinutes(timeInSeconds) % 60
        val second = timeInSeconds % 60

        binding.tvTime.text = String.format("%02d:%02d:%02d", hour, minute, second)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }
}
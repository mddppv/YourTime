package com.example.yourtime.presentation.timer

import android.app.TimePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.yourtime.databinding.FragmentTimerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TimerFragment : Fragment() {

    private val binding: FragmentTimerBinding by lazy {
        FragmentTimerBinding.inflate(layoutInflater)
    }
    private lateinit var countDownTimer: CountDownTimer
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
            btnSetTime.setOnClickListener {
                timePickerDialog()
            }

            btnToggle.setOnClickListener {
                if (worked) {
                    stopTimer()
                } else {
                    if (seconds > 0) {
                        startTimer(seconds)
                    } else {
                        Toast.makeText(context, "Set time first", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            btnReset.setOnClickListener {
                resetTimer()
            }
        }
    }

    private fun startTimer(seconds: Long) {
        binding.btnToggle.text = "Stop"
        job = CoroutineScope(Dispatchers.Main).launch {
            worked = true
            countDownTimer = object : CountDownTimer(seconds * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    this@TimerFragment.seconds = millisUntilFinished / 1000
                    timerSetting(this@TimerFragment.seconds)
                }

                override fun onFinish() {
                    worked = false
                    binding.btnToggle.text = "Start"
                    Toast.makeText(context, "Timer finished!", Toast.LENGTH_SHORT).show()
                }
            }.start()
        }
    }

    private fun stopTimer() {
        binding.btnToggle.text = "Start"
        worked = false
        countDownTimer.cancel()
        job.cancel()
    }

    private fun resetTimer() {
        stopTimer()
        seconds = 0
        timerSetting(seconds)
    }

    private fun timerSetting(timeInSeconds: Long) {
        val hour = TimeUnit.SECONDS.toHours(timeInSeconds)
        val minute = TimeUnit.SECONDS.toMinutes(timeInSeconds) % 60
        val second = timeInSeconds % 60

        binding.tvTime.text = String.format("%02d:%02d:%02d", hour, minute, second)
    }

    private fun timePickerDialog() {
        val timePicker = TimePickerDialog(
            requireContext(), { _: TimePicker, hourOfDay: Int, minute: Int ->
                val timeInSeconds =
                    TimeUnit.HOURS.toSeconds(hourOfDay.toLong()) + TimeUnit.MINUTES.toSeconds(
                        minute.toLong()
                    )
                seconds = timeInSeconds
                timerSetting(seconds)
            }, 0, 0, true
        )
        timePicker.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (worked) {
            countDownTimer.cancel()
        }
        job.cancel()
    }
}
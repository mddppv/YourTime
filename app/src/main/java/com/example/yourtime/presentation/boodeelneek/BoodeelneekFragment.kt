package com.example.yourtime.presentation.boodeelneek

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yourtime.databinding.FragmentBoodeelneekBinding
import java.util.Calendar

class BoodeelneekFragment : Fragment() {

    private val binding: FragmentBoodeelneekBinding by lazy {
        FragmentBoodeelneekBinding.inflate(layoutInflater)
    }
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private var timeSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAlarmManager()
        initBtn()
    }

    private fun initAlarmManager() {
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), BoodeelneekReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun initBtn() {
        with(binding) {
            btnSetTime.setOnClickListener {
                timePickerDialog()
            }

            btnStop.setOnClickListener {
                stopBoodeelneek()
            }
        }
    }

    private fun stopBoodeelneek() {
        alarmManager.cancel(pendingIntent)
        BoodeelneekReceiver().stopMusic()
    }

    private fun boodeelneekSetting(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
        )

        binding.tvTime.text = String.format("%02d:%02d:00", hour, minute)
        timeSelected = true
    }

    private fun timePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(), { _, selectedHour, selectedMinute ->
                boodeelneekSetting(selectedHour, selectedMinute)
            }, hour, minute, true
        )
        timePickerDialog.show()
    }
}
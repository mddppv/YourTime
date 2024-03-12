package com.example.yourtime.presentation.boodeelneek

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.example.yourtime.R

class BoodeelneekReceiver : BroadcastReceiver() {

    private var mediaPlayer: MediaPlayer? = null
    private var repeatCount = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        mediaPlayer = MediaPlayer.create(context, R.raw.kawaii)
        mediaPlayer?.setOnCompletionListener {
            if (repeatCount < 4) {
                repeatCount++
                it.start()
            }
        }
        mediaPlayer?.start()
    }

    fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
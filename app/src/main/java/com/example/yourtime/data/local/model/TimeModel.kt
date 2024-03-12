package com.example.yourtime.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time")
data class TimeModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, val time: Long
)
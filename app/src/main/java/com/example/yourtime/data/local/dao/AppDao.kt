package com.example.yourtime.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.yourtime.data.local.model.TimeModel

@Dao
interface AppDao {

    @Insert
    fun setTime(timeModel: TimeModel)

    @Query("SELECT * FROM time")
    fun getTime(): List<TimeModel>

}
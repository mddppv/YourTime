package com.example.yourtime.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.yourtime.data.local.dao.AppDao
import com.example.yourtime.data.local.model.TimeModel

@Database(entities = [TimeModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}
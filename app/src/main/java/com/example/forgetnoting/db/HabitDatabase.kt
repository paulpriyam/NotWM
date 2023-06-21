package com.example.forgetnoting.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.forgetnoting.converters.HabitTypeConverters
import com.example.forgetnoting.model.Habit

@Database(entities = [Habit::class], version = 1)
@TypeConverters(HabitTypeConverters::class)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
}
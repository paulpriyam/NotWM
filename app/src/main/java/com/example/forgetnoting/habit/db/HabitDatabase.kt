package com.example.forgetnoting.habit.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.forgetnoting.habit.converters.HabitTypeConverters
import com.example.forgetnoting.habit.model.Habit

@Database(entities = [Habit::class], version = 1)
@TypeConverters(HabitTypeConverters::class)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
}
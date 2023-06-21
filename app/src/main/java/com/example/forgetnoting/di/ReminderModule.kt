package com.example.forgetnoting.di

import android.content.Context
import androidx.room.Room
import com.example.forgetnoting.db.HabitDao
import com.example.forgetnoting.db.HabitDatabase
import com.example.forgetnoting.db.ReminderDao
import com.example.forgetnoting.db.ReminderDatabase
import com.example.forgetnoting.repo.HabitRepository
import com.example.forgetnoting.repo.ReminderRepository
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object ReminderModule {


    @Provides
    @Singleton
    fun provideReminderDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ReminderDatabase::class.java, "Reminder-db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideHabitDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, HabitDatabase::class.java, "Habit_db")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideReminderDao(reminderDatabase: ReminderDatabase) =
        reminderDatabase.reminderDoa()

    @Provides
    @Singleton
    fun provideHabitDao(habitDatabase: HabitDatabase) = habitDatabase.habitDao()

    @Provides
    @Singleton
    fun provideReminderRepository(reminderDao: ReminderDao) = ReminderRepository(reminderDao)

    @Provides
    @Singleton
    fun provideHabitRepository(habitDao: HabitDao) = HabitRepository(habitDao)
}
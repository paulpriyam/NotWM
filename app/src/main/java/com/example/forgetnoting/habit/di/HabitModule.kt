package com.example.forgetnoting.habit.di

import android.content.Context
import androidx.room.Room
import com.example.forgetnoting.habit.db.HabitDao
import com.example.forgetnoting.habit.db.HabitDatabase
import com.example.forgetnoting.habit.repo.HabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HabitModule {

    @Provides
    @Singleton
    fun provideHabitDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, HabitDatabase::class.java, "Habit_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideHabitDao(habitDatabase: HabitDatabase) = habitDatabase.habitDao()

    @Provides
    @Singleton
    fun provideHabitRepository(habitDao: HabitDao) = HabitRepository(habitDao)
}
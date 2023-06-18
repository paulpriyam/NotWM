package com.example.forgetnoting.di

import android.content.Context
import androidx.room.Room
import com.example.forgetnoting.db.ReminderDao
import com.example.forgetnoting.db.ReminderDatabase
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
    fun provideReminderDao(reminderDatabase: ReminderDatabase) =
        reminderDatabase.reminderDoa()

    @Provides
    @Singleton
    fun provideReminderRepository(reminderDao: ReminderDao) = ReminderRepository(reminderDao)
}
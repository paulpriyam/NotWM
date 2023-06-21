package com.example.forgetnoting.reminder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.forgetnoting.reminder.model.Reminder

@Database(entities = [Reminder::class], exportSchema = false, version = 1)
abstract class ReminderDatabase : RoomDatabase() {

    abstract fun reminderDoa(): ReminderDao
}
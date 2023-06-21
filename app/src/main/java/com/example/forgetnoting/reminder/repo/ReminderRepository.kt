package com.example.forgetnoting.reminder.repo

import com.example.forgetnoting.reminder.db.ReminderDao
import com.example.forgetnoting.reminder.model.Reminder
import javax.inject.Inject

class ReminderRepository @Inject constructor(private val reminderDao: ReminderDao) {

    suspend fun insertOrUpdateReminder(reminder: Reminder): Long {
        return reminderDao.addOrUpdateReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
    }

    fun getAllReminders(): List<Reminder> {
        return reminderDao.getAllReminders()
    }

    fun getReminderById(reminderId: Int) = reminderDao.getReminderById(reminderId)

    fun getIdFromRowId(rowId: Long) = reminderDao.getIdFromRowId(rowId)

    fun getAllReminderPagingList() = reminderDao.getAllReminderPagedList()
}
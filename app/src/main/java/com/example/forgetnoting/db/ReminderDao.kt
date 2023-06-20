package com.example.forgetnoting.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.forgetnoting.model.Reminder
import kotlinx.coroutines.flow.Flow
import java.sql.RowId

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdateReminder(reminder: Reminder): Long

    @Query("SELECT * FROM reminder_table ORDER BY createdAt")
    fun getAllReminders(): List<Reminder>

    @Transaction
    @Query("SELECT * FROM reminder_table ORDER BY createdAt")
    fun getAllReminderPagedList(): PagingSource<Int, Reminder>

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("SELECT * FROM reminder_table WHERE id LIKE :reminderId")
    fun getReminderById(reminderId: Int): Reminder

    @Query("SELECT id FROM reminder_table WHERE rowId = :rowId")
    fun getIdFromRowId(rowId: Long): Long


}
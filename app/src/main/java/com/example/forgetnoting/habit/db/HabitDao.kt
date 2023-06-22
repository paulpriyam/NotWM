package com.example.forgetnoting.habit.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.forgetnoting.habit.model.Habit

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("DELETE FROM habit_table WHERE id = :id")
    suspend fun deleteHabitById(id: Int)

    @Query("DELETE FROM habit_table WHERE image IS NULL")
    suspend fun deleteNullImage()

    @Query("SELECT * FROM habit_table WHERE id = :id")
    fun getHabitById(id: Int): Habit

    @Query("SELECT * FROM habit_table ORDER BY createdAt DESC")
    fun getAllHabits(): List<Habit>

    @Transaction
    @Query("SELECT * FROM habit_table ORDER BY createdAt DESC")
    fun getAllHabitsPagedList(): PagingSource<Int, Habit>
}
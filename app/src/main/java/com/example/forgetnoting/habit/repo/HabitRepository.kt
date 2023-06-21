package com.example.forgetnoting.habit.repo

import com.example.forgetnoting.habit.db.HabitDao
import com.example.forgetnoting.habit.model.Habit
import javax.inject.Inject

class HabitRepository @Inject constructor(private val habitDao: HabitDao) {

    suspend fun insertOrUpdateHabit(habit: Habit) = habitDao.insertHabit(habit)

    suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(habit)

    fun getAllHabits() = habitDao.getAllHabits()

    fun getAllHabitPagedList() = habitDao.getAllHabitsPagedList()

    fun getHabitById(id: Int) = habitDao.getHabitById(id)
}
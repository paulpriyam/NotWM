package com.example.forgetnoting.repo

import com.example.forgetnoting.db.HabitDao
import javax.inject.Inject

class HabitRepository @Inject constructor(private val habitDao: HabitDao) {
}
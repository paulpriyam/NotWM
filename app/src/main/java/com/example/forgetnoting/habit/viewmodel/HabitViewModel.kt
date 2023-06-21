package com.example.forgetnoting.habit.viewmodel

import androidx.lifecycle.ViewModel
import com.example.forgetnoting.habit.repo.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(private val habitRepository: HabitRepository):ViewModel() {

}
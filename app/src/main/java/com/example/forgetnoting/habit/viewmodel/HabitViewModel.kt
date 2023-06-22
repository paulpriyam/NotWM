package com.example.forgetnoting.habit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forgetnoting.habit.model.Habit
import com.example.forgetnoting.habit.repo.HabitRepository
import com.example.forgetnoting.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(private val habitRepository: HabitRepository) :
    ViewModel() {

    private var _insertHabitLiveDate = MutableLiveData<ViewState>()
    val insertHabitLiveDate: LiveData<ViewState> get() = _insertHabitLiveDate

    private var _habitsLiveData = MutableLiveData<List<Habit>>()
    val habitsLiveData: LiveData<List<Habit>> get() = _habitsLiveData
    fun insertHabit(habit: Habit) {
        _insertHabitLiveDate.postValue(ViewState.Loading)
        try {
            viewModelScope.launch(Dispatchers.IO) {
                habitRepository.insertOrUpdateHabit(habit)
                withContext(Dispatchers.Main) {
                    _insertHabitLiveDate.postValue(ViewState.Success)
                }

            }
        } catch (e: Exception) {
            _insertHabitLiveDate.postValue(ViewState.Error(e.message.toString()))
        }
    }

    fun getHabitList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = habitRepository.getAllHabits()
            withContext(Dispatchers.Main) {
                _habitsLiveData.postValue(response)
            }
        }
    }

    fun deleteHabitById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.deleteHabitById(id)
        }
    }

    fun deleteNullImageEntry(){
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.deleteNullImageEntry()
        }
    }
}
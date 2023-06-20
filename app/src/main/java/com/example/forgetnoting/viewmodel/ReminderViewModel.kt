package com.example.forgetnoting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.forgetnoting.R
import com.example.forgetnoting.model.Reminder
import com.example.forgetnoting.repo.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ReminderViewModel @Inject constructor(private val reminderRepository: ReminderRepository) :
    ViewModel() {

    private var _reminders: MutableLiveData<List<Reminder>> = MutableLiveData<List<Reminder>>()

    val reminders: LiveData<List<Reminder>> get() = _reminders

    private var _rowId: MutableLiveData<Long> = MutableLiveData<Long>()
    val rowId: LiveData<Long> get() = _rowId

    fun getAllReminders() {
        viewModelScope.launch(Dispatchers.IO) {
            val reminderList = reminderRepository.getAllReminders()
            withContext(Dispatchers.Main) {
                _reminders.postValue(reminderList)
            }
        }
    }

    val reminderFlow = Pager(
        PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
            prefetchDistance = 5,
            enablePlaceholders = false,
            maxSize = 30
        )
    ) {
        reminderRepository.getAllReminderPagingList()
    }.flow.cachedIn(viewModelScope)

    fun addReminder(reminder: Reminder) =
        viewModelScope.launch(Dispatchers.IO) {
            val id = reminderRepository.insertOrUpdateReminder(reminder)
            withContext(Dispatchers.Main) {
                _rowId.postValue(id)
            }
        }


    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            reminderRepository.deleteReminder(reminder)
        }
    }

    fun getReminderById(reminderId: Int) =
        reminderRepository.getReminderById(reminderId)


    fun getIdFromRowId(rowId: Long) =
        reminderRepository.getIdFromRowId(rowId)


    fun fromChipIdToColor(chipId: Int): Int {
        return when (chipId) {
            R.id.chipPink -> R.color.pink
            R.id.chipPurple -> R.color.purple_500
            R.id.chipPurpleDark -> R.color.purple_700
            R.id.chipPurpleLight -> R.color.purple_200
            R.id.chipYellow -> R.color.yellow
            R.id.chipRed -> R.color.red
            R.id.chipTeal -> R.color.teal_700
            R.id.chipTealLight -> R.color.teal_200
            else -> R.color.yellow
        }
    }

    fun fromColorToChipId(color: Int): Int {
        return when (color) {
            R.color.pink -> R.id.chipPink
            R.color.purple_500 -> R.id.chipPurple
            R.color.purple_700 -> R.id.chipPurpleDark
            R.color.purple_200 -> R.id.chipPurpleLight
            R.color.yellow -> R.id.chipYellow
            R.color.red -> R.id.chipRed
            R.color.teal_700 -> R.id.chipTeal
            R.color.teal_200 -> R.id.chipTealLight
            else -> R.id.chipYellow
        }
    }

    fun fromChipColorToGroup(color: Int): String {
        return when (color) {
            R.color.pink -> ReminderGroup.GUITAR.name
            R.color.purple_500 -> ReminderGroup.ANDROID.name
            R.color.purple_700 -> ReminderGroup.DSA.name
            R.color.purple_200 -> ReminderGroup.READING.name
            R.color.yellow -> ReminderGroup.DEFAULT.name
            R.color.red -> ReminderGroup.DRIVING.name
            R.color.teal_700 -> ReminderGroup.SWIMMING.name
            R.color.teal_200 -> ReminderGroup.WATER.name
            else -> ReminderGroup.DEFAULT.name
        }
    }

    fun fromIntervalToMillis(interval: String): Long {
        return when (interval) {
            "Every Hour" -> 60 * 60 * 1000L
            "Every Day" -> 24 * 60 * 60 * 1000L
            "Every Week" -> 7 * 24 * 60 * 60 * 1000L
            "Every Month" -> 30 * 24 * 60 * 60 * 1000L
            else -> 0L
        }
    }

}

enum class ReminderGroup {
    DEFAULT,
    ANDROID,
    DSA,
    WATER,
    GUITAR,
    DRIVING,
    SWIMMING,
    READING

}
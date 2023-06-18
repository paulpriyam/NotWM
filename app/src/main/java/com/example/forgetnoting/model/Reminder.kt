package com.example.forgetnoting.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.forgetnoting.R

@Entity(tableName = "reminder_table")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val remindAt: String = "",
    val createdAt: Long? = 0L,
    val title: String = "",
    val description: String = "",
    val isRepeating: Boolean = false,
    val repeatInterval: Long = 0L,
    val color: Int = R.color.yellow,
    val group: String = "DEFAULT"
)

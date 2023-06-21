package com.example.forgetnoting.habit.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
data class Habit(
    var title: String,
    var tag: String,
    var createdAt: Long,
    var daysCompleted: Int,
    var goal: Int,
    var image: Bitmap

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

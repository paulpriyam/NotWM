package com.example.forgetnoting

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.forgetnoting.databinding.ActivityMainBinding
import com.example.forgetnoting.util.ReminderWorker
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val today = Calendar.getInstance()
        var chosenYear = 0
        var chosenMonth = 0
        var chosenDay = 0
        var chosenHour = 0
        var chosenMinute = 0
        binding.datePicker.init(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            chosenYear = year
            chosenMonth = month
            chosenDay = day
        }

        binding.timePicker.setOnTimeChangedListener { timePicker, hour, minute ->
            chosenHour = hour
            chosenMinute = minute
        }
        binding.btSet.setOnClickListener {
            val userSelectedDateTime = Calendar.getInstance()
            userSelectedDateTime.set(chosenYear, chosenMonth, chosenDay, chosenHour, chosenMinute)
            val todayDateTime = Calendar.getInstance()
            val differenceInTime =
                (userSelectedDateTime.timeInMillis / 1000) - (todayDateTime.timeInMillis / 1000)
            Log.d("Reminder", differenceInTime.toString())
            createWorkRequest(binding.etTaskName.text.toString(), differenceInTime)
            Toast.makeText(this, "Reminder set", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createWorkRequest(message: String, timeDelayInSeconds: Long) {
        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .setInputData(
                workDataOf(
                    "title" to "Reminder",
                    "message" to message
                )
            )
            .build()
        WorkManager.getInstance(this).enqueue(request)

    }

}
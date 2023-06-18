package com.example.forgetnoting.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.forgetnoting.R
import com.example.forgetnoting.databinding.ActivityMainBinding
import com.example.forgetnoting.util.ReminderManager
import com.example.forgetnoting.util.ReminderWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
//        val today = Calendar.getInstance()
//        var chosenYear = 0
//        var chosenMonth = 0
//        var chosenDay = 0
//        var chosenHour = 0
//        var chosenMinute = 0
//        binding.datePicker.init(
//            today.get(Calendar.YEAR),
//            today.get(Calendar.MONTH),
//            today.get(Calendar.DAY_OF_MONTH)
//        ) { _, year, month, day ->
//            chosenYear = year
//            chosenMonth = month
//            chosenDay = day
//        }
//
//        binding.timePicker.setOnTimeChangedListener { _, hour, minute ->
//            chosenHour = hour
//            chosenMinute = minute
//        }
//        binding.btSet.setOnClickListener {
//            val reminderTime = "$chosenHour:$chosenMinute"
//            Log.d("Alarm--->", "$reminderTime")
//            ReminderManager.startReminder(this, reminderTime)
////            val userSelectedDateTime = Calendar.getInstance().apply {
////                set(Calendar.MINUTE, chosenMinute)
////            }
////            val todayDateTime = Calendar.getInstance()
////            if (todayDateTime.before(userSelectedDateTime)) {
////                userSelectedDateTime.add(Calendar.HOUR_OF_DAY, 1)
////            }
////            val differenceInTime =
////                (userSelectedDateTime.timeInMillis / 1000) - (todayDateTime.timeInMillis / 1000)
////            Log.d("Reminder", differenceInTime.toString())
//////            createWorkRequest(binding.etTaskName.text.toString(), differenceInTime)
//            Toast.makeText(this, "Reminder set", Toast.LENGTH_SHORT).show()
//        }
    }

//    private fun createWorkRequest(message: String, timeDelayInSeconds: Long) {
//        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
//            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
//            .setInputData(
//                workDataOf(
//                    "title" to "Reminder",
//                    "message" to message
//                )
//            )
//            .build()
//        WorkManager.getInstance(this).enqueue(request)
//
//    }

//    private fun createPeriodicWorkRequest(message: String, timeDelayInSeconds: Long) {
//        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.HOURS)
//            .addTag("PERIODIC_REMINDER")
//            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
//            .setInputData(
//                workDataOf(
//                    "title" to "Reminder",
//                    "message" to message
//                )
//            ).build()
//
//        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
//            "PERIODIC_REMINDER_NOTIFICATION",
//            ExistingPeriodicWorkPolicy.UPDATE,
//            workRequest
//        )
//    }

}
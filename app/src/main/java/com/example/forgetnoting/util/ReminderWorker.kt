package com.example.forgetnoting.util

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class ReminderWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
//        NotificationHelper(context).createNotification(
//            inputData.getString("title").toString(),
//            inputData.getString("message").toString()
//        )
//        Log.d("Reminder", Result.success().toString())
        return Result.success()
    }
}
package com.example.forgetnoting.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        if (context != null) {
            NotificationHelper(context).createNotification(
                intent.getStringExtra("Title").toString(),
                intent.getStringExtra("Desc").toString(),
                intent.getIntExtra("reminderId",0)
            )
        }
    }
}
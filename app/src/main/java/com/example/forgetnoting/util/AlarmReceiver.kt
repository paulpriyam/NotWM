package com.example.forgetnoting.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        if (context != null) {
            NotificationHelper(context).createNotification(
                "Alarm Manager",
                "This notification has been created using alarm manager set alarmClock"
            )
        }
    }
}
package com.example.forgetnoting.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CancelAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        ReminderManager.stopReminder(context, intent.getIntExtra("reminderId", 0))
    }
}
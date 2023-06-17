package com.example.forgetnoting.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import java.util.*

object ReminderManager {
    const val REMINDER_NOTIFICATION_REQUEST_CODE = 123
    fun startReminder(
        context: Context,
        reminderTime: String = "08:00",
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val (hour, minute) = reminderTime.split(":").map { it.toInt() }

        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(
                context.applicationContext,
                reminderId,
                it,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

        val calendar = Calendar.getInstance(Locale.ENGLISH).apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        if (Calendar.getInstance(Locale.ENGLISH).apply {
                add(Calendar.MINUTE, 1)
            }.timeInMillis - calendar.timeInMillis > 0) {
            calendar.add(Calendar.HOUR_OF_DAY, 1)
        }

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, intent),
            intent
        )
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_HALF_HOUR,intent)
    }

    fun stopReminder(
        context: Context,
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(
                context,
                reminderId,
                it,
                0
            )
        }
        alarmManager.cancel(intent)
    }
}
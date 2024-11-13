package com.example.todo.ui.theme.ui.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi

class ScheduleNotification(val context: Context) {
    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("NotConstructor")
    fun scheduleNotification(eventId: Int, title: String, hour: Int, min: Int) {
        /*https://developer.android.com/reference/android/app/PendingIntent#getService(android.content.Context,%2520int,%2520android.content.Intent,%2520int)*/
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("EVENT_TO_BE_DONE", title)
            putExtra("NOTIFICATION_ID", eventId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext,
            eventId,
            intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        /*https://developer.android.com/develop/background-work/services/alarms/schedule*/
        val calender = Calendar.getInstance().apply {
         timeInMillis = System.currentTimeMillis()
         set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
        }
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (alarmManager.canScheduleExactAlarms()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calender.timeInMillis,
                pendingIntent
            )
        }
    }
    fun cancelAlarmNotification(eventId: Int) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val pendingIntent =
            PendingIntent.getBroadcast(context.applicationContext, eventId, Intent(context, ReminderReceiver::class.java),
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        if (pendingIntent != null && alarmMgr != null) {
            alarmMgr.cancel(pendingIntent)
        }
    }
}
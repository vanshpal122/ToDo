package com.example.todo.ui.theme.ui.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.todo.R

class EventReminderNotification(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    companion object {
        const val REMINDER_CHANNEL_ID = "reminder_channel"
    }

    fun showNotification(notificationId: Int, title: String?) {
        val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Event")
            .setContentText("You have $title ToDo")
            .setAutoCancel(true)
            .build()
        notificationManager.notify(notificationId, notification)
    }
}


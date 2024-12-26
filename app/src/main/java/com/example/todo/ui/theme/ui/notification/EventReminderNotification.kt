package com.example.todo.ui.theme.ui.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.ui.theme.navigation.DOMAIN

class EventReminderNotification(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    companion object {
        const val REMINDER_CHANNEL_ID = "reminder_channel"
    }

    private fun getPendingIntent(notificationId: Int): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            data = "http://$DOMAIN/$notificationId".toUri()
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        return pendingIntent
    }

    fun showNotification(notificationId: Int, title: String?) {
        val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Event")
            .setContentText("You have $title ToDo")
            .setContentIntent(getPendingIntent(notificationId))
            .setAutoCancel(true)
            .build()
        notificationManager.notify(notificationId, notification)
    }
}


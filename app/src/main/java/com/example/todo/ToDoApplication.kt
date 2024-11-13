package com.example.todo

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.todo.data.AppContainer
import com.example.todo.data.DefaultAppContainer
import com.example.todo.ui.theme.ui.notification.EventReminderNotification

class ToDoApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        createNotificationChannel()
    }

    @SuppressLint("NewApi")
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //Guard this code with a condition on the SDK_INT version to run only on Android 8.0 (API level 26) and higher, because the notification channels APIs aren't available in the Support Library.
            val channel = NotificationChannel(
                EventReminderNotification.REMINDER_CHANNEL_ID,
                "Event",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This is to make you aware about the Events ToDo"
            }
            // Register the channel with the system. We can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
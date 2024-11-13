package com.example.todo.ui.theme.ui.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val title: String? = p1?.getStringExtra("EVENT_TO_BE_DONE")
        val notificationId: Int? = p1?.getIntExtra("NOTIFICATION_ID", 0)
        if (notificationId != null) {
            p0?.let { EventReminderNotification(context = it) }?.showNotification(notificationId, title)
        }
    }
}
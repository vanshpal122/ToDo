package com.example.todo.data

import android.content.Context

interface AppContainer {
    val eventsRepository: EventRepository
}

class DefaultAppContainer(private val context: Context): AppContainer {
    override val eventsRepository: AppEventsRepository by lazy {
        AppEventsRepository(EventDatabase.getDatabase(context).eventDao())
    }
}
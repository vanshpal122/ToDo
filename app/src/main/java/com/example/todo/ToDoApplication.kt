package com.example.todo

import android.app.Application
import com.example.todo.data.AppContainer
import com.example.todo.data.DefaultAppContainer

class ToDoApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
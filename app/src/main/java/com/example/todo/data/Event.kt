package com.example.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val heading: String,
    val description: String,
    val time: String,
    val duration: Int
)

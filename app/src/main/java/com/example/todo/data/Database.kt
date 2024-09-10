package com.example.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Event::class], version = 1, exportSchema = false)
abstract class EventDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao
    companion object {
        @Volatile
        private var Instance: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, EventDatabase::class.java, "Events")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
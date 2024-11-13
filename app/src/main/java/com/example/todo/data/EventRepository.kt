package com.example.todo.data

import kotlinx.coroutines.flow.Flow


interface EventRepository {
    fun getAllEvents(): Flow<List<Event>>
    suspend fun updateEvent(event: Event)
    suspend fun deleteEvent(event: Event)
    suspend fun insertEvent(event: Event)
    fun getEvent(id: Int): Flow<Event?>
    suspend fun getId(heading: String): List<Event>
}

class AppEventsRepository(private val eventDao: EventDao): EventRepository {
    override fun getEvent(id: Int): Flow<Event> = eventDao.getEvent(id)

    override fun getAllEvents(): Flow<List<Event>> = eventDao.getAllItem()

    override suspend fun getId(heading: String): List<Event> = eventDao.getId(heading)

    override suspend fun updateEvent(event: Event) {
        eventDao.update(event)
    }

    override suspend fun deleteEvent(event: Event) {
        eventDao.delete(event)
    }

    override suspend fun insertEvent(event: Event) {
        eventDao.insert(event)
    }

}
package com.example.todo.ui.theme.ui.event

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.Event
import com.example.todo.data.EventRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventDetailsViewModel (
    savedStateHandle: SavedStateHandle,
    private val toDoRepository: EventRepository
) : ViewModel() {

    suspend fun deleteEvent(event: Event) {
        toDoRepository.deleteEvent(event)
    }

    private val eventId: Int = checkNotNull(savedStateHandle[EventDetailsScreenDestination.eventId])
    val eventDetailUiState: StateFlow<EventUiState> =
        toDoRepository.getEvent(eventId).filterNotNull()
            .map { EventUiState(eventDetails = it.toEventDetails(), isEntryValid = true)/*EventDetailsUiState(eventId = eventId, eventHeading = it.heading, eventDescription = it.description, eventDuration = it.duration.toString())*/ }
            .stateIn(
                initialValue = EventUiState(),
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
        )

}

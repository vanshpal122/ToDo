package com.example.todo.ui.theme.ui.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todo.data.Event
import com.example.todo.data.EventRepository

class AddEventScreenViewModel(private val toDoRepository: EventRepository): ViewModel() {
    var eventUiState by mutableStateOf(EventUiState())
        private set

    suspend fun saveEvent() {
        if (validateInput()) {
            toDoRepository.insertEvent(eventUiState.eventDetails.toEvent())
        }
    }

    fun updateUiState(eventDetails: EventDetails) {
        eventUiState =
            EventUiState(eventDetails = eventDetails, isEntryValid = validateInput(eventDetails))
    }

    private fun validateInput(uiState: EventDetails = eventUiState.eventDetails): Boolean {
        return with(uiState) {
            heading.isNotBlank() && description.isNotBlank() && duration.isNotBlank()
        }
    }

}

data class EventUiState(
    val eventDetails: EventDetails = EventDetails(),
    val isEntryValid: Boolean = false
)

data class EventDetails(
    val id: Int = 0,
    val heading: String = "",
    val description: String = "",
    val duration: String = "",
)

fun EventDetails.toEvent(): Event = Event(
    id = id,
    heading = heading,
    description = description,
    duration = duration.toIntOrNull() ?: 0
)

fun Event.toEventUiState(isEntryValid: Boolean = false): EventUiState = EventUiState(
    eventDetails = this.toEventDetails(),
    isEntryValid = isEntryValid
)

fun Event.toEventDetails(): EventDetails = EventDetails(
    id = id,
    heading = heading,
    description = description,
    duration = duration.toString()
)
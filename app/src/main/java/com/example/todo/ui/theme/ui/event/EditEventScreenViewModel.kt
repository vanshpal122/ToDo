package com.example.todo.ui.theme.ui.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.EventRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditEventScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val toDoRepository: EventRepository
) : ViewModel() {
    var eventUiState by mutableStateOf(EventUiState())
        private set

    private val eventId: Int = checkNotNull(savedStateHandle[EditEventScreenDestination.eventId])

    init {
        viewModelScope.launch {
            eventUiState = toDoRepository.getEvent(eventId)
                .filterNotNull()
                .first()
                .toEventUiState(true)
        }
    }

    suspend fun updateEvent() {
        toDoRepository.updateEvent(eventUiState.eventDetails.toEvent())
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


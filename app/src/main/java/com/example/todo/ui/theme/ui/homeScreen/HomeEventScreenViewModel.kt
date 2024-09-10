package com.example.todo.ui.theme.ui.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.Event
import com.example.todo.data.EventRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class HomeEventScreenViewModel(private val toDoEventRepository: EventRepository): ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        toDoEventRepository.getAllEvents().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteEvent(event: Event) {
        toDoEventRepository.deleteEvent(event)
    }

}

data class HomeUiState(val listOfEvents: List<Event> = listOf())
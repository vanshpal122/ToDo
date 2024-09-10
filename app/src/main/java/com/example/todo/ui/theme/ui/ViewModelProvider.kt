package com.example.todo.ui.theme.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo.ToDoApplication
import com.example.todo.ui.theme.ui.event.AddEventScreenViewModel
import com.example.todo.ui.theme.ui.event.EditEventScreenViewModel
import com.example.todo.ui.theme.ui.event.EventDetailsViewModel
import com.example.todo.ui.theme.ui.homeScreen.HomeEventScreenViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            EditEventScreenViewModel(
                this.createSavedStateHandle(),
                toDoApplication().container.eventsRepository
            )
        }

        initializer {
             AddEventScreenViewModel(toDoApplication().container.eventsRepository)
        }

        initializer {
            HomeEventScreenViewModel(toDoApplication().container.eventsRepository)
        }

        initializer {
            EventDetailsViewModel(
                this.createSavedStateHandle(),
                toDoApplication().container.eventsRepository
            )
        }
    }
}


fun CreationExtras.toDoApplication(): ToDoApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ToDoApplication)
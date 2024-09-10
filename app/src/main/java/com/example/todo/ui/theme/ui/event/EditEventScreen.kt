package com.example.todo.ui.theme.ui.event

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.R
import com.example.todo.ui.theme.navigation.NavDestination
import com.example.todo.ui.theme.theme.ToDoTheme
import com.example.todo.ui.theme.ui.ViewModelProvider

object EditEventScreenDestination: NavDestination {
    override val name: String
        get() = "EditEvent"

    val eventId = "eventId"
    val routeToEventId = "$name/{$eventId}"
}



@Composable
fun EditEventScreen(
    context: Context?,
    onNavClick: () -> Unit,
    editEventViewModel: EditEventScreenViewModel = viewModel(factory = ViewModelProvider.Factory),
    modifier: Modifier = Modifier)
{
    AEEventScreen(
        buttonFunctionality = stringResource(R.string.save),
        eventHeading = editEventViewModel.eventUiState.eventDetails.heading,
        eventDescription = editEventViewModel.eventUiState.eventDetails.description,
        eventDuration = editEventViewModel.eventUiState.eventDetails.duration,
        onHeadValueChange = { editEventViewModel.updateUiState(editEventViewModel.eventUiState.eventDetails.copy(heading = it)) },
        onDesValueChange = { editEventViewModel.updateUiState(editEventViewModel.eventUiState.eventDetails.copy(description = it)) },
        onDurValueChange = { editEventViewModel.updateUiState(editEventViewModel.eventUiState.eventDetails.copy(duration = it)) },
        onButtonClicked =
        {
            editEventViewModel.updateEvent()
            Toast.makeText(context, context!!.getString(R.string.event_saved), Toast.LENGTH_LONG).show()
            onNavClick()
        },
        isButtonEnabled = editEventViewModel.eventUiState.isEntryValid,
        onNavClick = onNavClick,
        currentDestination = EditEventScreenDestination,
        modifier = modifier
    )
}

@Preview
@Composable
fun EditEventScreenPreview() {
    ToDoTheme {
        EditEventScreen(context = null, onNavClick = {})
    }
}
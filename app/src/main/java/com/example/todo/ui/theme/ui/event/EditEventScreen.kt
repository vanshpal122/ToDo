package com.example.todo.ui.theme.ui.event

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.R
import com.example.todo.ui.theme.navigation.NavDestination
import com.example.todo.ui.theme.theme.ToDoTheme
import com.example.todo.ui.theme.ui.ViewModelProvider
import com.example.todo.ui.theme.ui.notification.ScheduleNotification

object EditEventScreenDestination: NavDestination {
    override val name: String
        get() = "EditEvent"

    val eventId = "eventId"
    val routeToEventId = "$name/{$eventId}"
}



@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun EditEventScreen(
    context: Context?,
    onNavClick: () -> Unit,
    editEventViewModel: EditEventScreenViewModel = viewModel(factory = ViewModelProvider.Factory),
    modifier: Modifier = Modifier
)
{
    AEEventScreen(
        buttonFunctionality = stringResource(R.string.save),
        eventHeading = editEventViewModel.eventUiState.eventDetails.heading,
        eventDescription = editEventViewModel.eventUiState.eventDetails.description,
        eventDuration = editEventViewModel.eventUiState.eventDetails.duration,
        eventHour = editEventViewModel.eventUiState.eventDetails.hour,
        eventMin = editEventViewModel.eventUiState.eventDetails.min,
        eventMeridiem = editEventViewModel.eventUiState.eventDetails.meridiem,
        onHeadValueChange = { editEventViewModel.updateUiState(editEventViewModel.eventUiState.eventDetails.copy(heading = it)) },
        onDesValueChange = { editEventViewModel.updateUiState(editEventViewModel.eventUiState.eventDetails.copy(description = it)) },
        onDurValueChange = { editEventViewModel.updateUiState(editEventViewModel.eventUiState.eventDetails.copy(duration = it)) },
        onHourValueChange = { editEventViewModel.updateUiState(editEventViewModel.eventUiState.eventDetails.copy(hour = it)) },
        onMinValueChange = { editEventViewModel.updateUiState(editEventViewModel.eventUiState.eventDetails.copy(min = it)) },
        onMeridiemChange = { editEventViewModel.updateUiState(editEventViewModel.eventUiState.eventDetails.copy(meridiem = it)) },
        onButtonClicked =
        {
            Toast.makeText(context, context!!.getString(R.string.event_saved), Toast.LENGTH_LONG).show()
            editEventViewModel.updateEvent()
            ScheduleNotification(context = context).scheduleNotification(
                title = editEventViewModel.eventUiState.eventDetails.heading,
                eventId = editEventViewModel.eventId,
                hour = if(editEventViewModel.eventUiState.eventDetails.meridiem == TimeMeridiem.PM && editEventViewModel.eventUiState.eventDetails.hour.toInt() != 12) {
                    editEventViewModel.eventUiState.eventDetails.hour.toInt() + 12
                } else editEventViewModel.eventUiState.eventDetails.hour.toInt(),
                min = editEventViewModel.eventUiState.eventDetails.min.toInt()
            )
            onNavClick()
        },
        isButtonEnabled = editEventViewModel.eventUiState.isEntryValid,
        onNavClick = onNavClick,
        currentDestination = EditEventScreenDestination,
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview
@Composable
fun EditEventScreenPreview() {
    ToDoTheme {
        EditEventScreen(context = null, onNavClick = {})
    }
}
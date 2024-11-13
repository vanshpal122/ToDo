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

object AddEventScreenDestination: NavDestination {
    override val name: String
        get() = "Add Event"
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AddEventScreen(
    context: Context?,
    onNavClick: () -> Unit,
    modifier: Modifier = Modifier,
    addEventScreenViewModel: AddEventScreenViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    AEEventScreen(
        buttonFunctionality = stringResource(R.string.add),
        eventHeading = addEventScreenViewModel.eventUiState.eventDetails.heading,
        eventDescription = addEventScreenViewModel.eventUiState.eventDetails.description,
        eventDuration = addEventScreenViewModel.eventUiState.eventDetails.duration,
        eventHour = addEventScreenViewModel.eventUiState.eventDetails.hour,
        eventMin = addEventScreenViewModel.eventUiState.eventDetails.min,
        eventMeridiem = addEventScreenViewModel.eventUiState.eventDetails.meridiem,
        onHeadValueChange = {  addEventScreenViewModel.updateUiState(addEventScreenViewModel.eventUiState.eventDetails.copy(heading = it)) },
        onDesValueChange = {  addEventScreenViewModel.updateUiState(addEventScreenViewModel.eventUiState.eventDetails.copy(description = it)) },
        onDurValueChange = {  addEventScreenViewModel.updateUiState(addEventScreenViewModel.eventUiState.eventDetails.copy(duration = it)) },
        onHourValueChange = { addEventScreenViewModel.updateUiState(addEventScreenViewModel.eventUiState.eventDetails.copy(hour = it)) },
        onMinValueChange = { addEventScreenViewModel.updateUiState(addEventScreenViewModel.eventUiState.eventDetails.copy(min = it)) },
        onMeridiemChange = { addEventScreenViewModel.updateUiState(addEventScreenViewModel.eventUiState.eventDetails.copy(meridiem = it)) },
        onButtonClicked = {
            addEventScreenViewModel.saveEvent()
            Toast.makeText(context, context!!.getString(R.string.event_added), Toast.LENGTH_LONG).show()
            ScheduleNotification(context = context).scheduleNotification(
                eventId = addEventScreenViewModel.getId(addEventScreenViewModel.eventUiState.eventDetails.heading),
                hour = if(addEventScreenViewModel.eventUiState.eventDetails.meridiem == TimeMeridiem.PM) {addEventScreenViewModel.eventUiState.eventDetails.hour.toInt() + 12} else {addEventScreenViewModel.eventUiState.eventDetails.hour.toInt()},
                min = addEventScreenViewModel.eventUiState.eventDetails.min.toInt(),
                title = addEventScreenViewModel.eventUiState.eventDetails.heading
            )
            onNavClick()
        },
        isButtonEnabled = addEventScreenViewModel.eventUiState.isEntryValid,
        onNavClick = onNavClick,
        currentDestination = AddEventScreenDestination,
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview
@Composable
fun AddEventScreenPreview() {
    ToDoTheme {
        AddEventScreen(context = null, onNavClick = {})
    }
}
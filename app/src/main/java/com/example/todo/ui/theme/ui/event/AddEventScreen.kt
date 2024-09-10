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

object AddEventScreenDestination: NavDestination {
    override val name: String
        get() = "Add Event"
}

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
        onHeadValueChange = {  addEventScreenViewModel.updateUiState(addEventScreenViewModel.eventUiState.eventDetails.copy(heading = it)) },
        onDesValueChange = {  addEventScreenViewModel.updateUiState(addEventScreenViewModel.eventUiState.eventDetails.copy(description = it))},
        onDurValueChange = {  addEventScreenViewModel.updateUiState(addEventScreenViewModel.eventUiState.eventDetails.copy(duration = it))},
        onButtonClicked = {
            addEventScreenViewModel.saveEvent()
            onNavClick()
            Toast.makeText(context, context!!.getString(R.string.event_added), Toast.LENGTH_LONG).show() },
        isButtonEnabled = addEventScreenViewModel.eventUiState.isEntryValid,
        onNavClick = onNavClick,
        currentDestination = AddEventScreenDestination,
        modifier = modifier
    )
}

@Preview
@Composable
fun AddEventScreenPreview() {
    ToDoTheme {
        AddEventScreen(context = null, onNavClick = {})
    }
}
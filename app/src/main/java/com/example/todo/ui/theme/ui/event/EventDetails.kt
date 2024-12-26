package com.example.todo.ui.theme.ui.event

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.ui.theme.navigation.NavDestination
import com.example.todo.ui.theme.theme.ToDoTheme
import com.example.todo.ui.theme.ui.ToDoTopAppBar
import com.example.todo.ui.theme.ui.ViewModelProvider
import com.example.todo.ui.theme.ui.notification.ScheduleNotification
import kotlinx.coroutines.launch


object EventDetailsScreenDestination: NavDestination {
    override val name: String
        get() = "Event"

    const val eventId = "ID"
    val routeWithArgs = "$name/{$eventId}"
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    context: Context?,
    canNavigateBack: Boolean,
    onNavClick: () -> Unit,
    modifier: Modifier = Modifier,
    onEditButtonClicked: (Int) -> Unit,
    eventDetailsViewModel: EventDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = ViewModelProvider.Factory),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            ToDoTopAppBar(
                canNavigateBack = canNavigateBack,
                currentDestination = EventDetailsScreenDestination,
                scrollBehavior = scrollBehavior,
                onNaviClick = onNavClick)
        }
    ) {
        val coroutine = rememberCoroutineScope()
        val eventDetailsUiState by eventDetailsViewModel.eventDetailUiState.collectAsState()
        Column(modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
            .padding(it), verticalArrangement = Arrangement.SpaceBetween) {
            Card {
                Row(modifier = Modifier.height(52.dp)) {
                    Text(text = eventDetailsUiState.eventDetails.heading, fontSize = 24.sp, modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(start = 4.dp)
                        .weight(3f))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End).align(Alignment.Bottom).weight(2f)
                    ) {
                        Text(text = "Time: ${eventDetailsUiState.eventDetails.hour}:${padZero(eventDetailsUiState.eventDetails.min.toIntOrNull() ?: 0)} ${eventDetailsUiState.eventDetails.meridiem.name}")
                        Text(
                            text = stringResource(
                                R.string.duration_min,
                                eventDetailsUiState.eventDetails.duration
                            )
                        )
                    }
                }
                HorizontalDivider(thickness = 2.dp, color = Color.Black, modifier = Modifier.padding(bottom = 8.dp))
                Text(text = eventDetailsUiState.eventDetails.description, modifier = Modifier.padding(8.dp))
            }
            Column {
                Button(
                    onClick = { onEditButtonClicked(eventDetailsUiState.eventDetails.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = stringResource(R.string.edit))
                }
                Button(
                    onClick = {
                        coroutine.launch {
                            Toast.makeText(context, context!!.getString(R.string.event_deleted), Toast.LENGTH_LONG).show()
                            ScheduleNotification(context = context).cancelAlarmNotification(eventDetailsViewModel.eventId)
                            eventDetailsViewModel.deleteEvent(eventDetailsUiState.eventDetails.toEvent())
                            onNavClick()
                        }
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = stringResource(R.string.delete))
                }
            }
        }
    }

}

@Preview
@Composable
fun EventDetailsScreenPreview() {
    ToDoTheme {
        EventDetailsScreen(context = null, onEditButtonClicked = {}, onNavClick = {}, canNavigateBack = false)
    }
}

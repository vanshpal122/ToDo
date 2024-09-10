package com.example.todo.ui.theme.ui.homeScreen


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.data.Event
import com.example.todo.ui.theme.navigation.NavDestination
import com.example.todo.ui.theme.theme.ToDoTheme
import com.example.todo.ui.theme.ui.ToDoTopAppBar
import com.example.todo.ui.theme.ui.ViewModelProvider
import kotlinx.coroutines.launch

enum class MenuOptions {
    Edit,
    Delete,
}

object HomeEventScreenDestination: NavDestination {
    override val name: String
        get() = "ToDo"
}


val menuOptions: List<MenuOptions> = listOf(MenuOptions.Edit, MenuOptions.Delete)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentEventScreen(
    context: Context?,
    onFloatingActionButtonClick: () -> Unit,
    onEditMenuItemClicked: (Int) -> Unit,
    onEventClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeEventScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = ViewModelProvider.Factory),
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            ToDoTopAppBar(
                canNavigateBack = false,
                currentDestination = HomeEventScreenDestination,
                scrollBehavior = scrollBehavior,
                onNaviClick = { }
            )
        },
        floatingActionButton = {
            AddNewItemButton {
                onFloatingActionButtonClick()
            }
        }
    ) {
        innerPadding ->
        if (homeUiState.listOfEvents.isEmpty()) {
            Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                Spacer(modifier = Modifier.padding(1.dp))
                Text(text = stringResource(R.string.no_events_todo))
                Spacer(modifier = Modifier.padding(1.dp))
            }
        }
        else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
                    .padding(innerPadding)
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                items(homeUiState.listOfEvents) {
                    EventCard(
                        event = it,
                        onClick = {
                            onEventClick(it.id)
                        },
                        onEditMenuItemClicked = { onEditMenuItemClicked(it.id) },
                        onDeleteMenuItemClicked = {
                            coroutineScope.launch{
                                Toast.makeText(context, context!!.getString(R.string.event_deleted), Toast.LENGTH_LONG).show()
                                viewModel.deleteEvent(it)
                            }
                                                  },
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}


@Composable
fun EventCard(onEditMenuItemClicked: () -> Unit, onDeleteMenuItemClicked: () -> Unit, event: Event, onClick: () -> Unit, modifier: Modifier = Modifier) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffSet by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    Card(
        modifier = modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        isContextMenuVisible = true
                        pressOffSet = DpOffset(it.x.toDp(), it.y.toDp())
                    },
                    onTap = {
                        onClick()
                    }
                )
            }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = event.heading, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.padding(bottom = 12.dp))
            Text(text = event.description)
        }

        DropdownMenu(expanded = isContextMenuVisible, onDismissRequest = { isContextMenuVisible = false }, offset = pressOffSet.copy(y = pressOffSet.y - itemHeight)) {
            DropdownMenuItem(
                text = { Text(text = menuOptions[0].name) },
                onClick = { onEditMenuItemClicked()
                    isContextMenuVisible = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = menuOptions[1].name) },
                onClick = {
                    onDeleteMenuItemClicked()
                    isContextMenuVisible = false
                }
            )
        }
    }
}

@Composable
fun AddNewItemButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Edit")
    }
}


@Preview
@Composable
fun DropDownPreview() {
    ToDoTheme {
        CurrentEventScreen(
            context = null,
            onFloatingActionButtonClick = {  },
            onEditMenuItemClicked = {},
            onEventClick = {}
        )
    }
}
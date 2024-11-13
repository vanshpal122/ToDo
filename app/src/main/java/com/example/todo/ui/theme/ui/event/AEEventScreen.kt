package com.example.todo.ui.theme.ui.event

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.theme.navigation.NavDestination
import com.example.todo.ui.theme.theme.ToDoTheme
import com.example.todo.ui.theme.ui.ToDoTopAppBar
import kotlinx.coroutines.launch
import java.util.Calendar

const val MAX_HEAD_LEN = 25
const val MAX_DES_LEN = 100
const val HOUR_LIMIT = 12
const val MIN_LIMIT = 59

enum class TimeMeridiem {
    AM,
    PM
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AEEventScreen(
    eventHeading: String,
    eventDescription: String,
    eventDuration: String,
    eventHour: String,
    eventMin: String,
    eventMeridiem: TimeMeridiem,
    buttonFunctionality: String,
    onHeadValueChange: (String) -> Unit,
    onDesValueChange: (String) -> Unit,
    onDurValueChange: (String) -> Unit,
    onHourValueChange: (String) -> Unit,
    onMinValueChange: (String) -> Unit,
    onMeridiemChange: (TimeMeridiem) -> Unit,
    onButtonClicked: suspend () -> Unit,
    isButtonEnabled: Boolean,
    onNavClick: () -> Unit,
    currentDestination: NavDestination,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var isSetTimingDown by remember {
        mutableStateOf(false)
    }

    var isMeridiemDropDown by remember {
        mutableStateOf(false)
    }

    var isTimeExceeded by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            ToDoTopAppBar(
                canNavigateBack = canNavigateBack,
                currentDestination = currentDestination,
                scrollBehavior = scrollBehavior,
                onNaviClick = { onNavClick() })
        }
    ) {
        innerPadding ->
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
                .padding(innerPadding)) {
            EventAddField(
                text = eventHeading,
                label = stringResource(R.string.event),
                onValueChange = { onHeadValueChange(it)  },
                supportingText = "${eventHeading.length}/$MAX_HEAD_LEN",
                characterLimit = MAX_HEAD_LEN,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            EventAddField(
                text = eventDescription,
                label = stringResource(R.string.event_description),
                onValueChange = { onDesValueChange(it) },
                supportingText = "${eventDescription.length}/$MAX_DES_LEN",
                characterLimit = MAX_DES_LEN,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
            )
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            EventAddField(
                text = eventDuration,
                label = stringResource(R.string.event_duration),
                onValueChange = onDurValueChange,
                keyBoardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                characterLimit = 3,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            Card(onClick = { isSetTimingDown = !isSetTimingDown }, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Row(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 16.dp, bottom = 16.dp)) {
                        Text(
                            text = "Set Timings",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 12.dp)
                        )
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)) {
                            Icon(
                                imageVector = if (!isSetTimingDown) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                                contentDescription = "SetTime",
                            )
                        }
                    }
                    if (isSetTimingDown) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            TimeEventAddField(
                                label = "HR",
                                fillTimings = eventHour,
                                onChangeTimings = { onHourValueChange(it) },
                                timeLimit = HOUR_LIMIT,
                                imeAction = ImeAction.Next,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            )
                            TimeEventAddField(
                                label = "MIN",
                                fillTimings = eventMin,
                                onChangeTimings = { onMinValueChange(it) },
                                timeLimit = MIN_LIMIT,
                                imeAction = ImeAction.Done,
                                modifier = Modifier.weight(1f)
                            )
                            TimeMeridiemToggleButton (
                                isDropDown = isMeridiemDropDown,
                                onDropDownClick = { isMeridiemDropDown = it},
                                currentMeridiem = eventMeridiem,
                                onMeridiemClick = { onMeridiemChange(it) }
                            )
                        }
                    }
                }
            }
            if (isTimeExceeded) {
                Text(text = stringResource(R.string.time_already_passed), color = Color.Red)
            }
            Spacer(modifier = Modifier.padding(bottom = 80.dp))
            Button(
                enabled = isButtonEnabled, 
                onClick = {
                    isTimeExceeded = checkTimeAlreadyPassed(eventHour.toIntOrNull(), eventMin.toIntOrNull(), eventMeridiem)
                    if (!isTimeExceeded) {
                            coroutineScope.launch { onButtonClicked() }
                    } },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = buttonFunctionality, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }

    }
}

@Composable
fun TimeMeridiemToggleButton(
    isDropDown: Boolean,
    onDropDownClick: (Boolean) -> Unit,
    currentMeridiem: TimeMeridiem,
    onMeridiemClick: (TimeMeridiem) -> Unit
) {
    Card(onClick = { onDropDownClick(!isDropDown) }) {
        Row {
            Text(text = currentMeridiem.name)
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "TimeSelection")
        }
        DropdownMenu(expanded = isDropDown, onDismissRequest = { onDropDownClick(!isDropDown) }) {
            DropdownMenuItem(text = { Text(text = TimeMeridiem.AM.name) }, onClick = { onMeridiemClick(TimeMeridiem.AM)
                onDropDownClick(!isDropDown)})
            DropdownMenuItem(text = { Text(text = TimeMeridiem.PM.name) }, onClick = { onMeridiemClick(TimeMeridiem.PM)
                onDropDownClick(!isDropDown)})
        }
    }
}

@Composable
fun TimeEventAddField(
    fillTimings: String,
    onChangeTimings: (String) -> Unit,
    label: String,
    timeLimit: Int,
    imeAction: ImeAction,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    var len by rememberSaveable {
        mutableIntStateOf(0)
    }

    TextField(
        value = fillTimings,
        label = {
            Text(
                text = label, modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End))
                },
        onValueChange = {
            if (it.toIntOrNull() != null) {
                len = it.toInt()
                onChangeTimings(it)
            }
             else if(it == "") {
                len = 0
                onChangeTimings(it)
            }
        },
        keyboardOptions = KeyboardOptions().copy(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        isError =  len > timeLimit,
        modifier = modifier
    )
}


@Composable
fun EventAddField(
    label: String,
    text: String,
    supportingText: String = "",
    characterLimit: Int,
    onValueChange: (String) -> Unit,
    keyBoardOptions: KeyboardOptions = KeyboardOptions.Default,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    var len by rememberSaveable {
        mutableIntStateOf(0)
    }
    TextField(
        value = text,
        label = { Text(text = label) },
        onValueChange = {
            len = it.length
            if (len <= characterLimit) onValueChange(it)
                        },
        keyboardOptions = keyBoardOptions,
        isError = len > characterLimit,
        supportingText =
        { Text(text = supportingText, modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End))
        },
        modifier = modifier
    )
}

fun checkTimeAlreadyPassed(hour: Int?, min: Int?, meridiem: TimeMeridiem): Boolean {
    if (hour == null || min == null) return false
    val calendar = Calendar.getInstance()
    val inputTime = twelveHourToTwentyFourClock(hour, min, meridiem)
    val inputTimeInMinutes = parseHoursAndMinutes(inputTime, "HR") * 60 + parseHoursAndMinutes(inputTime, "MIN")
    val currentTimeInMinute = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
    return inputTimeInMinutes < currentTimeInMinute
}


@Preview
@Composable
fun AEEventScreenPreview() {
    ToDoTheme {
        AEEventScreen(
            eventHeading = "",
            eventDescription = "",
            eventDuration = "",
            eventHour = "",
            eventMin = "",
            eventMeridiem = TimeMeridiem.AM,
            buttonFunctionality = "Add",
            onHeadValueChange = {},
            onDesValueChange = {},
            onDurValueChange = {},
            onHourValueChange = {},
            onMinValueChange = {},
            onMeridiemChange = {},
            onButtonClicked = {},
            isButtonEnabled = false,
            onNavClick = {  },
            currentDestination = AddEventScreenDestination
        )
    }
}


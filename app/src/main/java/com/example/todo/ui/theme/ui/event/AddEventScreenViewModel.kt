package com.example.todo.ui.theme.ui.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todo.data.Event
import com.example.todo.data.EventRepository

class AddEventScreenViewModel(private val toDoRepository: EventRepository): ViewModel() {
    var eventUiState by mutableStateOf(EventUiState())
        private set


    suspend fun getId(heading: String): Int {
        return toDoRepository.getId(heading).last().id
    }

    suspend fun saveEvent() {
        if (validateInput()) {
            toDoRepository.insertEvent(eventUiState.eventDetails.toEvent())
        }
    }


    fun updateUiState(eventDetails: EventDetails) {
        eventUiState =
            EventUiState(eventDetails = eventDetails, isEntryValid = validateInput(eventDetails))
    }

    private fun validateInput(uiState: EventDetails = eventUiState.eventDetails): Boolean {
        return with(uiState) {
            heading.isNotBlank() && description.isNotBlank() && duration.isNotBlank() && hour.isNotBlank() && min.isNotBlank()
        }
    }
}

data class EventUiState(
    val eventDetails: EventDetails = EventDetails(),
    val isEntryValid: Boolean = false
)


data class EventDetails(
    val id: Int = 0,
    val heading: String = "",
    val description: String = "",
    val duration: String = "",
    val hour: String = "",
    val min: String = "",
    val meridiem: TimeMeridiem = TimeMeridiem.AM
)

fun EventDetails.toEvent(): Event = Event(
    id = id,
    heading = heading,
    description = description,
    duration = duration.toIntOrNull() ?: 0,
    time = twelveHourToTwentyFourClock(hour.toInt(), min.toInt(), meridiem)
)

fun Event.toEventUiState(isEntryValid: Boolean = false): EventUiState = EventUiState(
    eventDetails = this.toEventDetails(),
    isEntryValid = isEntryValid
)

fun Event.toEventDetails(): EventDetails = EventDetails(
    id = id,
    heading = heading,
    description = description,
    duration = duration.toString(),
    hour = parseHoursAndMinutes(twentyFourToTwelveHourClock(time), "HR").toString(),
    min = parseHoursAndMinutes(twentyFourToTwelveHourClock(time), "MIN").toString(),
    meridiem = if (parseHoursAndMinutes(time, "HR") > 12) TimeMeridiem.PM else TimeMeridiem.AM
    )


fun parseHoursAndMinutes(time: String, value: String): Int {
    // Check if the time contains AM/PM
    val isAmPmFormat = time.contains("AM", true) || time.contains("PM", true)

    if (isAmPmFormat) {
        // Split the time and meridiem (AM/PM) part
        val parts = time.split(" ")
        if (parts.size != 2) {
            throw IllegalArgumentException("Invalid time format. Expected 'HH:mm AM/PM' but got '$time'.")
        }

        val timePart = parts[0]  // "12:45"
        //val meridiem = parts[1].uppercase()  // "AM" or "PM"

        // Split the time part into hours and minutes
        val timeSplit = timePart.split(":")
        if (timeSplit.size != 2) {
            throw IllegalArgumentException("Invalid time format. Expected 'HH:mm' but got '$timePart'.")
        }

        val hour = timeSplit[0].toIntOrNull()
        val minute = timeSplit[1].toIntOrNull()

        if (hour == null || minute == null) {
            throw IllegalArgumentException("Invalid hour or minute in time string '$time'.")
        }

        // If we're asking for the hour
        if (value == "HR") {
            return hour
        }

        // If we're asking for the minute
        if (value == "MIN") {
            return minute
        }

        throw IllegalArgumentException("Invalid value '$value' passed to parseHoursAndMinutes. Expected 'HR' or 'MIN'.")
    } else {
        // For 24-hour time format (e.g., "13:45")
        val timeSplit = time.split(":")
        if (timeSplit.size != 2) {
            throw IllegalArgumentException("Invalid time format. Expected 'HH:mm' but got '$time'.")
        }

        val hour = timeSplit[0].toIntOrNull()
        val minute = timeSplit[1].toIntOrNull()

        if (hour == null || minute == null) {
            throw IllegalArgumentException("Invalid hour or minute in time string '$time'.")
        }

        // If we're asking for the hour
        if (value == "HR") {
            return hour
        }

        // If we're asking for the minute
        if (value == "MIN") {
            return minute
        }

        throw IllegalArgumentException("Invalid value '$value' passed to parseHoursAndMinutes. Expected 'HR' or 'MIN'.")
    }
}


fun padZero(time: Int): String {
    return time.toString().padStart(2, '0')
}

fun twelveHourToTwentyFourClock(hour: Int, min: Int, meridiem: TimeMeridiem): String {
    val adjustedHour = when {
        meridiem == TimeMeridiem.PM && hour != 12 -> hour + 12  // PM conversion, except for 12 PM
        meridiem == TimeMeridiem.AM && hour == 12 -> 0          // 12 AM should be converted to 00 (midnight)
        else -> hour // No change for other AM times
    }
    return "${padZero(adjustedHour)}:${padZero(min)}"
}

fun twentyFourToTwelveHourClock(time: String): String {
    val hour = parseHoursAndMinutes(time, "HR")
    val min = parseHoursAndMinutes(time, "MIN")

    // Determine AM or PM
    val amPm = if (hour < 12) "AM" else "PM"

    // Adjust hour for 12-hour format
    val adjustedHour = when {
        hour == 0 -> 12 // Midnight (00:00) -> 12:00 AM
        hour > 12 -> hour - 12 // PM times, subtract 12 to convert to 12-hour format
        hour == 12 -> 12 // Noon (12:00) remains as 12:00 PM
        else -> hour // For hours between 1 and 11, no change needed
    }

    // Return the formatted time in 12-hour format with AM/PM
    return "${padZero(adjustedHour)}:${padZero(min)} $amPm"
}


package com.example.todo.ui.theme.ui.rationalScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.theme.ToDoTheme

@Composable
fun GrantPermissionScreen(onInClick:() -> Unit, onCancelClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(12.dp))
        Text(text = "Dont forget the Event ToDo Allow the Noitification to get the Reminders")
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {  }) {
                Text(text = "Cancel")
            }
            Button(onClick = { onInClick() }) {
                Text(text = "I'm In")
            }
        }
    }
}

@Preview
@Composable
fun GrantPermissionPreview() {
    ToDoTheme {
        GrantPermissionScreen(onInClick = {}, onCancelClick = {})
    }
}
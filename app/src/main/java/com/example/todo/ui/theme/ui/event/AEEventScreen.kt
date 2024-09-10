package com.example.todo.ui.theme.ui.event

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.theme.navigation.NavDestination
import com.example.todo.ui.theme.ui.ToDoTopAppBar
import kotlinx.coroutines.launch

const val MAX_CHAR = 25

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AEEventScreen(
    eventHeading: String,
    eventDescription: String,
    eventDuration: String,
    buttonFunctionality: String,
    onHeadValueChange: (String) -> Unit,
    onDesValueChange: (String) -> Unit,
    onDurValueChange: (String) -> Unit,
    onButtonClicked: suspend () -> Unit,
    isButtonEnabled: Boolean,
    canNavigateBack: Boolean = true,
    onNavClick: () -> Unit,
    currentDestination: NavDestination,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
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
            //Text(text = "Event Heading", fontWeight = FontWeight.Bold)
            //HorizontalDivider()
            EventAddField(
                text = eventHeading,
                label = stringResource(R.string.event),
                onValueChange = onHeadValueChange,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            EventAddField(
                text = eventDescription,
                label = stringResource(R.string.event_description),
                onValueChange = onDesValueChange,
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
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(bottom = 80.dp))
            Button(enabled = isButtonEnabled, onClick = { coroutineScope.launch { onButtonClicked() } }, modifier = Modifier.fillMaxWidth()) {
                Text(text = buttonFunctionality, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }

    }

}

@Composable
fun EventAddField(label: String, text: String, onValueChange: (String) -> Unit, keyBoardOptions: KeyboardOptions = KeyboardOptions.Default, @SuppressLint(
    "ModifierParameter"
) modifier: Modifier = Modifier) {
    TextField(
        value = text,
        label = { Text(text = label) },
        onValueChange = { onValueChange(it) },
        keyboardOptions = keyBoardOptions,
        modifier = modifier
    )
}
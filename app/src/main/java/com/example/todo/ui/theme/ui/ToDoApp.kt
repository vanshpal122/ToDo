package com.example.todo.ui.theme.ui

import android.content.Context
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todo.R
import com.example.todo.ui.theme.navigation.NavDestination
import com.example.todo.ui.theme.navigation.ToDoNavigationGraph



@Composable
fun ToDoApp(navController: NavHostController = rememberNavController(), context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        ToDoNavigationGraph(context, navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoTopAppBar(canNavigateBack: Boolean, currentDestination: NavDestination, scrollBehavior: TopAppBarScrollBehavior, onNaviClick:() -> Unit, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = currentDestination.name)
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNaviClick) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(
                        R.string.navigateback
                    )
                    )
                }
            }
//            else {
//                IconButton(onClick = {}) {
//                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "NavigationDrawer")
//                }
//            }
        }
    )
}



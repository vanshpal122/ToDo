package com.example.todo.ui.theme.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todo.ui.theme.ui.event.AddEventScreen
import com.example.todo.ui.theme.ui.event.AddEventScreenDestination
import com.example.todo.ui.theme.ui.event.EditEventScreen
import com.example.todo.ui.theme.ui.event.EditEventScreenDestination
import com.example.todo.ui.theme.ui.event.EventDetailsScreen
import com.example.todo.ui.theme.ui.event.EventDetailsScreenDestination
import com.example.todo.ui.theme.ui.homeScreen.CurrentEventScreen
import com.example.todo.ui.theme.ui.homeScreen.HomeEventScreenDestination

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ToDoNavigationGraph(context: Context, navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeEventScreenDestination.name) {
        composable(HomeEventScreenDestination.name) {
            CurrentEventScreen(
                context = context,
                onEditMenuItemClicked = { navController.navigate("${EditEventScreenDestination.name}/${it}") },
                onFloatingActionButtonClick = { navController.navigate(AddEventScreenDestination.name) },
                onEventClick = { navController.navigate("${EventDetailsScreenDestination.name}/${it}") }
            )
        }

        composable(
            route = EventDetailsScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(EventDetailsScreenDestination.eventId) {
                type = NavType.IntType
            })
        ) {
            EventDetailsScreen(
                context = context,
                canNavigateBack = true,
                onNavClick = { navController.navigateUp() },
                onEditButtonClicked = { navController.navigate("${EditEventScreenDestination.name}/${it}") })
        }

        composable(route = EditEventScreenDestination.routeToEventId,
            arguments = listOf(
                navArgument(EditEventScreenDestination.eventId) {
                type = NavType.IntType
            }
            )
        ) {
            EditEventScreen(context = context, onNavClick = { navController.popBackStack(HomeEventScreenDestination.name, false) })
        }
        
        composable(route = AddEventScreenDestination.name) {
            AddEventScreen(context = context, onNavClick = { navController.popBackStack() })
        }
    }
}
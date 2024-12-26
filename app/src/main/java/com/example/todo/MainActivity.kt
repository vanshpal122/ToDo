package com.example.todo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.todo.ui.theme.theme.ToDoTheme
import com.example.todo.ui.theme.ui.ToDoApp

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                list: Map<String, @JvmSuppressWildcards Boolean> ->
                list.forEach {
                    if (it.value) {
                        println("Permission Accepted")
                    }
                    else {
                        println("Permission Denied")
                    }
                }
            }
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                when {
                    ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.SCHEDULE_EXACT_ALARM
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        ToDoApp(context = applicationContext)
                    }
                    else -> {
                        requestPermissionLauncher.launch(arrayOf(Manifest.permission.SCHEDULE_EXACT_ALARM, Manifest.permission.POST_NOTIFICATIONS))
                        ToDoApp(context = applicationContext)
                    }
                }
            }
        }
    }
}

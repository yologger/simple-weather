package com.yologger.simpleweather.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.yologger.simpleweather.ui.theme.Purple200

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navigateUp: () -> Unit
) {
    viewModel.test()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Settings")
                },
                backgroundColor =  MaterialTheme.colors.primarySurface,
                navigationIcon = {
                    IconButton(onClick = { navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) {
        Column() {
            Text(text = "SettingsScreen")
            Button(onClick = {
                navigateUp()
            }) {
                Text(text = "Go to settings")
            }
        }
    }
}
package com.yologger.simpleweather.ui.screen.settings

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yologger.simpleweather.ui.theme.Teal200

@ExperimentalPermissionsApi
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel<SettingsViewModel>(),
    navigateUp: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Teal200)

    // viewModel.test()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Settings")
                },
                backgroundColor = MaterialTheme.colors.primarySurface,
                navigationIcon = {
                    IconButton(onClick = { navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) {

    }
}
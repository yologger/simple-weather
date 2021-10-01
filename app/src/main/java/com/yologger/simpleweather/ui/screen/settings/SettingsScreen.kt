package com.yologger.simpleweather.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.yologger.simpleweather.ui.theme.Purple200

@ExperimentalPermissionsApi
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
                backgroundColor = MaterialTheme.colors.primarySurface,
                navigationIcon = {
                    IconButton(onClick = { navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) {
        val accessFineLocationPermissionState = rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            if (accessFineLocationPermissionState.shouldShowRationale) {
                PermissionRationale()
            }

            if (accessFineLocationPermissionState.hasPermission) {
                PermissionGrantedButton()
            } else if (accessFineLocationPermissionState.hasBeenDeniedForever()) {
                PermissionDeniedButton()
            } else {
                RequestPermissionButton(accessFineLocationPermissionState)
            }
        }
    }
}

@Composable
private fun PermissionRationale() {
    Text("This is a rationale explaining why we need the ACCESS_FINE_LOCATION permission. We are displaying this because the user has denied the permission once.")
}

@ExperimentalPermissionsApi
@Composable
private fun RequestPermissionButton(accessFineLocationPermissionState: PermissionState) {
    Button(onClick = {
        if (!accessFineLocationPermissionState.hasPermission) {
            accessFineLocationPermissionState.launchPermissionRequest()
        }
    }) {
        Text( "Request ACCESS_FINE_LOCATION permission")
    }
}

@Composable
private fun PermissionGrantedButton() {
    Button(onClick = {
    }) {
        Text("ACCESS_FINE_LOCATION permission granted.")
    }
}

@Composable
private fun PermissionDeniedButton() {
    Button(onClick = {
    }) {
        Text("Camera permission denied forever - open settings.")
    }
}

@ExperimentalPermissionsApi
fun PermissionState.hasBeenDeniedForever(): Boolean {
    return this.permissionRequested && !this.shouldShowRationale
}

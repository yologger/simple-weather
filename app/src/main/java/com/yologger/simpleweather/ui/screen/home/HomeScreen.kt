package com.yologger.simpleweather.ui.screen.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yologger.simpleweather.ui.extension.hasBeenDeniedForever
import com.yologger.simpleweather.ui.theme.Sky200
import com.yologger.simpleweather.ui.theme.Sky500
import com.yologger.simpleweather.ui.theme.Teal200

@ExperimentalPermissionsApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    navigateToSettings: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Teal200)

    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val activity = (LocalContext.current as Activity)
    val packageName = activity.packageName

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(onClick = { viewModel.refreshWeather() }) { Icon(Icons.Filled.Refresh, "") } }
    ) {
        when {
            locationPermissionsState.allPermissionsGranted -> HomeScreenContent(viewModel = viewModel, navigateToSettings = navigateToSettings)
            locationPermissionsState.hasBeenDeniedForever() -> HomeScreenPermissionDialog(
                onSettingClick = {
                    // Open Setting App
                    val nextIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
                    activity.startActivity(nextIntent)
                    activity.finish()
                },
                onDenyClick = {
                    activity.finish()
                })
            else -> {
                if (locationPermissionsState.shouldShowRationale) {
                    HomeScreenPermissionDialog(
                        onSettingClick = {
                            // Open Setting App
                            val nextIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
                            activity.startActivity(nextIntent)
                            activity.finish()
                        }, onDenyClick = {
                            activity.finish()
                        })
                } else {
                    locationPermissionsState.launchMultiplePermissionRequest()
                }
            }
        }
    }
}
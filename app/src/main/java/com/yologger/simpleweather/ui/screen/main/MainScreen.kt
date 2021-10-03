package com.yologger.simpleweather.ui.screen.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.yologger.simpleweather.ui.theme.Purple200
import com.yologger.simpleweather.R
import com.yologger.simpleweather.ui.extension.hasBeenDeniedForever

@ExperimentalPermissionsApi
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navigateToSettings: () -> Unit
) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val activity = (LocalContext.current as? Activity)
    val packageName = activity?.packageName

    Scaffold(
        topBar = { HomeScreenTopAppBar(navigateToSettings) }
    ) {
        when {
            locationPermissionsState.allPermissionsGranted -> {
                HomeScreenContent()
            }
            locationPermissionsState.hasBeenDeniedForever() -> {
                RequirePermissionDialog(
                    onSettingClick = {
                        val nextIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName!!, null))
                        activity?.startActivity(nextIntent)
                    }, onDenyClick = {
                        activity?.finish()
                    })
            }
            else -> {
                if (locationPermissionsState.shouldShowRationale) {
                    RequirePermissionDialog(
                        onSettingClick = {
                            val nextIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName!!, null))
                            activity?.startActivity(nextIntent)
                        }, onDenyClick = {
                            activity?.finish()
                        })
                } else {
                    locationPermissionsState.launchMultiplePermissionRequest()
                }
            }
        }
    }
}

@Composable
fun HomeScreenTopAppBar(
    navigateToSettings: () -> Unit
) {
    TopAppBar(
        elevation = 0.dp,
        title = { Text("TopAppBar") },
        backgroundColor = Color.Transparent,
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Refresh, null)
            }
            IconButton(onClick = { navigateToSettings() }) {
                Icon(Icons.Filled.Settings, null)
            }
        }
    )
}

@Composable
fun HomeScreenContent() {
    Box(
        modifier = Modifier
            .background(Color(0xFF00063D))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("금천구 가산동", color = Color.White, style = Typography().h5)
            Text("수요일 9월 29일, 09:04 오후", color = Color.White, style = Typography().body1)
            Spacer(modifier = Modifier.size(30.dp))
            Image(
                painter = painterResource(id = R.drawable.weather),
                contentDescription = "",
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.size(30.dp))
            Text("19°C", color = Color.White, style = Typography().h2)
            Text("맑음", color = Color.White, style = Typography().body1)
        }
    }
}

@Composable
fun RequirePermissionDialog(
    onSettingClick: () -> Unit,
    onDenyClick: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text(text = "Location Permission") },
            text = { Text("This application requires access to you location") },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        onSettingClick()
                    }) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        onDenyClick()
                    }) {
                    Text("Deny")
                }
            }
        )
    }
}
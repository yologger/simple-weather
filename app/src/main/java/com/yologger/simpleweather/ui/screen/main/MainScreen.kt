package com.yologger.simpleweather.ui.screen.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
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
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.yologger.simpleweather.R
import com.yologger.simpleweather.ui.extension.hasBeenDeniedForever


@ExperimentalPermissionsApi
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel<MainViewModel>(),
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
        // topBar = { HomeScreenTopAppBar(navigateToSettings) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.refreshWeather()
            }) {
                Icon(Icons.Filled.Refresh, "")
            }
        },
    ) {
        when {
            locationPermissionsState.allPermissionsGranted -> {
                HomeScreenContent(viewModel = viewModel, navigateToSettings = navigateToSettings)
            }
            locationPermissionsState.hasBeenDeniedForever() -> {
                RequirePermissionDialog(
                    onSettingClick = {
                        val nextIntent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", packageName!!, null)
                        )
                        activity?.startActivity(nextIntent)
                    }, onDenyClick = {
                        activity?.finish()
                    })
            }
            else -> {
                if (locationPermissionsState.shouldShowRationale) {
                    RequirePermissionDialog(
                        onSettingClick = {
                            val nextIntent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", packageName!!, null)
                            )
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
        title = {
            // Text("TopAppBar")
        },
        backgroundColor = Color.Transparent,
        actions = {
            IconButton(onClick = { navigateToSettings() }) {
                Icon(Icons.Filled.Settings, null)
            }
        }
    )
}

@Composable
fun HomeScreenContent(
    viewModel: MainViewModel,
    navigateToSettings: () -> Unit
) {

    val temperature: Double by viewModel.liveTemperature.observeAsState(0.0)
    val isLoading: Boolean by viewModel.liveIsLoading.observeAsState(false)
    val weather: String by viewModel.liveWeather.observeAsState(initial = "")
    val location: String by viewModel.liveLocation.observeAsState(initial = "")
    val currentTime: String by viewModel.liveCurrentTime.observeAsState(initial = "")
    val icon: String by viewModel.liveIcon.observeAsState(initial = "")

    var latitude: Double? = null
    var longitude: Double? = null

    val activity = (LocalContext.current as? Activity)

    activity?.let {
        val hasFineLocationPermission =
            ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission =
            ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION)

        // Permission check
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED || hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            val locationManager =
                activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                location?.run {
                    latitude = location.latitude
                    longitude = location.longitude
                }
            }
        }
    }

    Log.d("KKK", "iicon: ${icon}")
    if (isLoading) {
        LoadingContent()
    } else {
        Box(
            modifier = Modifier
                .background(Color(0xFF00063D))
                .fillMaxSize()
        ) {
            IconButton(
                onClick = { navigateToSettings() },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(Icons.Outlined.Settings, null, tint = Color.White)
            }
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("${location}", color = Color.White, style = Typography().h4)
                Spacer(modifier = Modifier.size(10.dp))
                Text("${currentTime}", color = Color.White, style = Typography().body1)
                Spacer(modifier = Modifier.size(30.dp))

                if (icon.isNullOrBlank()) {
//                    Image(
//                        painter = painterResource(id = R.drawable.weather),
//                        contentDescription = "",
//                        modifier = Modifier
//                            .width(150.dp)
//                            .height(150.dp)
//                    )
                } else {
                    val imageUrl = "http://openweathermap.org/img/wn/${icon}@2x.png";
                    GlideImage(
                        imageModel = imageUrl,
                        modifier = Modifier.width(50.dp).height(50.dp),
                        // Crop, Fit, Inside, FillHeight, FillWidth, None
                        contentScale = ContentScale.Crop,
                        // shows an image with a circular revealed animation.
                        circularReveal = CircularReveal(duration = 250),
                        // shows a placeholder ImageBitmap when loading.
                        // placeHolder = ImageBitmap.imageResource(R.drawable.placeholder),
                        // shows an error ImageBitmap when the request failed.
                        // error = ImageBitmap.imageResource(R.drawable.error)
                    )
                }

                Spacer(modifier = Modifier.size(30.dp))
                Text("${temperature}°", color = Color.White, style = Typography().h2)
                Text("${weather}", color = Color.White, style = Typography().body1)
            }
        }
    }
}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
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
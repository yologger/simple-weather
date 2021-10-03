package com.yologger.simpleweather.ui

import android.app.Activity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.yologger.simpleweather.ui.screen.main.MainScreen
import com.yologger.simpleweather.ui.screen.settings.SettingsScreen
import com.yologger.simpleweather.ui.theme.SimpleWeatherTheme

object NavDestinations {
    const val MAIN_ROUTE = "main"
    const val SETTINGS_ROUTE = "settings"
}

class NavActions(navController: NavHostController) {
    val navigateToSettings: () -> Unit = {
        navController.navigate(NavDestinations.SETTINGS_ROUTE)
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}

@ExperimentalPermissionsApi
@Composable
fun App(
) {
    SimpleWeatherTheme {
        val scaffoldState = rememberScaffoldState()
        val navController = rememberNavController()
        val coroutineScope = rememberCoroutineScope()
        val actions = remember(navController) { NavActions(navController) }

        Scaffold(
            scaffoldState = scaffoldState,
        ) {

            NavHost(navController = navController, startDestination = NavDestinations.MAIN_ROUTE) {
                composable(NavDestinations.MAIN_ROUTE) {
                    MainScreen(
                        navigateToSettings = actions.navigateToSettings,
                    )
                }
                composable(NavDestinations.SETTINGS_ROUTE) {
                    SettingsScreen(
                        navigateUp = actions.navigateUp
                    )
                }
            }
        }
    }
}
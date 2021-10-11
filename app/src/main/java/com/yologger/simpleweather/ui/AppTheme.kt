package com.yologger.simpleweather.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.yologger.simpleweather.ui.screen.home.HomeScreen
import com.yologger.simpleweather.ui.screen.settings.SettingsScreen
import com.yologger.simpleweather.ui.theme.SimpleWeatherTheme

object NavDestinations {
    const val HOME_ROUTE = "home"
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
fun AppTheme (
) {
    SimpleWeatherTheme {
        val scaffoldState = rememberScaffoldState()
        val navController = rememberNavController()
        val actions = remember(navController) { NavActions(navController) }

        Scaffold(
            scaffoldState = scaffoldState,
        ) {

            NavHost(navController = navController, startDestination = NavDestinations.HOME_ROUTE) {
                composable(NavDestinations.HOME_ROUTE) {
                    HomeScreen(
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
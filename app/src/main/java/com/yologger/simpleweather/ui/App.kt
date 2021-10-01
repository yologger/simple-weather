package com.yologger.simpleweather.ui

import android.content.Context
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yologger.simpleweather.ui.screen.main.MainScreen
import com.yologger.simpleweather.ui.screen.main.MainViewModel
import com.yologger.simpleweather.ui.screen.settings.SettingsScreen
import com.yologger.simpleweather.ui.screen.settings.SettingsViewModel
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

@Composable
fun App(applicationContext: Context) {
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
                    val mainViewModel: MainViewModel = viewModel()
                    MainScreen(
                        viewModel = mainViewModel,
                        navigateToSettings = actions.navigateToSettings
                    )
                }
                composable(NavDestinations.SETTINGS_ROUTE) {
                    val settingsViewModel: SettingsViewModel = viewModel()
                    SettingsScreen(
                        viewModel = settingsViewModel,
                        navigateUp = actions.navigateUp
                    )
                }
            }
        }
    }
}
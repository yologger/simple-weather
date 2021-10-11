package com.yologger.simpleweather.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun HomeScreenContent(
    viewModel: HomeViewModel,
    navigateToSettings: () -> Unit
) {
    val isLoading: Boolean by viewModel.liveIsLoading.observeAsState(false)

    if (isLoading) {
        HomeScreenContentLoading()
    } else {
        HomeScreenContentMain(navigateToSettings = navigateToSettings, viewModel = viewModel)
    }
}
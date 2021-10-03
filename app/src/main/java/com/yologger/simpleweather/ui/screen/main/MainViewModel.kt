package com.yologger.simpleweather.ui.screen.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yologger.simpleweather.ui.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    fun test() {
        Log.d("KKK", "test() from MainViewModel")
        weatherRepository.test()
    }

    fun getCurrentWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            var result = weatherRepository.getCurrentWeatherData()
            Log.d("KKK", "result: ${result}")
        }
    }
}
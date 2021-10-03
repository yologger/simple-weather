package com.yologger.simpleweather.data.repository

import android.util.Log
import com.yologger.simpleweather.data.datasource.api.WeatherData
import com.yologger.simpleweather.data.datasource.api.WeatherService
import com.yologger.simpleweather.ui.repository.WeatherRepository
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
): WeatherRepository {
    override fun test() {
        Log.d("KKK", "test() from WeatherRepositoryImpl")
    }

    override suspend fun getCurrentWeatherData(): WeatherData {
        return weatherService.getCurrentWeatherData("37.4741083", "126.8901586", "3597a17c03a0154e4839653cb053d0bd")
    }
}
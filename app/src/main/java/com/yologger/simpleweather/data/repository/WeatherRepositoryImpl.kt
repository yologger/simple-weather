package com.yologger.simpleweather.data.repository

import android.util.Log
import com.yologger.simpleweather.data.datasource.api.WeatherData
import com.yologger.simpleweather.data.datasource.api.WeatherService
import com.yologger.simpleweather.ui.repository.WeatherRepository
import com.yologger.simpleweather.util.LocationHelper
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val locationHelper: LocationHelper
): WeatherRepository {

    override suspend fun getCurrentWeatherData(): WeatherData {
        val location = locationHelper.getCurrentLocation()
        val latitude = location!!.latitude
        val longitude = location!!.longitude
        return weatherService.getCurrentWeatherData("${latitude}", "${longitude}", "3597a17c03a0154e4839653cb053d0bd")
    }
}
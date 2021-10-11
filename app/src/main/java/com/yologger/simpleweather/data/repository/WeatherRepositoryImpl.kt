package com.yologger.simpleweather.data.repository

import com.yologger.simpleweather.app.Constants
import com.yologger.simpleweather.data.datasource.api.WeatherData
import com.yologger.simpleweather.data.datasource.api.WeatherService
import com.yologger.simpleweather.data.util.LocationUtil
import com.yologger.simpleweather.ui.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val locationUtil: LocationUtil
): WeatherRepository {

    override suspend fun getCurrentWeatherData(): WeatherData? {
        val location = locationUtil.getLocation()
        location?.let {
            val latitude = it.latitude
            val longitude = it.longitude
            return weatherService.getCurrentWeatherData(latitude = latitude, longitude = longitude, appid = Constants.APP_ID)
        } ?: run {
            return null
        }
    }
}
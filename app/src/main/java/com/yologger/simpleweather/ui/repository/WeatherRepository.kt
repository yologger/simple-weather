package com.yologger.simpleweather.ui.repository

import com.yologger.simpleweather.data.datasource.api.WeatherData
import okhttp3.ResponseBody
import retrofit2.Response

interface WeatherRepository {
    fun test()
    suspend fun getCurrentWeatherData(): WeatherData
}
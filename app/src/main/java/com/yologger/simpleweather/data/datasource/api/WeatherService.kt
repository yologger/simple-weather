package com.yologger.simpleweather.data.datasource.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather")
    suspend fun getCurrentWeatherData(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") appid: String,
        @Query("units") units: String = "metric"
    ): WeatherData
}
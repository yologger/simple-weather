package com.yologger.simpleweather.data.datasource.api


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET
    fun getCurrentWeatherData(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") appid: String
    ): Call<ResponseBody>
}
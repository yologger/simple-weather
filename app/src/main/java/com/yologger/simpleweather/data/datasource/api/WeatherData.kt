package com.yologger.simpleweather.data.datasource.api

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("name") val name: String,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("coord") val coordinator: Coordinator,
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("wind") val wind: Wind,
) {
    data class Coordinator(
        @SerializedName("lon") val longitude: Double,
        @SerializedName("lat") val latitude: Double,
    )

    data class Main(
        @SerializedName("temp") val temperature: Double,
        @SerializedName("feels_like") val feelsLike: Double,
        @SerializedName("temp_min") val minTemperature: Double,
        @SerializedName("temp_max") val maxTemperature: Double,
        @SerializedName("pressure") val pressure: Int,
        @SerializedName("humidity") val humidity: Int,
    )

    data class Weather(
        @SerializedName("main") val main: String,
        @SerializedName("description") val description: String,
        @SerializedName("icon") val icon: String
    )

    data class Wind(
        @SerializedName("speed") val speed: Double,
        @SerializedName("deg") val deg: Int,
    )
}

package com.yologger.simpleweather.data.repository

import android.util.Log
import com.yologger.simpleweather.ui.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(

): WeatherRepository {
    override fun test() {
        Log.d("KKK", "test() from WeatherRepositoryImpl")
    }
}
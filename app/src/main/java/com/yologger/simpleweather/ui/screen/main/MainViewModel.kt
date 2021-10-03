package com.yologger.simpleweather.ui.screen.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yologger.simpleweather.ui.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _liveLocation = MutableLiveData<String>().apply { value = "" }
    val liveLocation = _liveLocation

    private val _liveIsLoading = MutableLiveData<Boolean>().apply { value = false }
    val liveIsLoading = _liveIsLoading

    private val _liveTemperature = MutableLiveData<Double>().apply { value = 0.0 }
    val liveTemperature = _liveTemperature

    private val _liveWeather = MutableLiveData<String>().apply { value = "" }
    val liveWeather = _liveWeather

    private val _liveCurrentTime = MutableLiveData<String>().apply { value = "" }
    val liveCurrentTime = _liveCurrentTime

    private val _liveIcon = MutableLiveData<String>().apply { value = "" }
    val liveIcon = _liveIcon

    init {

        getCurrentWeather()
    }

    private fun getCurrentWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            _liveIsLoading.postValue(true)
            var result = weatherRepository.getCurrentWeatherData()
            _liveTemperature.postValue(result.main.temperature)
            _liveWeather.postValue(result.weather[0].main)
            _liveLocation.postValue(result.name)
            _liveCurrentTime.postValue(getCurrentTime())
            _liveIcon.postValue(result.weather[0].icon)
            _liveIsLoading.postValue(false)
        }
    }

    fun refreshWeather() {
        getCurrentWeather()
    }

    fun getCurrentTime(): String {
        val curTime = Date().time
        val format = SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss a", Locale.US)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return format.format(curTime)
    }
}
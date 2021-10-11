package com.yologger.simpleweather.ui.screen.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yologger.simpleweather.ui.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _liveIsLoading = MutableLiveData<Boolean>().apply { value = false }
    val liveIsLoading = _liveIsLoading

    private val _liveLocation = MutableLiveData<String>().apply { value = "" }
    val liveLocation = _liveLocation

    private val _liveTemperature = MutableLiveData<Double>().apply { value = 0.0 }
    val liveTemperature = _liveTemperature

    private val _liveWeather = MutableLiveData<String>().apply { value = "" }
    val liveWeather = _liveWeather

    private val _liveCurrentTime = MutableLiveData<String>().apply { value = "" }
    val liveCurrentTime = _liveCurrentTime

    private val _liveCurrentDate = MutableLiveData<String>().apply { value = "" }
    val liveCurrentDate = _liveCurrentDate

    private val _liveIcon = MutableLiveData<String>().apply { value = "" }
    val liveIcon = _liveIcon

    init {
        fetchCurrentWeather()
    }

    private fun fetchCurrentWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            _liveIsLoading.postValue(true)
            var data = weatherRepository.getCurrentWeatherData()
            data?.let {
                _liveTemperature.postValue(it.main.temperature)
                _liveWeather.postValue(it.weather[0].main)
                _liveLocation.postValue(it.name)
                _liveCurrentTime.postValue(getCurrentTime())
                _liveCurrentDate.postValue(getCurrentDate())
                _liveIcon.postValue(it.weather[0].icon)

            }
            _liveIsLoading.postValue(false)
        }
    }

    fun refreshWeather() {
        fetchCurrentWeather()
    }

    private fun getCurrentTime(): String {
        val curTime = Date().time
        // val format = SimpleDateFormat("EEE d MMM yyyy hh:mm:ss a", Locale.US)
        val format = SimpleDateFormat("K:mm a", Locale.US)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return format.format(curTime)
    }

    private fun getCurrentDate(): String {
        val curTime = Date().time
        val format = SimpleDateFormat("EEE d MMM yyyy", Locale.US)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return format.format(curTime)

    }
}
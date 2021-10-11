package com.yologger.simpleweather.data.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import javax.inject.Inject

class LocationUtil @Inject constructor(
    private val context: Context
) {
    fun getLocation(): Location? {
        val hasFineLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED || hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
            return null
        } else {
            return null
        }
    }
}
package com.yologger.simpleweather.ui

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.gun0912.tedpermission.coroutine.TedPermission
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        lifecycleScope.launch {
            val result = TedPermission.create()
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .check()

            if (result.isGranted) {
                navigateToApp()
            } else {
                handleRejected()
            }
        }
    }

    private fun navigateToApp() {
        val nextIntent = Intent(this@SplashActivity, AppActivity::class.java)
        startActivity(nextIntent)
        finish()
    }

    private fun handleRejected() {
        val builder = AlertDialog.Builder(this@SplashActivity)

        val alertDialog = builder
            .setMessage("This app requires location permission.")
            .setPositiveButton("OK") { _, _ -> finish() }
            .create()

        alertDialog.show()
    }
}
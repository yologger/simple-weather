package com.yologger.simpleweather.ui.screen.home

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun HomeScreenPermissionDialog(
    onSettingClick: () -> Unit,
    onDenyClick: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text(text = "Location Permission") },
            text = { Text("This application requires access to you location") },
            confirmButton = {
                Button(onClick = {
                    openDialog.value = false
                    onSettingClick()
                }) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        onDenyClick()
                    }) {
                    Text("Deny")
                }
            }
        )
    }
}
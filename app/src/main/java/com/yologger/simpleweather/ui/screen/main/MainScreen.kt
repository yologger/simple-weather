package com.yologger.simpleweather.ui.screen.main

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.yologger.simpleweather.ui.theme.Purple200
import com.yologger.simpleweather.R

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navigateToSettings: () -> Unit
) {
    viewModel.test()
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = { Text("TopAppBar") },
                backgroundColor = Color.Transparent,
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Filled.Refresh, null)
                    }
                    IconButton(onClick = { navigateToSettings() }) {
                        Icon(Icons.Filled.Settings, null)
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFF00063D))
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("금천구 가산동", color = Color.White, style = Typography().h5)
                Text("수요일 9월 29일, 09:04 오후", color = Color.White, style = Typography().body1)
                Spacer(modifier = Modifier.size(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.weather),
                    contentDescription = "",
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.size(30.dp))
                Text("19°C", color = Color.White, style = Typography().h2)
                Text("맑음", color = Color.White, style = Typography().body1)
            }
        }
    }
}
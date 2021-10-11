package com.yologger.simpleweather.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeScreenContentMain(
    viewModel: HomeViewModel,
    navigateToSettings: () -> Unit
) {
    val temperature: Double by viewModel.liveTemperature.observeAsState(0.0)
    val location: String by viewModel.liveLocation.observeAsState(initial = "")
    val currentTime: String by viewModel.liveCurrentTime.observeAsState(initial = "")
    val currentDate: String by viewModel.liveCurrentDate.observeAsState(initial = "")
    val icon: String by viewModel.liveIcon.observeAsState(initial = "")
    val weather: String by viewModel.liveWeather.observeAsState(initial = "")

    Box(
        modifier = Modifier
            .background(Color(0xFF00063D))
            .fillMaxSize()
    ) {
        IconButton(
            onClick = { navigateToSettings() },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(Icons.Outlined.Settings, null, tint = Color.White)
        }
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(location, color = Color.White, style = Typography().h4)
            Spacer(modifier = Modifier.size(10.dp))
            Text(currentDate, color = Color.White, style = Typography().body1)
            Spacer(modifier = Modifier.size(10.dp))
            Text(currentTime, color = Color.White, style = Typography().h5)
            Spacer(modifier = Modifier.size(20.dp))

            if (icon.isNullOrBlank()) {
//                    Image(
//                        painter = painterResource(id = R.drawable.weather),
//                        contentDescription = "",
//                        modifier = Modifier
//                            .width(150.dp)
//                            .height(150.dp)
//                    )
            } else {
                val imageUrl = "http://openweathermap.org/img/wn/${icon}@2x.png";
                GlideImage(
                    imageModel = imageUrl,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                    // Crop, Fit, Inside, FillHeight, FillWidth, None
                    contentScale = ContentScale.Crop,
                    // shows an image with a circular revealed animation.
                    circularReveal = CircularReveal(duration = 250),
                    // shows a placeholder ImageBitmap when loading.
                    // placeHolder = ImageBitmap.imageResource(R.drawable.placeholder),
                    // shows an error ImageBitmap when the request failed.
                    // error = ImageBitmap.imageResource(R.drawable.error)
                )
            }

            Spacer(modifier = Modifier.size(20.dp))
            Text("${temperature}°", color = Color.White, style = Typography().h2)
            Text("${weather}", color = Color.White, style = Typography().body1)
            Row() {
            }
        }
    }
}
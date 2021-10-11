package com.yologger.simpleweather.ui.screen.home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.yologger.simpleweather.R
import com.yologger.simpleweather.ui.theme.Teal200
import com.yologger.simpleweather.ui.theme.Teal300

@Composable
fun HomeScreenContentMain(
    viewModel: HomeViewModel,
    navigateToSettings: () -> Unit
) {
    Box(
        modifier = Modifier
            // .background(Color(0xFF00063D))
            .fillMaxSize()
    ) {
        IconButton(
            onClick = { navigateToSettings() },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(Icons.Outlined.Settings, null)
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeScreenTop(viewModel = viewModel)
            Spacer(modifier = Modifier.size(40.dp))
            HomeScreenBottom(viewModel = viewModel)
        }
    }
}

@Composable
fun HomeScreenTop(
    viewModel: HomeViewModel
) {
    val location: String by viewModel.liveLocation.observeAsState(initial = "")
    val currentTime: String by viewModel.liveCurrentTime.observeAsState(initial = "")
    val currentDate: String by viewModel.liveCurrentDate.observeAsState(initial = "")
    val temperature: Double by viewModel.liveTemperature.observeAsState(0.0)
    val weather: String by viewModel.liveWeather.observeAsState(initial = "")
    val icon: String by viewModel.liveIcon.observeAsState(initial = "")

    Text(location, style = Typography().h5)
    Spacer(modifier = Modifier.size(10.dp))
    Text(currentDate, style = Typography().body1)
    Spacer(modifier = Modifier.size(10.dp))
    Text(currentTime, style = Typography().h5)
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
            modifier = Modifier.width(50.dp).height(50.dp),
            contentScale = ContentScale.Crop,
            circularReveal = CircularReveal(duration = 250),
        )
    }
    Spacer(modifier = Modifier.size(20.dp))
    Text("$temperature°", style = Typography().h2)
    Text("$weather", style = Typography().body1)
}

@Composable
fun HomeScreenBottom(
    viewModel: HomeViewModel
) {
    val humidity: String by viewModel.liveHumidity.observeAsState(initial = "")
    val pressure: String by viewModel.livePressure.observeAsState(initial = "")
    val wind: String by viewModel.liveWind.observeAsState(initial = "")

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.ic_humidity), contentDescription = "", colorFilter = ColorFilter.tint(Teal200))
            Spacer(modifier = Modifier.size(15.dp))
            Text(text = "Humidity")
            Text(text = "$humidity%")
        }
        Spacer(modifier = Modifier.size(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.ic_wind), contentDescription = "", colorFilter = ColorFilter.tint(Teal200))
            Spacer(modifier = Modifier.size(15.dp))
            Text(text = "Wind")
            Text(text = "${wind}m/s")
        }
        Spacer(modifier = Modifier.size(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.ic_pressure), contentDescription = "", colorFilter = ColorFilter.tint(Teal200))
            Spacer(modifier = Modifier.size(15.dp))
            Text(text = "Pressure")
            Text(text = "${pressure}hPa")
        }
    }
}
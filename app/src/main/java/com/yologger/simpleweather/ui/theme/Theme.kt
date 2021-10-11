package com.yologger.simpleweather.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Teal200,
    primaryVariant = Teal300,
    secondary = Teal200,
    onPrimary = Black900,
    onSecondary = Black900
)

private val LightColorPalette = lightColors(
    primary = Teal200,
    primaryVariant = Teal300,
    secondary = Teal200,
    onPrimary = Black900,
    onSecondary = Black900
)

@Composable
fun SimpleWeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
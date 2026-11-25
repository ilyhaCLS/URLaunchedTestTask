package com.youarelaunched.challenge.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    background = Colors.BgPage,
    surface = Colors.BgPage,
    onPrimary = Colors.BgPage,
    onSecondary = Colors.BgPage,
)

@Composable
fun TaskTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
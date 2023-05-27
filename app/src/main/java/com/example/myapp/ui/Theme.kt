package com.example.myapp.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable

@Composable
fun MyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (isDarkThemeEnabled()) DarkThemeColors else LightThemeColors,
        typography = Typography(),
        content = content
    )
}

private fun isDarkThemeEnabled(): Boolean {
    return false
}
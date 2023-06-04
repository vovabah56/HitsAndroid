package com.example.myapp.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable

@Composable
fun MyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightThemeColors,
        content = content
    )
}

private fun isDarkThemeEnabled(): Boolean {
    return false
}
package com.example.zipcart.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = ZipGreen,
    onPrimary = Color.White,
    secondary = ZipGreenDark,
    background = ZipBackground,
    onBackground = ZipTextPrimary,
    surface = ZipSurface,
    onSurface = ZipTextPrimary,
    surfaceVariant = Color(0xFFF0F2EC),
    onSurfaceVariant = ZipTextSecondary,
    error = ZipError
)

@Composable
fun ZipCartTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = ZipTypography,
        shapes = Shapes(
            small = RoundedCornerShape(12.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(24.dp)
        ),
        content = content
    )
}

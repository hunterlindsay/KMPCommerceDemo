package com.hunterlindsay.kmpcommercedemo.android.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

private val AppColorScheme = darkColorScheme(
    primary = CommerceLightBlue,
    secondary = CommerceMutedBlue,
    background = CommerceDarkBlue,
    surface = CommerceSurfaceBlue,
    surfaceVariant = CommerceElevatedBlue,
    error = CommerceErrorRed,

    onPrimary = CommerceWhite,
    onSecondary = CommerceDarkBlue,
    onBackground = CommerceWhite,
    onSurface = CommerceWhite,
    onSurfaceVariant = CommerceWhite,
    onError = CommerceDarkBlue
)

@Composable
fun KMPCommerceDemoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = AppTypography,
        content = content
    )
}
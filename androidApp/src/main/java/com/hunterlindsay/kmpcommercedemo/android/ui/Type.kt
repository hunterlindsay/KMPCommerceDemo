package com.hunterlindsay.kmpcommercedemo.android.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.hunterlindsay.kmpcommercedemo.android.R

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

val SoraFontFamily = FontFamily(
    Font(R.font.sora)
)

private val DefaultTypography = Typography()

val AppTypography = Typography(
    displayLarge = DefaultTypography.displayLarge.withSora(),
    displayMedium = DefaultTypography.displayMedium.withSora(),
    displaySmall = DefaultTypography.displaySmall.withSora(),
    headlineLarge = DefaultTypography.headlineLarge.withSora(),
    headlineMedium = DefaultTypography.headlineMedium.withSora(),
    headlineSmall = DefaultTypography.headlineSmall.withSora(),
    titleLarge = DefaultTypography.titleLarge.withSora(),
    titleMedium = DefaultTypography.titleMedium.withSora(),
    titleSmall = DefaultTypography.titleSmall.withSora(),
    bodyLarge = DefaultTypography.bodyLarge.withSora(),
    bodyMedium = DefaultTypography.bodyMedium.withSora(),
    bodySmall = DefaultTypography.bodySmall.withSora(),
    labelLarge = DefaultTypography.labelLarge.withSora(),
    labelMedium = DefaultTypography.labelMedium.withSora(),
    labelSmall = DefaultTypography.labelSmall.withSora()
)

private fun TextStyle.withSora(): TextStyle {
    return copy(fontFamily = SoraFontFamily)
}
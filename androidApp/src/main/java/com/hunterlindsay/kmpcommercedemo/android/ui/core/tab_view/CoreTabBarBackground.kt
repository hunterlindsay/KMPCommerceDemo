package com.hunterlindsay.kmpcommercedemo.android.ui.core.tab_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun CoreTabBarBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(340.dp)
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.00f to Color.Transparent,
                        0.20f to Color.Transparent,
                        0.42f to CommerceDarkBlue.copy(alpha = 0.30f),
                        0.62f to CommerceDarkBlue.copy(alpha = 0.62f),
                        0.82f to CommerceDarkBlue.copy(alpha = 0.88f),
                        1.00f to Color.Black.copy(alpha = 0.98f)
                    )
                )
            )
    )
}
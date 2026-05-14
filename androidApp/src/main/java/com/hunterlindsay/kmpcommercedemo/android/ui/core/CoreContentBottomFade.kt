package com.hunterlindsay.kmpcommercedemo.android.ui.core

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
fun CoreContentBottomFade(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.00f to Color.Transparent,
                        0.18f to CommerceDarkBlue.copy(alpha = 0.28f),
                        0.34f to CommerceDarkBlue.copy(alpha = 0.62f),
                        0.52f to CommerceDarkBlue.copy(alpha = 0.86f),
                        0.68f to CommerceDarkBlue.copy(alpha = 0.96f),
                        1.00f to CommerceDarkBlue
                    )
                )
            )
    )
}
package com.hunterlindsay.kmpcommercedemo.android.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun CoreScreenVignette(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colorStops = arrayOf(
                            0.00f to Color.Black.copy(alpha = 0.38f),
                            0.08f to Color.Black.copy(alpha = 0.16f),
                            0.18f to Color.Transparent,
                            0.82f to Color.Transparent,
                            0.92f to Color.Black.copy(alpha = 0.16f),
                            1.00f to Color.Black.copy(alpha = 0.38f)
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.00f to Color.Black.copy(alpha = 0.24f),
                            0.08f to Color.Black.copy(alpha = 0.10f),
                            0.18f to Color.Transparent,

                            0.36f to Color.Transparent,
                            0.58f to Color.Black.copy(alpha = 0.12f),
                            0.76f to Color.Black.copy(alpha = 0.28f),
                            0.90f to Color.Black.copy(alpha = 0.42f),
                            1.00f to Color.Black.copy(alpha = 0.56f)
                        )
                    )
                )
        )
    }
}
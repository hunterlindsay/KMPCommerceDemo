package com.hunterlindsay.kmpcommercedemo.android.ui.core.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue

/**
 * Created by Hunter Lindsay on 13/05/2026.
 */

@Composable
fun CoreContentTopFade(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.00f to CommerceDarkBlue,
                        0.62f to CommerceDarkBlue.copy(alpha = 0.94f),
                        0.82f to CommerceDarkBlue.copy(alpha = 0.58f),
                        1.00f to CommerceDarkBlue.copy(alpha = 0f)
                    )
                )
            )
    )
}
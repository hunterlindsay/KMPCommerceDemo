package com.hunterlindsay.kmpcommercedemo.android.ui.core.transition_supporting_views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun StartShoppingButtonMorphTransition(
    modifier: Modifier = Modifier,
    sourceBounds: Rect,
    targetBounds: Rect,
    progress: Float
) {
    val density = LocalDensity.current

    val leftPx = lerp(
        start = sourceBounds.left,
        end = targetBounds.left,
        progress = progress
    )

    val topPx = lerp(
        start = sourceBounds.top,
        end = targetBounds.top,
        progress = progress
    )

    val widthPx = lerp(
        start = sourceBounds.width,
        end = targetBounds.width,
        progress = progress
    )

    val heightPx = lerp(
        start = sourceBounds.height,
        end = targetBounds.height,
        progress = progress
    )

    val startTextAlpha = ((0.78f - progress) / 0.20f).coerceIn(0f, 1f)
    val browseTextAlpha = ((progress - 0.62f) / 0.22f).coerceIn(0f, 1f)

    val textSize = lerp(
        start = 24f,
        end = 14f,
        progress = progress
    )

    val shape = RoundedCornerShape(999.dp)

    Box(
        modifier = modifier
            .offset {
                IntOffset(
                    x = leftPx.roundToInt(),
                    y = topPx.roundToInt()
                )
            }
            .size(
                width = with(density) {
                    widthPx.toDp()
                },
                height = with(density) {
                    heightPx.toDp()
                }
            )
            .clip(shape)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = shape
            )
            .clickable(onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .offset(y = (-1).dp)
                .alpha(startTextAlpha),
            text = "Start Shopping!",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Black,
            fontSize = textSize.sp,
            lineHeight = textSize.sp,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Text(
            modifier = Modifier
                .offset(y = (-1).dp)
                .alpha(browseTextAlpha),
            text = "Filter",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Black,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

private fun lerp(
    start: Float,
    end: Float,
    progress: Float
): Float {
    return start + ((end - start) * progress)
}
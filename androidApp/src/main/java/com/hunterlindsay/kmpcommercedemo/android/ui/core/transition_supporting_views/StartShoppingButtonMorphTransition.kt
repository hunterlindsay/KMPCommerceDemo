package com.hunterlindsay.kmpcommercedemo.android.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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

    val backgroundAlpha = ((0.70f - progress) / 0.36f).coerceIn(0f, 1f)
    val startTextAlpha = ((0.52f - progress) / 0.24f).coerceIn(0f, 1f)
    val browseTextAlpha = ((progress - 0.26f) / 0.34f).coerceIn(0f, 1f)

    val browseTextSize = lerp(
        start = 24f,
        end = 38f,
        progress = progress
    )

    val browseLetterSpacing = lerp(
        start = 0f,
        end = 1.5f,
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
                color = MaterialTheme.colorScheme.primary.copy(alpha = backgroundAlpha),
                shape = shape
            )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .offset(y = (-1).dp)
                    .alpha(startTextAlpha),
                text = "Start Shopping!",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                fontSize = 24.sp,
                lineHeight = 24.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                modifier = Modifier.alpha(browseTextAlpha),
                text = "BROWSE",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Black,
                fontSize = browseTextSize.sp,
                lineHeight = browseTextSize.sp,
                letterSpacing = browseLetterSpacing.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

private fun lerp(
    start: Float,
    end: Float,
    progress: Float
): Float {
    return start + ((end - start) * progress)
}
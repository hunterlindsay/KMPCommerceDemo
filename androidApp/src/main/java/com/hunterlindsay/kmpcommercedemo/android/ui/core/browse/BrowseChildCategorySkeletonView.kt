package com.hunterlindsay.kmpcommercedemo.android.ui.browse

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceWhite
import kotlin.math.abs

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun BrowseChildCategorySkeletonView(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(
        label = "BrowseChildCategorySkeletonTransition"
    )

    val shimmerProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 950,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "BrowseChildCategorySkeletonProgress"
    )

    val textWidths = listOf(
        92.dp,
        138.dp,
        116.dp
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        textWidths.forEachIndexed { index, width ->
            BrowseChildCategorySkeletonPillView(
                textWidth = width,
                index = index,
                shimmerProgress = shimmerProgress
            )
        }
    }
}

@Composable
private fun BrowseChildCategorySkeletonPillView(
    textWidth: Dp,
    index: Int,
    shimmerProgress: Float
) {
    val highlightPosition = shimmerProgress * 4f
    val distanceFromHighlight = abs(highlightPosition - index)

    val pillAlpha = when {
        distanceFromHighlight < 0.35f -> 0.82f
        distanceFromHighlight < 0.85f -> 0.58f
        else -> 0.32f
    }

    val textAlpha = when {
        distanceFromHighlight < 0.35f -> 0.90f
        distanceFromHighlight < 0.85f -> 0.64f
        else -> 0.40f
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(
                color = CommerceWhite.copy(alpha = pillAlpha),
                shape = RoundedCornerShape(999.dp)
            )
            .padding(horizontal = 18.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .offset(y = (-1).dp)
                .width(textWidth)
                .height(13.dp)
                .background(
                    color = CommerceWhite.copy(alpha = textAlpha),
                    shape = RoundedCornerShape(999.dp)
                )
        )
    }
}
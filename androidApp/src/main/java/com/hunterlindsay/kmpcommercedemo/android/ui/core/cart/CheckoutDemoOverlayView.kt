package com.hunterlindsay.kmpcommercedemo.android.ui.core

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceWhite
import com.hunterlindsay.kmpcommercedemo.android.ui.RotatingDemoView

/**
 * Created by Hunter Lindsay on 14/05/2026.
 */

@Composable
fun CheckoutDemoOverlayView(
    sourceBounds: Rect,
    modifier: Modifier = Modifier,
    onDismissed: () -> Unit
) {
    val density = LocalDensity.current

    val openProgress = remember {
        Animatable(0f)
    }

    val rootWidthPx = remember {
        mutableFloatStateOf(0f)
    }

    val rootHeightPx = remember {
        mutableFloatStateOf(0f)
    }

    LaunchedEffect(Unit) {
        openProgress.snapTo(0f)
        openProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 360)
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                rootWidthPx.floatValue = coordinates.size.width.toFloat()
                rootHeightPx.floatValue = coordinates.size.height.toFloat()
            }
    ) {
        if (rootWidthPx.floatValue <= 0f || rootHeightPx.floatValue <= 0f) {
            return@Box
        }

        val openValue = smoothStep(openProgress.value)

        val horizontalMarginPx = with(density) {
            28.dp.toPx()
        }

        val targetWidthPx = rootWidthPx.floatValue - (horizontalMarginPx * 2f)
        val targetHeightPx = with(density) {
            486.dp.toPx()
        }

        val targetLeftPx = horizontalMarginPx
        val targetTopPx = (rootHeightPx.floatValue - targetHeightPx) / 2f

        val currentLeftPx = lerp(
            start = sourceBounds.left,
            end = targetLeftPx,
            progress = openValue
        )

        val currentTopPx = lerp(
            start = sourceBounds.top,
            end = targetTopPx,
            progress = openValue
        )

        val currentWidthPx = lerp(
            start = sourceBounds.width,
            end = targetWidthPx,
            progress = openValue
        )

        val currentHeightPx = lerp(
            start = sourceBounds.height,
            end = targetHeightPx,
            progress = openValue
        )

        val outerCornerRadius = lerp(
            start = 999f,
            end = 44f,
            progress = openValue
        ).dp

        val innerCornerRadius = lerp(
            start = 999f,
            end = 30f,
            progress = openValue
        ).dp

        val outlineBandPadding = 46.dp

        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(openProgress.value)
                .background(
                    color = CommerceDarkBlue.copy(alpha = 0.62f)
                )
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null,
                    onClick = onDismissed
                )
        )

        Box(
            modifier = Modifier
                .offset(
                    x = with(density) {
                        currentLeftPx.toDp()
                    },
                    y = with(density) {
                        currentTopPx.toDp()
                    }
                )
                .width(
                    width = with(density) {
                        currentWidthPx.toDp()
                    }
                )
                .height(
                    height = with(density) {
                        currentHeightPx.toDp()
                    }
                )
                .background(
                    color = CommerceDarkBlue,
                    shape = RoundedCornerShape(outerCornerRadius)
                )
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null
                ) {},
            contentAlignment = Alignment.Center
        ) {
            RotatingDemoView(
                text = "KOTLIN  MULTIPLATFORM  COMMERCE  DEMO",
                modifier = Modifier.fillMaxSize(),
                borderColor = CommerceDarkBlue,
                textColor = CommerceWhite,
                borderWidth = 18.dp,
                textPathBorderWidth = 44.dp,
                cornerRadius = outerCornerRadius,
                fontSize = 16.sp,
                minimumTextGroupGap = 220.dp,
                animationDurationMillis = 30000
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(outlineBandPadding)
                    .background(
                        color = CommerceWhite,
                        shape = RoundedCornerShape(innerCornerRadius)
                    )
                    .padding(
                        start = 30.dp,
                        top = 34.dp,
                        end = 30.dp,
                        bottom = 30.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(openProgress.value),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "THANKS FOR CHECKING OUT THIS DEMO",
                        modifier = Modifier.width(220.dp),
                        fontWeight = FontWeight.Black,
                        fontSize = 21.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.35.sp,
                        textAlign = TextAlign.Center,
                        color = CommerceDarkBlue
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Built over a focused four-day sprint with Kotlin Multiplatform, shared business logic, real live API data, favourite-item persistence, async and resilient data retrieval, and a simple service-based architecture with minimal interconnect for easier testing.",
                        modifier = Modifier.width(236.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.5.sp,
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Center,
                        color = CommerceDarkBlue.copy(alpha = 0.72f)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Ready for a SwiftUI iOS frontend to be added on top of the shared KMP core.",
                        modifier = Modifier.width(224.dp),
                        fontWeight = FontWeight.Black,
                        fontSize = 12.5.sp,
                        lineHeight = 17.sp,
                        textAlign = TextAlign.Center,
                        color = CommerceDarkBlue.copy(alpha = 0.86f)
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Hunter Lindsay",
                        modifier = Modifier.width(220.dp),
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Center,
                        color = CommerceDarkBlue
                    )
                }
            }
        }
    }
}

private fun smoothStep(progress: Float): Float {
    return progress * progress * (3f - (2f * progress))
}

private fun lerp(
    start: Float,
    end: Float,
    progress: Float
): Float {
    return start + ((end - start) * progress)
}
package com.hunterlindsay.kmpcommercedemo.android.ui.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.hunterlindsay.kmpcommercedemo.android.ui.core.ribbon.RibbonButtonView

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun RibbonView(
    modifier: Modifier = Modifier,
    firstButtonAlpha: Float = 1f,
    revealedTrailingButtonCount: Int = 2,
    onFirstButtonPositioned: (Rect) -> Unit = {},
    onFilterClicked: () -> Unit = {},
    onSortClicked: () -> Unit = {},
    onLayoutClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .horizontalScroll(rememberScrollState())
            .padding(
                PaddingValues(
                    start = 20.dp,
                    top = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                )
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        RibbonButtonView(
            modifier = Modifier
                .alpha(firstButtonAlpha)
                .onGloballyPositioned { coordinates ->
                    onFirstButtonPositioned(coordinates.boundsInRoot())
                },
            text = "Filter",
            isSelected = false,
            onClick = onFilterClicked
        )

        AnimatedRibbonButton(
            visible = revealedTrailingButtonCount >= 1
        ) {
            RibbonButtonView(
                text = "Sort",
                onClick = onSortClicked
            )
        }

        AnimatedRibbonButton(
            visible = revealedTrailingButtonCount >= 2
        ) {
            RibbonButtonView(
                text = "Grid",
                onClick = onLayoutClicked
            )
        }
    }
}

@Composable
private fun AnimatedRibbonButton(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 140)
        ) + slideInHorizontally(
            animationSpec = tween(durationMillis = 170),
            initialOffsetX = { fullWidth ->
                fullWidth + 30
            }
        )
    ) {
        content()
    }
}
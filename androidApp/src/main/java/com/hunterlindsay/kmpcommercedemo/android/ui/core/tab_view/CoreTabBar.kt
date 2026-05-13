package com.hunterlindsay.kmpcommercedemo.android.ui.core.tab_view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun CoreTabBar(
    selectedTab: CoreTab,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    onTabSelected: (CoreTab) -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 180)
        ) + slideInVertically(
            animationSpec = tween(durationMillis = 220),
            initialOffsetY = { fullHeight ->
                fullHeight / 2
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 30.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CoreTab.entries.forEach { tab ->
                CoreTabBarItem(
                    tab = tab,
                    isSelected = selectedTab == tab,
                    onClick = {
                        onTabSelected(tab)
                    }
                )
            }
        }
    }
}
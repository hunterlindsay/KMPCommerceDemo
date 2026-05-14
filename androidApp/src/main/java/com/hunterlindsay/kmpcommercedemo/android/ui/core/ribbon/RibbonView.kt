package com.hunterlindsay.kmpcommercedemo.android.ui.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.hunterlindsay.kmpcommercedemo.android.ui.core.browse.ProductSortFamily
import com.hunterlindsay.kmpcommercedemo.android.ui.core.sort.ProductSortMode
import com.hunterlindsay.kmpcommercedemo.android.ui.core.tab_view.CoreTab

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun RibbonView(
    selectedTab: CoreTab,
    selectedSortMode: ProductSortMode?,
    firstButtonAlpha: Float,
    revealedTrailingButtonCount: Int,
    modifier: Modifier = Modifier,
    onSortFamilySelected: (ProductSortFamily) -> Unit
) {
    val sortFamilies = when (selectedTab) {
        CoreTab.Browse -> {
            listOf(
                ProductSortFamily.Recent,
                ProductSortFamily.Name,
                ProductSortFamily.Price,
                ProductSortFamily.Rating
            )
        }

        CoreTab.Saved -> {
            listOf(
                ProductSortFamily.Recent,
                ProductSortFamily.Name,
                ProductSortFamily.Price,
                ProductSortFamily.Rating
            )
        }

        CoreTab.Cart -> {
            listOf(
                ProductSortFamily.Recent,
                ProductSortFamily.Name,
                ProductSortFamily.Price,
                ProductSortFamily.Quantity
            )
        }
    }

    AnimatedContent(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(
                start = 22.dp,
                top = 14.dp,
                end = 22.dp,
                bottom = 10.dp
            ),
        targetState = selectedTab,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(durationMillis = 170)
            ) togetherWith fadeOut(
                animationSpec = tween(durationMillis = 120)
            ) using SizeTransform(
                clip = false,
                sizeAnimationSpec = { _, _ ->
                    tween(durationMillis = 220)
                }
            )
        },
        label = "RibbonTabContent"
    ) { val it = it
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            sortFamilies.forEachIndexed { index, sortFamily ->
                val isSelected = selectedSortMode?.family == sortFamily
                val title = if (isSelected) {
                    selectedSortMode?.activeTitle ?: sortFamily.inactiveTitle
                } else {
                    sortFamily.inactiveTitle
                }

                AnimatedVisibility(
                    visible = index == 0 || index <= revealedTrailingButtonCount,
                    enter = fadeIn(
                        animationSpec = tween(durationMillis = 170)
                    ) + expandHorizontally(
                        animationSpec = tween(durationMillis = 220),
                        expandFrom = Alignment.Start
                    ),
                    exit = fadeOut(
                        animationSpec = tween(durationMillis = 120)
                    ) + shrinkHorizontally(
                        animationSpec = tween(durationMillis = 160),
                        shrinkTowards = Alignment.Start
                    )
                ) {
                    RibbonButtonView(
                        modifier = Modifier
                            .weight(1f)
                            .alpha(
                                alpha = if (index == 0) {
                                    firstButtonAlpha
                                } else {
                                    1f
                                }
                            ),
                        title = title,
                        isSelected = isSelected,
                        onClick = {
                            onSortFamilySelected(sortFamily)
                        }
                    )
                }
            }
        }
    }
}
package com.hunterlindsay.kmpcommercedemo.android.ui.core

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.zIndex
import com.hunterlindsay.kmpcommercedemo.android.ui.browse.BrowseView
import com.hunterlindsay.kmpcommercedemo.android.ui.core.browse.CoreContentTopFade
import com.hunterlindsay.kmpcommercedemo.android.ui.core.tab_view.CoreTab
import com.hunterlindsay.kmpcommercedemo.android.ui.core.tab_view.CoreTabBar
import com.hunterlindsay.kmpcommercedemo.android.ui.core.transition_supporting_views.IntroMatchedStartButtonMeasurementView
import com.hunterlindsay.kmpcommercedemo.android.ui.core.transition_supporting_views.StartShoppingButtonMorphTransition
import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun CoreView(
    productService: ProductService
) {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    var ribbonBottomPx by remember {
        mutableFloatStateOf(0f)
    }

    var tabBarHeightPx by remember {
        mutableFloatStateOf(0f)
    }

    var sourceButtonBounds by remember {
        mutableStateOf<Rect?>(null)
    }

    var targetButtonBounds by remember {
        mutableStateOf<Rect?>(null)
    }

    var lockedSourceBounds by remember {
        mutableStateOf<Rect?>(null)
    }

    var lockedTargetBounds by remember {
        mutableStateOf<Rect?>(null)
    }

    var showSourceButton by remember {
        mutableStateOf(true)
    }

    var showMorphingButton by remember {
        mutableStateOf(false)
    }

    var hasFinishedMorph by remember {
        mutableStateOf(false)
    }

    var revealedTrailingButtonCount by remember {
        mutableIntStateOf(0)
    }

    var revealedBrowseCategoryCount by remember {
        mutableIntStateOf(0)
    }

    var showBottomTabBar by remember {
        mutableStateOf(false)
    }

    var selectedTab by remember {
        mutableStateOf(CoreTab.Browse)
    }

    val morphProgress = remember {
        Animatable(0f)
    }

    val topOverlayHeight = with(density) {
        ribbonBottomPx.toDp()
    }

    val bottomOverlayHeight = with(density) {
        tabBarHeightPx.toDp()
    }

    LaunchedEffect(Unit) {
        snapshotFlow {
            sourceButtonBounds to targetButtonBounds
        }
            .filter { bounds ->
                val source = bounds.first
                val target = bounds.second

                source != null &&
                        target != null &&
                        source.width > 0f &&
                        source.height > 0f &&
                        target.width > 0f &&
                        target.height > 0f
            }
            .first()

        val source = sourceButtonBounds
        val target = targetButtonBounds

        if (source == null || target == null) {
            return@LaunchedEffect
        }

        lockedSourceBounds = source
        lockedTargetBounds = target

        delay(90)

        showSourceButton = false
        showMorphingButton = true

        morphProgress.snapTo(0f)

        morphProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 680)
        )

        showMorphingButton = false
        hasFinishedMorph = true

        delay(60)
        revealedTrailingButtonCount = 1
        revealedBrowseCategoryCount = 1

        delay(60)
        revealedBrowseCategoryCount = 2

        delay(30)
        revealedTrailingButtonCount = 2

        delay(60)
        revealedBrowseCategoryCount = 3

        delay(60)
        revealedBrowseCategoryCount = 4

        delay(60)
        revealedBrowseCategoryCount = 5

        delay(90)
        showBottomTabBar = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (selectedTab) {
            CoreTab.Browse -> {
                BrowseView(
                    productService = productService,
                    revealedCategoryCount = revealedBrowseCategoryCount,
                    topOverlayHeight = topOverlayHeight,
                    bottomOverlayHeight = bottomOverlayHeight,
                    modifier = Modifier.fillMaxSize(),
                    onCategoryExpanded = { categoryId ->
                        coroutineScope.launch {
                            productService.loadProductsForCategory(categoryId)
                        }
                    }
                )
            }

            CoreTab.Saved -> {
                // Saved view comes next.
            }

            CoreTab.Cart -> {
                // Cart view comes next.
            }
        }

        CoreScreenVignette(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
        )

        CoreContentBottomFade(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(3f)
        )

        RibbonView(
            modifier = Modifier
                .zIndex(4f)
                .onGloballyPositioned { coordinates ->
                    ribbonBottomPx = coordinates.boundsInRoot().bottom
                },
            firstButtonAlpha = if (hasFinishedMorph) {
                1f
            } else {
                0f
            },
            revealedTrailingButtonCount = revealedTrailingButtonCount,
            onFirstButtonPositioned = { bounds ->
                if (targetButtonBounds == null) {
                    targetButtonBounds = bounds
                }
            }
        )

        if (!hasFinishedMorph) {
            IntroMatchedStartButtonMeasurementView(
                sourceButtonAlpha = if (showSourceButton) {
                    1f
                } else {
                    0f
                },
                onStartButtonPositioned = { bounds ->
                    if (sourceButtonBounds == null) {
                        sourceButtonBounds = bounds
                    }
                }
            )
        }

        CoreTabBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(5f)
                .onGloballyPositioned { coordinates ->
                    tabBarHeightPx = coordinates.boundsInRoot().height
                },
            selectedTab = selectedTab,
            isVisible = showBottomTabBar,
            onTabSelected = { tab ->
                selectedTab = tab
            }
        )

        val source = lockedSourceBounds
        val target = lockedTargetBounds

        if (source != null && target != null && showMorphingButton) {
            StartShoppingButtonMorphTransition(
                modifier = Modifier.zIndex(10f),
                sourceBounds = source,
                targetBounds = target,
                progress = morphProgress.value
            )
        }
    }
}
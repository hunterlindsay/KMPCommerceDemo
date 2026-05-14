package com.hunterlindsay.kmpcommercedemo.android.ui.core

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.zIndex
import com.hunterlindsay.kmpcommercedemo.android.ui.browse.BrowseView
import com.hunterlindsay.kmpcommercedemo.android.ui.core.browse.SelectedProductPresentation
import com.hunterlindsay.kmpcommercedemo.android.ui.core.cart.CartView
import com.hunterlindsay.kmpcommercedemo.android.ui.core.saved.SavedProductsPersistence
import com.hunterlindsay.kmpcommercedemo.android.ui.core.sort.ProductSortMode
import com.hunterlindsay.kmpcommercedemo.android.ui.core.sort.nextModeForFamily
import com.hunterlindsay.kmpcommercedemo.android.ui.core.tab_view.CoreTab
import com.hunterlindsay.kmpcommercedemo.android.ui.core.tab_view.CoreTabBar
import com.hunterlindsay.kmpcommercedemo.android.ui.saved.SavedView
import com.hunterlindsay.kmpcommercedemo.concerns.products.Product
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
    productService: ProductService,
    initialStartButtonBounds: Rect?
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    val savedProductsPersistence = remember {
        SavedProductsPersistence(context)
    }

    val debugSlowAnimations = false

    val morphDurationMillis = if (debugSlowAnimations) {
        2600
    } else {
        620
    }

    val revealDelayMillis = if (debugSlowAnimations) {
        240L
    } else {
        55L
    }

    var ribbonBottomPx by remember {
        mutableFloatStateOf(0f)
    }

    var tabBarHeightPx by remember {
        mutableFloatStateOf(0f)
    }

    var browseTitleBounds by remember {
        mutableStateOf<Rect?>(null)
    }

    var lockedTargetBounds by remember {
        mutableStateOf<Rect?>(null)
    }

    var hasStartedMorph by remember {
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

    var selectedProductPresentation by remember {
        mutableStateOf<SelectedProductPresentation?>(null)
    }

    var selectedProductWasOpenedFromCart by remember {
        mutableStateOf(false)
    }

    var checkoutSourceBounds by remember {
        mutableStateOf<Rect?>(null)
    }

    var cartProducts by remember {
        mutableStateOf<List<Product>>(emptyList())
    }

    var savedProducts by remember {
        mutableStateOf<List<Product>>(emptyList())
    }

    var savedProductIds by remember {
        mutableStateOf<Set<Int>>(emptySet())
    }

    var hasLoadedSavedProductIds by remember {
        mutableStateOf(false)
    }

    var selectedBrowseSortMode by remember {
        mutableStateOf<ProductSortMode?>(null)
    }

    var selectedSavedSortMode by remember {
        mutableStateOf<ProductSortMode?>(null)
    }

    var selectedCartSortMode by remember {
        mutableStateOf<ProductSortMode?>(null)
    }

    var cartTabBounds by remember {
        mutableStateOf<Rect?>(null)
    }

    val productServiceState by productService.state.collectAsState()

    val displayedSavedProducts = remember(
        savedProducts,
        selectedSavedSortMode
    ) {
        savedProducts.sortedForProductSortMode(
            sortMode = selectedSavedSortMode
        )
    }

    val selectedRibbonSortMode = when (selectedTab) {
        CoreTab.Browse -> selectedBrowseSortMode
        CoreTab.Saved -> selectedSavedSortMode
        CoreTab.Cart -> selectedCartSortMode
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
        savedProductIds = savedProductsPersistence.loadSavedProductIds()
        hasLoadedSavedProductIds = true
    }

    LaunchedEffect(
        hasLoadedSavedProductIds,
        savedProductIds
    ) {
        if (hasLoadedSavedProductIds) {
            savedProductsPersistence.saveProductIds(savedProductIds)
        }
    }

    LaunchedEffect(
        hasLoadedSavedProductIds,
        savedProductIds,
        productServiceState.productsByCategoryId
    ) {
        if (!hasLoadedSavedProductIds || savedProductIds.isEmpty()) {
            return@LaunchedEffect
        }

        val loadedProducts = productServiceState.productsByCategoryId
            .values
            .flatten()

        val restoredProducts = loadedProducts.filter { product ->
            savedProductIds.contains(product.id)
        }

        if (restoredProducts.isNotEmpty()) {
            val existingProductIds = savedProducts.map { product ->
                product.id
            }.toSet()

            val productsToAdd = restoredProducts.filter { product ->
                !existingProductIds.contains(product.id)
            }

            if (productsToAdd.isNotEmpty()) {
                savedProducts = savedProducts + productsToAdd
            }
        }
    }

    LaunchedEffect(initialStartButtonBounds) {
        if (initialStartButtonBounds == null) {
            return@LaunchedEffect
        }

        snapshotFlow {
            Triple(
                browseTitleBounds,
                ribbonBottomPx,
                initialStartButtonBounds
            )
        }
            .filter { layoutState ->
                val titleBounds = layoutState.first
                val ribbonBottom = layoutState.second
                val sourceBounds = layoutState.third

                titleBounds != null &&
                        titleBounds.width > 0f &&
                        titleBounds.height > 0f &&
                        ribbonBottom > 0f &&
                        sourceBounds.width > 0f &&
                        sourceBounds.height > 0f
            }
            .first()

        delay(80)

        val target = browseTitleBounds ?: return@LaunchedEffect

        lockedTargetBounds = target
        morphProgress.snapTo(0f)
        hasStartedMorph = true

        morphProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = morphDurationMillis)
        )

        hasFinishedMorph = true

        delay(50)
        revealedTrailingButtonCount = 1
        revealedBrowseCategoryCount = 1

        delay(revealDelayMillis)
        revealedBrowseCategoryCount = 2

        delay(revealDelayMillis / 2)
        revealedTrailingButtonCount = 2

        delay(revealDelayMillis)
        revealedBrowseCategoryCount = 3

        delay(revealDelayMillis)
        revealedBrowseCategoryCount = 4

        delay(revealDelayMillis)
        revealedBrowseCategoryCount = 5

        delay(80)
        showBottomTabBar = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Crossfade(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f),
            targetState = selectedTab,
            animationSpec = tween(durationMillis = 220),
            label = "CoreTabContentCrossfade"
        ) { tab ->
            when (tab) {
                CoreTab.Browse -> {
                    BrowseView(
                        productService = productService,
                        revealedCategoryCount = revealedBrowseCategoryCount,
                        selectedSortMode = selectedBrowseSortMode,
                        topOverlayHeight = topOverlayHeight,
                        bottomOverlayHeight = bottomOverlayHeight,
                        browseTitleAlpha = if (hasFinishedMorph) {
                            1f
                        } else {
                            0f
                        },
                        modifier = Modifier.fillMaxSize(),
                        onBrowseTitlePositioned = { bounds ->
                            browseTitleBounds = bounds
                        },
                        onCategoryExpanded = { categoryId ->
                            coroutineScope.launch {
                                productService.loadProductsForCategory(categoryId)
                            }
                        },
                        onProductSelected = { product, sourceBounds ->
                            selectedProductWasOpenedFromCart = false
                            selectedProductPresentation = SelectedProductPresentation(
                                product = product,
                                sourceBounds = sourceBounds
                            )
                        }
                    )
                }

                CoreTab.Saved -> {
                    SavedView(
                        products = displayedSavedProducts,
                        topOverlayHeight = topOverlayHeight,
                        bottomOverlayHeight = bottomOverlayHeight,
                        modifier = Modifier.fillMaxSize(),
                        onProductSelected = { product, sourceBounds ->
                            selectedProductWasOpenedFromCart = false
                            selectedProductPresentation = SelectedProductPresentation(
                                product = product,
                                sourceBounds = sourceBounds
                            )
                        }
                    )
                }

                CoreTab.Cart -> {
                    CartView(
                        products = cartProducts,
                        selectedSortMode = selectedCartSortMode,
                        topOverlayHeight = topOverlayHeight,
                        bottomOverlayHeight = bottomOverlayHeight,
                        modifier = Modifier.fillMaxSize(),
                        onProductSelected = { product, sourceBounds ->
                            selectedProductWasOpenedFromCart = true
                            selectedProductPresentation = SelectedProductPresentation(
                                product = product,
                                sourceBounds = sourceBounds
                            )
                        },
                        onCheckoutSelected = { sourceBounds ->
                            checkoutSourceBounds = sourceBounds
                        }
                    )
                }
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
            selectedTab = selectedTab,
            selectedSortMode = selectedRibbonSortMode,
            firstButtonAlpha = if (hasFinishedMorph) {
                1f
            } else {
                0f
            },
            revealedTrailingButtonCount = revealedTrailingButtonCount,
            onSortFamilySelected = { sortFamily ->
                when (selectedTab) {
                    CoreTab.Browse -> {
                        selectedBrowseSortMode = selectedBrowseSortMode.nextModeForFamily(
                            family = sortFamily
                        )
                    }

                    CoreTab.Saved -> {
                        selectedSavedSortMode = selectedSavedSortMode.nextModeForFamily(
                            family = sortFamily
                        )
                    }

                    CoreTab.Cart -> {
                        selectedCartSortMode = selectedCartSortMode.nextModeForFamily(
                            family = sortFamily
                        )
                    }
                }
            }
        )

        CoreTabBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(5f)
                .onGloballyPositioned { coordinates ->
                    tabBarHeightPx = coordinates.boundsInRoot().height
                },
            selectedTab = selectedTab,
            isVisible = showBottomTabBar,
            cartItemCount = cartProducts.size,
            onTabPositioned = { tab, bounds ->
                if (tab == CoreTab.Cart) {
                    cartTabBounds = bounds
                }
            },
            onTabSelected = { tab ->
                selectedTab = tab
            }
        )

        val source = initialStartButtonBounds
        val target = lockedTargetBounds

        if (!hasFinishedMorph && source != null) {
            StartShoppingButtonMorphTransition(
                modifier = Modifier.zIndex(10f),
                sourceBounds = source,
                targetBounds = if (hasStartedMorph && target != null) {
                    target
                } else {
                    source
                },
                progress = if (hasStartedMorph) {
                    morphProgress.value
                } else {
                    0f
                }
            )
        }

        selectedProductPresentation?.let { presentation ->
            ProductDetailOverlayView(
                modifier = Modifier.zIndex(20f),
                product = presentation.product,
                sourceBounds = presentation.sourceBounds,
                cartTargetBounds = cartTabBounds,
                isCartMode = selectedProductWasOpenedFromCart,
                isSaved = savedProductIds.contains(presentation.product.id),
                cartQuantity = cartProducts.count { product ->
                    product.id == presentation.product.id
                },
                onAddToCartCompleted = { product ->
                    cartProducts = cartProducts + product
                },
                onIncreaseCartQuantity = { product ->
                    cartProducts = cartProducts + product
                },
                onDecreaseCartQuantity = { product ->
                    val indexToRemove = cartProducts.indexOfFirst { cartProduct ->
                        cartProduct.id == product.id
                    }

                    if (indexToRemove >= 0) {
                        cartProducts = cartProducts.filterIndexed { index, _ ->
                            index != indexToRemove
                        }

                        if (cartProducts.none { cartProduct -> cartProduct.id == product.id }) {
                            selectedProductPresentation = null
                        }
                    }
                },
                onRemoveFromCart = { product ->
                    cartProducts = cartProducts.filterNot { cartProduct ->
                        cartProduct.id == product.id
                    }
                },
                onSavedClicked = { product ->
                    val alreadySaved = savedProductIds.contains(product.id)

                    savedProductIds = if (alreadySaved) {
                        savedProductIds - product.id
                    } else {
                        savedProductIds + product.id
                    }

                    savedProducts = if (alreadySaved) {
                        savedProducts.filterNot { savedProduct ->
                            savedProduct.id == product.id
                        }
                    } else {
                        val alreadyInSavedProducts = savedProducts.any { savedProduct ->
                            savedProduct.id == product.id
                        }

                        if (alreadyInSavedProducts) {
                            savedProducts
                        } else {
                            savedProducts + product
                        }
                    }
                },
                onDismissed = {
                    selectedProductPresentation = null
                    selectedProductWasOpenedFromCart = false
                }
            )
        }

        checkoutSourceBounds?.let { sourceBounds ->
            CheckoutDemoOverlayView(
                modifier = Modifier.zIndex(30f),
                sourceBounds = sourceBounds,
                onDismissed = {
                    checkoutSourceBounds = null
                }
            )
        }
    }
}

private fun List<Product>.sortedForProductSortMode(
    sortMode: ProductSortMode?
): List<Product> {
    return when (sortMode) {
        null -> {
            this
        }

        ProductSortMode.RecentNewest -> {
            this.withIndex()
                .sortedByDescending { indexedProduct ->
                    indexedProduct.index
                }
                .map { indexedProduct ->
                    indexedProduct.value
                }
        }

        ProductSortMode.RecentOldest -> {
            this
        }

        ProductSortMode.NameAscending -> {
            this.sortedBy { product ->
                product.title.lowercase()
            }
        }

        ProductSortMode.NameDescending -> {
            this.sortedByDescending { product ->
                product.title.lowercase()
            }
        }

        ProductSortMode.PriceLowest -> {
            this.sortedBy { product ->
                product.price
            }
        }

        ProductSortMode.PriceHighest -> {
            this.sortedByDescending { product ->
                product.price
            }
        }

        ProductSortMode.RatingHighest -> {
            this.sortedByDescending { product ->
                product.rating
            }
        }

        ProductSortMode.RatingLowest -> {
            this.sortedBy { product ->
                product.rating
            }
        }

        ProductSortMode.QuantityHighest,
        ProductSortMode.QuantityLowest -> {
            this
        }
    }
}
package com.hunterlindsay.kmpcommercedemo.android.ui.core

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceWhite
import com.hunterlindsay.kmpcommercedemo.concerns.products.Product
import kotlin.math.roundToInt

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun ProductDetailOverlayView(
    product: Product,
    sourceBounds: Rect,
    cartTargetBounds: Rect?,
    isCartMode: Boolean,
    isSaved: Boolean,
    cartQuantity: Int,
    modifier: Modifier = Modifier,
    onAddToCartCompleted: (Product) -> Unit,
    onIncreaseCartQuantity: (Product) -> Unit,
    onDecreaseCartQuantity: (Product) -> Unit,
    onRemoveFromCart: (Product) -> Unit,
    onSavedClicked: (Product) -> Unit,
    onDismissed: () -> Unit
) {
    val density = LocalDensity.current

    val openProgress = remember {
        Animatable(0f)
    }

    val addToCartProgress = remember {
        Animatable(0f)
    }

    var shouldDismiss by remember {
        mutableStateOf(false)
    }

    var isAddingToCart by remember {
        mutableStateOf(false)
    }

    var rootWidthPx by remember {
        mutableFloatStateOf(0f)
    }

    var rootHeightPx by remember {
        mutableFloatStateOf(0f)
    }

    var detailImageBounds by remember {
        mutableStateOf(Rect.Zero)
    }

    LaunchedEffect(product.id) {
        openProgress.snapTo(0f)
        addToCartProgress.snapTo(0f)

        openProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 360)
        )
    }

    LaunchedEffect(shouldDismiss) {
        if (!shouldDismiss) {
            return@LaunchedEffect
        }

        openProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 240)
        )

        onDismissed()
    }

    LaunchedEffect(isAddingToCart) {
        if (!isAddingToCart) {
            return@LaunchedEffect
        }

        addToCartProgress.snapTo(0f)

        addToCartProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 760)
        )

        onAddToCartCompleted(product)
        onDismissed()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                rootWidthPx = coordinates.size.width.toFloat()
                rootHeightPx = coordinates.size.height.toFloat()
            }
    ) {
        if (rootWidthPx <= 0f || rootHeightPx <= 0f) {
            return@Box
        }

        val targetHorizontalMarginPx = with(density) {
            22.dp.toPx()
        }

        val openCardWidthPx = rootWidthPx - (targetHorizontalMarginPx * 2f)
        val openCardHeightPx = rootHeightPx * 0.74f
        val openCardLeftPx = targetHorizontalMarginPx
        val openCardTopPx = (rootHeightPx - openCardHeightPx) / 2f

        val openValue = openProgress.value
        val easedOpenValue = smoothStep(openValue)

        val openedLeftPx = lerp(
            start = sourceBounds.left,
            end = openCardLeftPx,
            progress = easedOpenValue
        )

        val openedTopPx = lerp(
            start = sourceBounds.top,
            end = openCardTopPx,
            progress = easedOpenValue
        )

        val openedWidthPx = lerp(
            start = sourceBounds.width,
            end = openCardWidthPx,
            progress = easedOpenValue
        )

        val openedHeightPx = lerp(
            start = sourceBounds.height,
            end = openCardHeightPx,
            progress = easedOpenValue
        )

        val addValue = addToCartProgress.value

        val shrinkProgress = smoothStep(
            progress = segmentProgress(
                progress = addValue,
                start = 0f,
                end = 0.42f
            )
        )

        val flyProgress = smoothStep(
            progress = segmentProgress(
                progress = addValue,
                start = 0.42f,
                end = 1f
            )
        )

        val circularImageSizePx = with(density) {
            92.dp.toPx()
        }

        val imageCircleLeftPx = if (detailImageBounds.width > 0f) {
            detailImageBounds.center.x - (circularImageSizePx / 2f)
        } else {
            openedLeftPx + ((openedWidthPx - circularImageSizePx) / 2f)
        }

        val imageCircleTopPx = if (detailImageBounds.height > 0f) {
            detailImageBounds.center.y - (circularImageSizePx / 2f)
        } else {
            openedTopPx + with(density) {
                150.dp.toPx()
            }
        }

        val currentCardLeftPx = lerp(
            start = openedLeftPx,
            end = imageCircleLeftPx,
            progress = shrinkProgress
        )

        val currentCardTopPx = lerp(
            start = openedTopPx,
            end = imageCircleTopPx,
            progress = shrinkProgress
        )

        val currentCardWidthPx = lerp(
            start = openedWidthPx,
            end = circularImageSizePx,
            progress = shrinkProgress
        )

        val currentCardHeightPx = lerp(
            start = openedHeightPx,
            end = circularImageSizePx,
            progress = shrinkProgress
        )

        val contentAlpha = openValue * (1f - segmentProgress(addValue, 0f, 0.28f))
        val cardAlpha = openValue * (1f - flyProgress)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(openValue * (1f - segmentProgress(addValue, 0.55f, 1f)))
                .background(
                    color = CommerceDarkBlue.copy(alpha = 0.58f)
                )
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null
                ) {
                    if (!isAddingToCart) {
                        shouldDismiss = true
                    }
                }
        )

        Box(
            modifier = Modifier
                .offset(
                    x = with(density) {
                        currentCardLeftPx.toDp()
                    },
                    y = with(density) {
                        currentCardTopPx.toDp()
                    }
                )
                .width(
                    width = with(density) {
                        currentCardWidthPx.toDp()
                    }
                )
                .height(
                    height = with(density) {
                        currentCardHeightPx.toDp()
                    }
                )
                .alpha(cardAlpha)
                .background(
                    color = CommerceWhite,
                    shape = RoundedCornerShape(
                        size = lerp(
                            start = 34f,
                            end = 999f,
                            progress = shrinkProgress
                        ).dp
                    )
                )
        ) {
            ProductDetailScrollableContentView(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(contentAlpha)
                    .padding(
                        start = 20.dp,
                        top = 24.dp,
                        end = 20.dp,
                        bottom = 104.dp
                    ),
                product = product,
                onImagePositioned = { bounds ->
                    detailImageBounds = bounds
                }
            )

            ProductDetailCloseButtonView(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = 16.dp,
                        end = 16.dp
                    )
                    .alpha(contentAlpha),
                onClick = {
                    if (!isAddingToCart) {
                        shouldDismiss = true
                    }
                }
            )

            if (isCartMode) {
                ProductDetailCartControlsView(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 20.dp
                        )
                        .alpha(contentAlpha),
                    quantity = cartQuantity,
                    onMinusClicked = {
                        onDecreaseCartQuantity(product)
                    },
                    onPlusClicked = {
                        onIncreaseCartQuantity(product)
                    },
                    onRemoveClicked = {
                        onRemoveFromCart(product)
                        shouldDismiss = true
                    }
                )
            } else {
                ProductDetailBrowseControlsView(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 20.dp
                        )
                        .alpha(contentAlpha),
                    isSaved = isSaved,
                    isAddToCartEnabled = !isAddingToCart,
                    onAddToCartClicked = {
                        if (!isAddingToCart) {
                            isAddingToCart = true
                        }
                    },
                    onSavedClicked = {
                        onSavedClicked(product)

                        if (isSaved) {
                            shouldDismiss = true
                        }
                    }
                )
            }

            if (isAddingToCart && shrinkProgress > 0.15f) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    model = product.thumbnail,
                    contentDescription = product.title,
                    contentScale = ContentScale.Fit
                )
            }
        }

        if (isAddingToCart && addValue > 0.42f) {
            val fallbackTargetCenterXPx = rootWidthPx * 0.84f
            val fallbackTargetCenterYPx = rootHeightPx - with(density) {
                86.dp.toPx()
            }

            val cartIconTargetCenterXPx = cartTargetBounds?.center?.x ?: fallbackTargetCenterXPx

            val cartIconTargetCenterYPx = cartTargetBounds?.let { bounds ->
                bounds.top + with(density) {
                    26.dp.toPx()
                }
            } ?: fallbackTargetCenterYPx

            ProductAddToCartFlyingImageView(
                product = product,
                imageBounds = Rect(
                    left = imageCircleLeftPx,
                    top = imageCircleTopPx,
                    right = imageCircleLeftPx + circularImageSizePx,
                    bottom = imageCircleTopPx + circularImageSizePx
                ),
                targetCenterXPx = cartIconTargetCenterXPx,
                targetCenterYPx = cartIconTargetCenterYPx,
                progress = flyProgress
            )
        }
    }
}

@Composable
private fun ProductDetailBrowseControlsView(
    isSaved: Boolean,
    isAddToCartEnabled: Boolean,
    modifier: Modifier = Modifier,
    onAddToCartClicked: () -> Unit,
    onSavedClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProductDetailAddToCartButtonView(
            modifier = Modifier.weight(1f),
            isEnabled = isAddToCartEnabled,
            onClick = onAddToCartClicked
        )

        ProductDetailSavedButtonView(
            isSaved = isSaved,
            onClick = onSavedClicked
        )
    }
}

@Composable
private fun ProductDetailSavedButtonView(
    isSaved: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .background(
                color = if (isSaved) {
                    CommerceDarkBlue
                } else {
                    CommerceDarkBlue.copy(alpha = 0.09f)
                },
                shape = CircleShape
            )
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.offset(y = (-1.5).dp),
            text = if (isSaved) {
                "★"
            } else {
                "☆"
            },
            fontWeight = FontWeight.Black,
            fontSize = 25.sp,
            lineHeight = 25.sp,
            color = if (isSaved) {
                CommerceWhite
            } else {
                CommerceDarkBlue
            }
        )
    }
}

@Composable
private fun ProductDetailCartControlsView(
    quantity: Int,
    modifier: Modifier = Modifier,
    onMinusClicked: () -> Unit,
    onPlusClicked: () -> Unit,
    onRemoveClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .background(
                    color = CommerceDarkBlue.copy(alpha = 0.09f),
                    shape = RoundedCornerShape(999.dp)
                )
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            ProductDetailQuantityButtonView(
                text = "−",
                onClick = onMinusClicked
            )

            Text(
                modifier = Modifier.width(22.dp),
                text = quantity.toString(),
                fontWeight = FontWeight.Black,
                fontSize = 15.sp,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
                color = CommerceDarkBlue
            )

            ProductDetailQuantityButtonView(
                text = "+",
                onClick = onPlusClicked
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .background(
                    color = Color(0xFFE04A4A),
                    shape = RoundedCornerShape(999.dp)
                )
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null,
                    onClick = onRemoveClicked
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "REMOVE",
                fontWeight = FontWeight.Black,
                fontSize = 15.sp,
                lineHeight = 16.sp,
                letterSpacing = 1.0.sp,
                color = CommerceWhite
            )
        }
    }
}

@Composable
private fun ProductDetailQuantityButtonView(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(38.dp)
            .background(
                color = CommerceDarkBlue,
                shape = CircleShape
            )
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Black,
            fontSize = 22.sp,
            lineHeight = 22.sp,
            color = CommerceWhite
        )
    }
}

@Composable
private fun ProductDetailScrollableContentView(
    product: Product,
    modifier: Modifier = Modifier,
    onImagePositioned: (Rect) -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(
            state = rememberScrollState()
        )
    ) {
        val brand = product.brand

        if (!brand.isNullOrBlank()) {
            Text(
                modifier = Modifier.padding(end = 58.dp),
                text = brand.uppercase(),
                fontWeight = FontWeight.Black,
                fontSize = 11.sp,
                lineHeight = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = CommerceDarkBlue.copy(alpha = 0.52f)
            )

            Spacer(modifier = Modifier.height(6.dp))
        }

        Text(
            modifier = Modifier.padding(end = 58.dp),
            text = product.title,
            fontWeight = FontWeight.Black,
            fontSize = 28.sp,
            lineHeight = 30.sp,
            color = CommerceDarkBlue
        )

        Spacer(modifier = Modifier.height(20.dp))

        ProductDetailImageView(
            product = product,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    onImagePositioned(coordinates.boundsInRoot())
                }
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                text = product.priceDisplayText(),
                fontWeight = FontWeight.Black,
                fontSize = 25.sp,
                lineHeight = 26.sp,
                color = CommerceDarkBlue
            )

            if (product.discountPercentage > 0.0) {
                ProductDetailBadgeView(
                    text = "-${product.discountPercentage.roundToInt()}%"
                )
            }

            ProductDetailBadgeView(
                text = "★ ${product.rating.oneDecimalDisplayText()}"
            )
        }

        Spacer(modifier = Modifier.height(11.dp))

        Text(
            text = product.availabilityDisplayText(),
            fontWeight = FontWeight.Black,
            fontSize = 13.sp,
            lineHeight = 15.sp,
            color = CommerceDarkBlue.copy(alpha = 0.52f)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = product.description,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            color = CommerceDarkBlue.copy(alpha = 0.72f)
        )

        Spacer(modifier = Modifier.height(22.dp))

        ProductDetailInfoRowView(
            title = "SKU",
            value = product.sku
        )

        ProductDetailInfoRowView(
            title = "STOCK",
            value = product.stock.toString()
        )

        val shippingInformation = product.shippingInformation
        if (!shippingInformation.isNullOrBlank()) {
            ProductDetailInfoRowView(
                title = "SHIPPING",
                value = shippingInformation
            )
        }

        val warrantyInformation = product.warrantyInformation
        if (!warrantyInformation.isNullOrBlank()) {
            ProductDetailInfoRowView(
                title = "WARRANTY",
                value = warrantyInformation
            )
        }

        val returnPolicy = product.returnPolicy
        if (!returnPolicy.isNullOrBlank()) {
            ProductDetailInfoRowView(
                title = "RETURNS",
                value = returnPolicy
            )
        }
    }
}

@Composable
private fun ProductDetailImageView(
    product: Product,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1.65f)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        CommerceDarkBlue.copy(alpha = 0.10f),
                        CommerceDarkBlue.copy(alpha = 0.04f),
                        CommerceDarkBlue.copy(alpha = 0.00f)
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            model = product.thumbnail,
            contentDescription = product.title,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun ProductAddToCartFlyingImageView(
    product: Product,
    imageBounds: Rect,
    targetCenterXPx: Float,
    targetCenterYPx: Float,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val easedProgress = smoothStep(progress)

    val startSizePx = imageBounds.width
    val endSizePx = with(density) {
        42.dp.toPx()
    }

    val startCenterXPx = imageBounds.center.x
    val startCenterYPx = imageBounds.center.y

    val controlCenterXPx = startCenterXPx + ((targetCenterXPx - startCenterXPx) * 0.34f)
    val controlCenterYPx = startCenterYPx - with(density) {
        170.dp.toPx()
    }

    val currentCenterXPx = quadraticBezier(
        start = startCenterXPx,
        control = controlCenterXPx,
        end = targetCenterXPx,
        progress = easedProgress
    )

    val currentCenterYPx = quadraticBezier(
        start = startCenterYPx,
        control = controlCenterYPx,
        end = targetCenterYPx,
        progress = easedProgress
    )

    val currentSizePx = lerp(
        start = startSizePx,
        end = endSizePx,
        progress = easedProgress
    )

    Box(
        modifier = modifier
            .offset(
                x = with(density) {
                    (currentCenterXPx - (currentSizePx / 2f)).toDp()
                },
                y = with(density) {
                    (currentCenterYPx - (currentSizePx / 2f)).toDp()
                }
            )
            .size(
                size = with(density) {
                    currentSizePx.toDp()
                }
            )
            .background(
                color = CommerceWhite,
                shape = CircleShape
            )
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = product.thumbnail,
            contentDescription = product.title,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun ProductDetailCloseButtonView(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(38.dp)
            .background(
                color = CommerceDarkBlue.copy(alpha = 0.08f),
                shape = CircleShape
            )
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "×",
            fontWeight = FontWeight.Black,
            fontSize = 25.sp,
            lineHeight = 25.sp,
            color = CommerceDarkBlue.copy(alpha = 0.72f)
        )
    }
}

@Composable
private fun ProductDetailAddToCartButtonView(
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = CommerceDarkBlue,
                shape = RoundedCornerShape(999.dp)
            )
            .clickable(
                enabled = isEnabled,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "ADD TO CART",
            fontWeight = FontWeight.Black,
            fontSize = 16.sp,
            lineHeight = 17.sp,
            letterSpacing = 1.1.sp,
            textAlign = TextAlign.Center,
            color = CommerceWhite
        )
    }
}

@Composable
private fun ProductDetailBadgeView(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = CommerceDarkBlue.copy(alpha = 0.09f),
                shape = RoundedCornerShape(999.dp)
            )
            .padding(
                horizontal = 9.dp,
                vertical = 5.dp
            )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Black,
            fontSize = 11.sp,
            lineHeight = 12.sp,
            color = CommerceDarkBlue.copy(alpha = 0.82f)
        )
    }
}

@Composable
private fun ProductDetailInfoRowView(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier.width(92.dp),
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            color = CommerceDarkBlue.copy(alpha = 0.42f)
        )

        Text(
            modifier = Modifier.weight(1f),
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            color = CommerceDarkBlue.copy(alpha = 0.68f)
        )
    }
}

private fun Product.priceDisplayText(): String {
    return "$${price.moneyDisplayText()}"
}

private fun Product.availabilityDisplayText(): String {
    val currentAvailabilityStatus = availabilityStatus

    return when {
        !currentAvailabilityStatus.isNullOrBlank() -> currentAvailabilityStatus
        stock > 0 -> "$stock in stock"
        else -> "Out of stock"
    }
}

private fun Double.moneyDisplayText(): String {
    val cents = (this * 100.0).roundToInt()
    val dollars = cents / 100
    val remainingCents = cents % 100

    return if (remainingCents == 0) {
        dollars.toString()
    } else {
        "$dollars.${remainingCents.toString().padStart(2, '0')}"
    }
}

private fun Double.oneDecimalDisplayText(): String {
    val rounded = (this * 10.0).roundToInt() / 10.0
    val wholeNumber = rounded.toInt()

    return if (rounded == wholeNumber.toDouble()) {
        wholeNumber.toString()
    } else {
        rounded.toString()
    }
}

private fun segmentProgress(
    progress: Float,
    start: Float,
    end: Float
): Float {
    if (progress <= start) {
        return 0f
    }

    if (progress >= end) {
        return 1f
    }

    return (progress - start) / (end - start)
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

private fun quadraticBezier(
    start: Float,
    control: Float,
    end: Float,
    progress: Float
): Float {
    val inverseProgress = 1f - progress

    return (inverseProgress * inverseProgress * start) +
            (2f * inverseProgress * progress * control) +
            (progress * progress * end)
}
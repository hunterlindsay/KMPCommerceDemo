package com.hunterlindsay.kmpcommercedemo.android.ui.core.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceWhite
import com.hunterlindsay.kmpcommercedemo.concerns.products.Product
import kotlin.math.roundToInt

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun BrowseProductRowView(
    product: Product,
    modifier: Modifier = Modifier,
    onProductSelected: (Product, Rect) -> Unit
) {
    val brand = product.brand
    val rowBounds = remember {
        mutableStateOf(Rect.Zero)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                rowBounds.value = coordinates.boundsInRoot()
            }
            .background(
                color = CommerceWhite,
                shape = RoundedCornerShape(28.dp)
            )
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) {
                onProductSelected(product, rowBounds.value)
            }
            .padding(
                start = 20.dp,
                top = 15.dp,
                end = 14.dp,
                bottom = 15.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (!brand.isNullOrBlank()) {
                Text(
                    text = brand.uppercase(),
                    fontWeight = FontWeight.Black,
                    fontSize = 10.sp,
                    lineHeight = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = CommerceDarkBlue.copy(alpha = 0.54f)
                )
            }

            Text(
                modifier = Modifier.offset(y = (-1).dp),
                text = product.title,
                fontWeight = FontWeight.Black,
                fontSize = 17.sp,
                lineHeight = 19.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = CommerceDarkBlue
            )

            Text(
                text = product.description,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = CommerceDarkBlue.copy(alpha = 0.68f)
            )

            Row(
                modifier = Modifier.padding(top = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Text(
                    text = product.priceDisplayText(),
                    fontWeight = FontWeight.Black,
                    fontSize = 17.sp,
                    lineHeight = 18.sp,
                    color = CommerceDarkBlue
                )

                if (product.discountPercentage > 0.0) {
                    BrowseProductBadgeView(
                        text = "-${product.discountPercentage.roundToInt()}%"
                    )
                }

                BrowseProductBadgeView(
                    text = "★ ${product.rating.oneDecimalDisplayText()}"
                )
            }

            Text(
                text = product.availabilityDisplayText(),
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                lineHeight = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = CommerceDarkBlue.copy(alpha = 0.48f)
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        BrowseProductImageView(
            product = product
        )
    }
}

@Composable
private fun BrowseProductBadgeView(
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
                horizontal = 8.dp,
                vertical = 4.dp
            )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Black,
            fontSize = 10.sp,
            lineHeight = 11.sp,
            color = CommerceDarkBlue.copy(alpha = 0.82f)
        )
    }
}

@Composable
fun BrowseProductSkeletonRowView(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(118.dp)
            .background(
                color = CommerceWhite.copy(alpha = 0.40f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(
                start = 20.dp,
                top = 18.dp,
                end = 17.dp,
                bottom = 18.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .height(9.dp)
                    .background(
                        color = CommerceWhite.copy(alpha = 0.58f),
                        shape = RoundedCornerShape(999.dp)
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.72f)
                    .height(15.dp)
                    .background(
                        color = CommerceWhite.copy(alpha = 0.78f),
                        shape = RoundedCornerShape(999.dp)
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .height(10.dp)
                    .background(
                        color = CommerceWhite.copy(alpha = 0.54f),
                        shape = RoundedCornerShape(999.dp)
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.46f)
                    .height(12.dp)
                    .background(
                        color = CommerceWhite.copy(alpha = 0.66f),
                        shape = RoundedCornerShape(999.dp)
                    )
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Box(
            modifier = Modifier
                .size(76.dp)
                .background(
                    color = CommerceWhite.copy(alpha = 0.62f),
                    shape = CircleShape
                )
        )
    }
}

@Composable
fun BrowseEmptyProductsRowView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = CommerceWhite.copy(alpha = 0.28f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(
                horizontal = 20.dp,
                vertical = 18.dp
            )
    ) {
        Text(
            text = "No products found",
            fontWeight = FontWeight.Black,
            fontSize = 15.sp,
            lineHeight = 17.sp,
            color = MaterialTheme.colorScheme.onBackground
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
package com.hunterlindsay.kmpcommercedemo.android.ui.core.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceWhite
import com.hunterlindsay.kmpcommercedemo.android.ui.core.browse.BrowseProductRowView
import com.hunterlindsay.kmpcommercedemo.android.ui.core.sort.ProductSortMode
import com.hunterlindsay.kmpcommercedemo.concerns.products.Product
import kotlin.math.roundToInt

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun CartView(
    products: List<Product>,
    selectedSortMode: ProductSortMode?,
    topOverlayHeight: Dp,
    bottomOverlayHeight: Dp,
    modifier: Modifier = Modifier,
    onProductSelected: (Product, Rect) -> Unit
) {
    val cartLines = remember(
        products,
        selectedSortMode
    ) {
        val recentCartLines = products
            .withIndex()
            .groupBy { indexedProduct ->
                indexedProduct.value.id
            }
            .values
            .map { indexedProducts ->
                val firstIndexedProduct = indexedProducts.first()

                CartLine(
                    product = firstIndexedProduct.value,
                    quantity = indexedProducts.size,
                    firstAddedIndex = firstIndexedProduct.index
                )
            }

        when (selectedSortMode) {
            null -> {
                recentCartLines.sortedBy { cartLine ->
                    cartLine.firstAddedIndex
                }
            }

            ProductSortMode.RecentNewest -> {
                recentCartLines.sortedByDescending { cartLine ->
                    cartLine.firstAddedIndex
                }
            }

            ProductSortMode.RecentOldest -> {
                recentCartLines.sortedBy { cartLine ->
                    cartLine.firstAddedIndex
                }
            }

            ProductSortMode.NameAscending -> {
                recentCartLines.sortedBy { cartLine ->
                    cartLine.product.title.lowercase()
                }
            }

            ProductSortMode.NameDescending -> {
                recentCartLines.sortedByDescending { cartLine ->
                    cartLine.product.title.lowercase()
                }
            }

            ProductSortMode.PriceLowest -> {
                recentCartLines.sortedBy { cartLine ->
                    cartLine.product.price * cartLine.quantity
                }
            }

            ProductSortMode.PriceHighest -> {
                recentCartLines.sortedByDescending { cartLine ->
                    cartLine.product.price * cartLine.quantity
                }
            }

            ProductSortMode.QuantityHighest -> {
                recentCartLines.sortedWith(
                    compareByDescending<CartLine> { cartLine ->
                        cartLine.quantity
                    }.thenBy { cartLine ->
                        cartLine.product.title.lowercase()
                    }
                )
            }

            ProductSortMode.QuantityLowest -> {
                recentCartLines.sortedWith(
                    compareBy<CartLine> { cartLine ->
                        cartLine.quantity
                    }.thenBy { cartLine ->
                        cartLine.product.title.lowercase()
                    }
                )
            }

            ProductSortMode.RatingHighest -> {
                recentCartLines.sortedByDescending { cartLine ->
                    cartLine.product.rating
                }
            }

            ProductSortMode.RatingLowest -> {
                recentCartLines.sortedBy { cartLine ->
                    cartLine.product.rating
                }
            }
        }
    }

    val subtotal = remember(products) {
        products.sumOf { product ->
            product.price
        }
    }

    val horizontalPadding = 22.dp
    val titleTopPadding = topOverlayHeight
    val titleHeight = 38.dp
    val titleToListSpacing = 40.dp

    val summaryHeight = 94.dp
    val summaryToTabBarSpacing = 24.dp

    val listTopPadding =
        titleTopPadding +
                titleHeight +
                titleToListSpacing

    val listBottomPadding =
        bottomOverlayHeight +
                summaryHeight +
                summaryToTabBarSpacing +
                34.dp

    val topFadeHeight =
        titleTopPadding +
                titleHeight +
                20.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding)
    ) {
        if (products.isEmpty()) {
            EmptyCartView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = listTopPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = listTopPadding,
                    bottom = listBottomPadding
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = cartLines,
                    key = { cartLine ->
                        cartLine.product.id
                    }
                ) { cartLine ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(7.dp)
                    ) {
                        CartQuantityHeaderView(
                            quantity = cartLine.quantity,
                            lineTotal = cartLine.product.price * cartLine.quantity
                        )

                        BrowseProductRowView(
                            product = cartLine.product,
                            onProductSelected = onProductSelected
                        )
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier.height(1.dp)
                    )
                }
            }

            CartBottomFade(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(bottomOverlayHeight + summaryHeight + summaryToTabBarSpacing + 40.dp)
                    .zIndex(1f)
            )

            CartSummaryView(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = bottomOverlayHeight + summaryToTabBarSpacing
                    )
                    .zIndex(4f),
                itemCount = products.size,
                subtotal = subtotal
            )
        }

        CartTopFade(
            modifier = Modifier
                .fillMaxWidth()
                .height(topFadeHeight)
                .zIndex(3f)
        )

        CartTitleView(
            modifier = Modifier
                .padding(top = titleTopPadding)
                .zIndex(4f)
        )
    }
}

@Composable
private fun CartSummaryView(
    itemCount: Int,
    subtotal: Double,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(94.dp)
            .background(
                color = CommerceWhite.copy(alpha = 0.18f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(
                horizontal = 20.dp,
                vertical = 17.dp
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "ITEMS",
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp,
                    lineHeight = 12.sp,
                    color = CommerceWhite.copy(alpha = 0.58f)
                )

                Text(
                    text = itemCount.toString(),
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp,
                    lineHeight = 15.sp,
                    color = CommerceWhite
                )
            }

            Spacer(modifier = Modifier.height(9.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "TOTAL",
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp,
                    lineHeight = 12.sp,
                    color = CommerceWhite.copy(alpha = 0.58f)
                )

                Text(
                    text = subtotal.priceDisplayText(),
                    fontWeight = FontWeight.Black,
                    fontSize = 25.sp,
                    lineHeight = 25.sp,
                    color = CommerceWhite
                )
            }
        }
    }
}

@Composable
private fun CartQuantityHeaderView(
    quantity: Int,
    lineTotal: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 18.dp,
                end = 18.dp
            )
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "QTY $quantity",
            fontWeight = FontWeight.Black,
            fontSize = 10.sp,
            lineHeight = 11.sp,
            color = CommerceWhite.copy(alpha = 0.52f)
        )

        Text(
            text = lineTotal.priceDisplayText(),
            fontWeight = FontWeight.Black,
            fontSize = 10.sp,
            lineHeight = 11.sp,
            color = CommerceWhite.copy(alpha = 0.52f)
        )
    }
}

@Composable
private fun EmptyCartView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = CommerceWhite.copy(alpha = 0.16f),
                shape = RoundedCornerShape(32.dp)
            )
            .padding(
                horizontal = 24.dp,
                vertical = 34.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Your cart is empty",
                fontWeight = FontWeight.Black,
                fontSize = 24.sp,
                lineHeight = 25.sp,
                textAlign = TextAlign.Center,
                color = CommerceWhite
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Add something from Browse and it’ll appear here.",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center,
                color = CommerceWhite.copy(alpha = 0.66f)
            )
        }
    }
}

@Composable
private fun CartTopFade(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            Brush.verticalGradient(
                colorStops = arrayOf(
                    0.00f to CommerceDarkBlue,
                    0.68f to CommerceDarkBlue,
                    0.86f to CommerceDarkBlue.copy(alpha = 0.70f),
                    0.96f to CommerceDarkBlue.copy(alpha = 0.28f),
                    1.00f to CommerceDarkBlue.copy(alpha = 0f)
                )
            )
        )
    )
}

@Composable
private fun CartBottomFade(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            Brush.verticalGradient(
                colorStops = arrayOf(
                    0.00f to CommerceDarkBlue.copy(alpha = 0f),
                    0.24f to CommerceDarkBlue.copy(alpha = 0.50f),
                    0.48f to CommerceDarkBlue.copy(alpha = 0.88f),
                    1.00f to CommerceDarkBlue
                )
            )
        )
    )
}

@Composable
private fun CartTitleView(
    modifier: Modifier = Modifier
) {
    val title = "CART"
    val color = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.offset(x = (-0.5).dp),
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 38.sp,
            lineHeight = 38.sp,
            letterSpacing = 1.5.sp,
            maxLines = 1,
            color = color
        )

        Text(
            modifier = Modifier.offset(x = 0.5.dp),
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 38.sp,
            lineHeight = 38.sp,
            letterSpacing = 1.5.sp,
            maxLines = 1,
            color = color
        )

        Text(
            modifier = Modifier.offset(y = (-0.5).dp),
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 38.sp,
            lineHeight = 38.sp,
            letterSpacing = 1.5.sp,
            maxLines = 1,
            color = color
        )

        Text(
            modifier = Modifier.offset(y = 0.5.dp),
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 38.sp,
            lineHeight = 38.sp,
            letterSpacing = 1.5.sp,
            maxLines = 1,
            color = color
        )

        Text(
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 38.sp,
            lineHeight = 38.sp,
            letterSpacing = 1.5.sp,
            maxLines = 1,
            color = color
        )
    }
}

private data class CartLine(
    val product: Product,
    val quantity: Int,
    val firstAddedIndex: Int
)

private fun Double.priceDisplayText(): String {
    val cents = (this * 100.0).roundToInt()
    val dollars = cents / 100
    val remainingCents = cents % 100

    return if (remainingCents == 0) {
        "$$dollars"
    } else {
        "$$dollars.${remainingCents.toString().padStart(2, '0')}"
    }
}
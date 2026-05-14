package com.hunterlindsay.kmpcommercedemo.android.ui.core.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.dp
import com.hunterlindsay.kmpcommercedemo.android.ui.core.sort.ProductSortMode
import com.hunterlindsay.kmpcommercedemo.concerns.products.Product

/**
 * Created by Hunter Lindsay on 14/05/2026.
 */

@Composable
fun BrowseProductListView(
    products: List<Product>,
    selectedSortMode: ProductSortMode?,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onProductSelected: (Product, Rect) -> Unit
) {
    val displayedProducts = remember(
        products,
        selectedSortMode
    ) {
        products.sortedForProductSortMode(
            sortMode = selectedSortMode
        )
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        when {
            isLoading -> {
                for (index in 0 until 3) {
                    BrowseProductSkeletonRowView()
                }
            }

            displayedProducts.isEmpty() -> {
                BrowseEmptyProductsRowView()
            }

            else -> {
                displayedProducts.forEach { product ->
                    BrowseProductRowView(
                        product = product,
                        onProductSelected = onProductSelected
                    )
                }
            }
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
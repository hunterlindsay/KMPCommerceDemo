package com.hunterlindsay.kmpcommercedemo.android.ui.core.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.dp
import com.hunterlindsay.kmpcommercedemo.concerns.products.Product

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun BrowseProductListView(
    products: List<Product>,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onProductSelected: (Product, Rect) -> Unit
) {
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

            products.isEmpty() -> {
                BrowseEmptyProductsRowView()
            }

            else -> {
                products.forEach { product ->
                    BrowseProductRowView(
                        product = product,
                        onProductSelected = onProductSelected
                    )
                }
            }
        }
    }
}
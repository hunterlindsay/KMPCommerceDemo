package com.hunterlindsay.kmpcommercedemo.android.ui.core.browse

import androidx.compose.ui.geometry.Rect
import com.hunterlindsay.kmpcommercedemo.concerns.products.Product

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

data class SelectedProductPresentation(
    val product: Product,
    val sourceBounds: Rect
)
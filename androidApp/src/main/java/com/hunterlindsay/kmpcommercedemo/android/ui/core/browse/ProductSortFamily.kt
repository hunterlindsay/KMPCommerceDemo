package com.hunterlindsay.kmpcommercedemo.android.ui.core.browse

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

enum class ProductSortFamily(
    val inactiveTitle: String
) {
    Recent(
        inactiveTitle = "RECENT"
    ),
    Name(
        inactiveTitle = "NAME"
    ),
    Price(
        inactiveTitle = "PRICE"
    ),
    Rating(
        inactiveTitle = "RATING"
    ),
    Quantity(
        inactiveTitle = "QTY"
    )
}
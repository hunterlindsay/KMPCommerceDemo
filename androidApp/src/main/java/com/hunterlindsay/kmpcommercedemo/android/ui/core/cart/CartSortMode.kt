package com.hunterlindsay.kmpcommercedemo.android.ui.core.cart

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

enum class CartSortMode(
    val title: String
) {
    Recent(
        title = "RECENT"
    ),
    Name(
        title = "NAME"
    ),
    Price(
        title = "PRICE"
    ),
    Quantity(
        title = "QTY"
    )
}
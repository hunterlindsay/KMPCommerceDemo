package com.hunterlindsay.kmpcommercedemo.concerns.cart

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

data class CartServiceState(
    val items: List<CartItem> = emptyList()
) {
    val totalItemCount: Int
        get() = items.sumOf { it.quantity }

    val subtotal: Double
        get() = items.sumOf { it.lineSubtotal }

    val discountedTotal: Double
        get() = items.sumOf { it.lineDiscountedTotal }

    val totalDiscount: Double
        get() = subtotal - discountedTotal

    val isEmpty: Boolean
        get() = items.isEmpty()
}
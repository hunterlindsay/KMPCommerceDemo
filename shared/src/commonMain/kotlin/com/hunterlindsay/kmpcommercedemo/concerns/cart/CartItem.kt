package com.hunterlindsay.kmpcommercedemo.concerns.cart

import com.hunterlindsay.kmpcommercedemo.concerns.products.Product

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

data class CartItem(
    val product: Product,
    val quantity: Int
) {
    val lineSubtotal: Double
        get() = product.price * quantity

    val lineDiscountedTotal: Double
        get() {
            val discountMultiplier = 1 - (product.discountPercentage / 100)
            return product.price * discountMultiplier * quantity
        }
}
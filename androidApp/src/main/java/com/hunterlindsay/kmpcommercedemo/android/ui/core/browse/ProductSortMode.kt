package com.hunterlindsay.kmpcommercedemo.android.ui.core.sort

import com.hunterlindsay.kmpcommercedemo.android.ui.core.browse.ProductSortFamily

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

enum class ProductSortMode(
    val family: ProductSortFamily,
    val activeTitle: String
) {
    RecentNewest(
        family = ProductSortFamily.Recent,
        activeTitle = "MOST RECENT"
    ),
    RecentOldest(
        family = ProductSortFamily.Recent,
        activeTitle = "OLDEST"
    ),
    NameAscending(
        family = ProductSortFamily.Name,
        activeTitle = "A TO Z"
    ),
    NameDescending(
        family = ProductSortFamily.Name,
        activeTitle = "Z TO A"
    ),
    PriceLowest(
        family = ProductSortFamily.Price,
        activeTitle = "LOWEST"
    ),
    PriceHighest(
        family = ProductSortFamily.Price,
        activeTitle = "HIGHEST"
    ),
    RatingHighest(
        family = ProductSortFamily.Rating,
        activeTitle = "BEST"
    ),
    RatingLowest(
        family = ProductSortFamily.Rating,
        activeTitle = "LOW RATING"
    ),
    QuantityHighest(
        family = ProductSortFamily.Quantity,
        activeTitle = "MOST QTY"
    ),
    QuantityLowest(
        family = ProductSortFamily.Quantity,
        activeTitle = "LEAST QTY"
    )
}

fun ProductSortMode?.nextModeForFamily(
    family: ProductSortFamily
): ProductSortMode? {
    return when (family) {
        ProductSortFamily.Recent -> {
            when (this) {
                ProductSortMode.RecentNewest -> ProductSortMode.RecentOldest
                ProductSortMode.RecentOldest -> null
                else -> ProductSortMode.RecentNewest
            }
        }

        ProductSortFamily.Name -> {
            when (this) {
                ProductSortMode.NameAscending -> ProductSortMode.NameDescending
                ProductSortMode.NameDescending -> null
                else -> ProductSortMode.NameAscending
            }
        }

        ProductSortFamily.Price -> {
            when (this) {
                ProductSortMode.PriceLowest -> ProductSortMode.PriceHighest
                ProductSortMode.PriceHighest -> null
                else -> ProductSortMode.PriceLowest
            }
        }

        ProductSortFamily.Rating -> {
            when (this) {
                ProductSortMode.RatingHighest -> ProductSortMode.RatingLowest
                ProductSortMode.RatingLowest -> null
                else -> ProductSortMode.RatingHighest
            }
        }

        ProductSortFamily.Quantity -> {
            when (this) {
                ProductSortMode.QuantityHighest -> ProductSortMode.QuantityLowest
                ProductSortMode.QuantityLowest -> null
                else -> ProductSortMode.QuantityHighest
            }
        }
    }
}
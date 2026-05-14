package com.hunterlindsay.kmpcommercedemo.android.ui.core.browse

import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductCategory

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class BrowseCategoryMapper {

    fun mapProductCategories(
        productCategories: List<ProductCategory>
    ): List<BrowseCategory> {
        return productCategories.map { productCategory ->
            BrowseCategory(
                id = productCategory.id,
                title = productCategory.name,
                children = emptyList()
            )
        }
    }
}
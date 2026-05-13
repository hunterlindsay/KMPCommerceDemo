package com.hunterlindsay.kmpcommercedemo.android.ui.core.browse

import com.hunterlindsay.kmpcommercedemo.concerns.products.Product
import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductCategory

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class BrowseCategoryMapper {

    fun mapProductCategories(
        productCategories: List<ProductCategory>,
        productsByCategoryId: Map<String, List<Product>>
    ): List<BrowseCategory> {
        return productCategories.map { productCategory ->
            BrowseCategory(
                id = productCategory.id,
                title = productCategory.name,
                children = makeTagChildren(
                    categoryId = productCategory.id,
                    products = productsByCategoryId[productCategory.id].orEmpty()
                )
            )
        }
    }

    private fun makeTagChildren(
        categoryId: String,
        products: List<Product>
    ): List<BrowseCategory> {
        return products
            .flatMap { product ->
                product.tags
            }
            .distinct()
            .sorted()
            .map { tag ->
                BrowseCategory(
                    id = "$categoryId-$tag",
                    title = tag.replaceFirstChar { character ->
                        character.uppercase()
                    },
                    children = emptyList()
                )
            }
    }
}
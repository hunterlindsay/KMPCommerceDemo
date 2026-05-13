package com.hunterlindsay.kmpcommercedemo.concerns.products

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

data class ProductServiceState(
    val isLoadingProducts: Boolean = false,
    val isLoadingCategories: Boolean = false,
    val products: List<Product> = emptyList(),
    val categories: List<ProductCategory> = emptyList(),
    val productsByCategoryId: Map<String, List<Product>> = emptyMap(),
    val loadingCategoryIds: Set<String> = emptySet(),
    val errorMessage: String? = null
)
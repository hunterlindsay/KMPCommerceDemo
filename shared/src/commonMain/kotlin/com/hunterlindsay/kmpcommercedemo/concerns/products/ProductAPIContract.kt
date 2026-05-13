package com.hunterlindsay.kmpcommercedemo.concerns.products

import com.hunterlindsay.kmpcommercedemo.core.networking.rest_client.RESTCallResponse

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

interface ProductsAPIContract {
    suspend fun retrieveProducts(): RESTCallResponse
    suspend fun retrieveProduct(productId: Int): RESTCallResponse
    suspend fun retrieveCategories(): RESTCallResponse
    suspend fun retrieveProductsForCategory(categoryId: String): RESTCallResponse
    suspend fun searchProducts(query: String): RESTCallResponse
}
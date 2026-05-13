package com.hunterlindsay.kmpcommercedemo.concerns.products

import com.hunterlindsay.kmpcommercedemo.core.networking.rest_client.RESTCall
import com.hunterlindsay.kmpcommercedemo.core.networking.rest_client.RESTCallResponse
import com.hunterlindsay.kmpcommercedemo.core.networking.rest_client.RESTCallType
import com.hunterlindsay.kmpcommercedemo.core.networking.rest_client.RESTClientService
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class ProductsAPI(
    private val restClientService: RESTClientService
): ProductsAPIContract {
    override suspend fun retrieveProducts(): RESTCallResponse {
        val restCall = RESTCall(
            callDescription = "Retrieve products",
            callType = RESTCallType.GET,
            urlString = "$BASE_URL/products"
        )

        return restClientService.makeRESTCall(restCall)
    }

    override suspend fun retrieveProduct(productId: Int): RESTCallResponse {
        val restCall = RESTCall(
            callDescription = "Retrieve product detail",
            callType = RESTCallType.GET,
            urlString = "$BASE_URL/products/$productId"
        )

        return restClientService.makeRESTCall(restCall)
    }

    override suspend fun retrieveCategories(): RESTCallResponse {
        val restCall = RESTCall(
            callDescription = "Retrieve product categories",
            callType = RESTCallType.GET,
            urlString = "$BASE_URL/products/categories"
        )

        return restClientService.makeRESTCall(restCall)
    }

    override suspend fun retrieveProductsForCategory(categoryId: String): RESTCallResponse {
        val restCall = RESTCall(
            callDescription = "Retrieve products for category",
            callType = RESTCallType.GET,
            urlString = "$BASE_URL/products/category/$categoryId"
        )

        return restClientService.makeRESTCall(restCall)
    }

    override suspend fun searchProducts(query: String): RESTCallResponse {
        val restCall = RESTCall(
            callDescription = "Search products",
            callType = RESTCallType.GET,
            urlString = "$BASE_URL/products/search?q=$query"
        )

        return restClientService.makeRESTCall(restCall)
    }

    private companion object {
        const val BASE_URL = "https://dummyjson.com"
    }
}
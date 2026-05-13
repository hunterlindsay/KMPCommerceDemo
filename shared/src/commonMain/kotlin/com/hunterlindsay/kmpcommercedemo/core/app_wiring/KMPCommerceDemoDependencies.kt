package com.hunterlindsay.kmpcommercedemo.core.app_wiring

import com.hunterlindsay.kmpcommercedemo.concerns.cart.CartService
import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductService
import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductsAPI
import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductsParser
import com.hunterlindsay.kmpcommercedemo.concerns.saved.SavedProductService
import com.hunterlindsay.kmpcommercedemo.core.networking.rest_client.RESTClientService
import io.ktor.client.HttpClient

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class KMPCommerceDemoDependencies {
    private val httpClient: HttpClient by lazy {
        HttpClient()
    }

    private val restClientService: RESTClientService by lazy {
        RESTClientService(httpClient)
    }

    private val productsAPI: ProductsAPI by lazy {
        ProductsAPI(restClientService)
    }

    private val productsParser: ProductsParser by lazy {
        ProductsParser()
    }

    val productService: ProductService by lazy {
        ProductService(
            productsAPI = productsAPI,
            productsParser = productsParser
        )
    }

    val cartService: CartService by lazy {
        CartService()
    }

    val savedProductService: SavedProductService by lazy {
        SavedProductService()
    }
}
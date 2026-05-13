package com.hunterlindsay.kmpcommercedemo.kotlin.product

import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductService
import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductsAPIContract
import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductsParser
import com.hunterlindsay.kmpcommercedemo.core.networking.rest_client.RESTCallResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlinx.coroutines.test.runTest

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class ProductServiceTest {

    @Test
    fun loadProductsUpdatesStateWithParsedProducts() = runTest {
        val productService = ProductService(
            productsAPI = FakeProductsAPI(
                productsResponse = RESTCallResponse(
                    success = true,
                    statusCode = 200,
                    responseBodyString = successfulProductsJson
                )
            ),
            productsParser = ProductsParser()
        )

        productService.loadProducts()

        val state = productService.state.value

        assertFalse(state.isLoading)
        assertEquals(null, state.errorMessage)
        assertEquals(1, state.products.size)

        val product = state.products.first()

        assertEquals(1, product.id)
        assertEquals("Test Product", product.title)
        assertEquals("beauty", product.categoryId)
        assertEquals(9.99, product.price)
    }

    @Test
    fun loadProductsUpdatesStateWithErrorWhenApiFails() = runTest {
        val productService = ProductService(
            productsAPI = FakeProductsAPI(
                productsResponse = RESTCallResponse(
                    success = false,
                    statusCode = 500,
                    responseBodyString = null,
                    errorMessage = "Server error"
                )
            ),
            productsParser = ProductsParser()
        )

        productService.loadProducts()

        val state = productService.state.value

        assertFalse(state.isLoading)
        assertEquals("Server error", state.errorMessage)
        assertEquals(emptyList(), state.products)
    }

    @Test
    fun loadCategoriesUpdatesStateWithParsedCategories() = runTest {
        val productService = ProductService(
            productsAPI = FakeProductsAPI(
                categoriesResponse = RESTCallResponse(
                    success = true,
                    statusCode = 200,
                    responseBodyString = successfulCategoriesJson
                )
            ),
            productsParser = ProductsParser()
        )

        productService.loadCategories()

        val state = productService.state.value

        assertEquals(null, state.errorMessage)
        assertEquals(2, state.categories.size)

        assertEquals("beauty", state.categories[0].id)
        assertEquals("Beauty", state.categories[0].name)
        assertEquals(null, state.categories[0].parentId)

        assertEquals("mens-shoes", state.categories[1].id)
        assertEquals("Mens Shoes", state.categories[1].name)
        assertEquals(null, state.categories[1].parentId)
    }

    @Test
    fun loadProductsSetsLoadingBackToFalseAfterSuccess() = runTest {
        val productService = ProductService(
            productsAPI = FakeProductsAPI(
                productsResponse = RESTCallResponse(
                    success = true,
                    statusCode = 200,
                    responseBodyString = successfulProductsJson
                )
            ),
            productsParser = ProductsParser()
        )

        assertFalse(productService.state.value.isLoading)

        productService.loadProducts()

        assertFalse(productService.state.value.isLoading)
    }

    private class FakeProductsAPI(
        private val productsResponse: RESTCallResponse = RESTCallResponse(
            success = false,
            statusCode = null,
            responseBodyString = null,
            errorMessage = "Products response not set"
        ),
        private val categoriesResponse: RESTCallResponse = RESTCallResponse(
            success = false,
            statusCode = null,
            responseBodyString = null,
            errorMessage = "Categories response not set"
        )
    ) : ProductsAPIContract {

        override suspend fun retrieveProducts(): RESTCallResponse {
            return productsResponse
        }

        override suspend fun retrieveProduct(productId: Int): RESTCallResponse {
            return productsResponse
        }

        override suspend fun retrieveCategories(): RESTCallResponse {
            return categoriesResponse
        }

        override suspend fun retrieveProductsForCategory(categoryId: String): RESTCallResponse {
            return productsResponse
        }

        override suspend fun searchProducts(query: String): RESTCallResponse {
            return productsResponse
        }
    }

    private companion object {
        val successfulProductsJson = """
            {
              "products": [
                {
                  "id": 1,
                  "title": "Test Product",
                  "description": "A product used for service testing.",
                  "category": "beauty",
                  "price": 9.99,
                  "discountPercentage": 10.5,
                  "rating": 4.25,
                  "stock": 12,
                  "tags": ["beauty", "test"],
                  "brand": "Test Brand",
                  "sku": "TEST-SKU-001",
                  "dimensions": {
                    "width": 10.0,
                    "height": 20.0,
                    "depth": 30.0
                  },
                  "warrantyInformation": "1 year warranty",
                  "shippingInformation": "Ships tomorrow",
                  "availabilityStatus": "In Stock",
                  "reviews": [],
                  "returnPolicy": "30 days return policy",
                  "minimumOrderQuantity": 2,
                  "images": ["https://example.com/image.webp"],
                  "thumbnail": "https://example.com/thumbnail.webp"
                }
              ],
              "total": 1,
              "skip": 0,
              "limit": 30
            }
        """.trimIndent()

        val successfulCategoriesJson = """
            [
              {
                "slug": "beauty",
                "name": "Beauty",
                "url": "https://dummyjson.com/products/category/beauty"
              },
              {
                "slug": "mens-shoes",
                "name": "Mens Shoes",
                "url": "https://dummyjson.com/products/category/mens-shoes"
              }
            ]
        """.trimIndent()
    }
}
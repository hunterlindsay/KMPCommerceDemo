package com.hunterlindsay.kmpcommercedemo.concerns.products

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class ProductService(
    private val productsAPI: ProductsAPIContract,
    private val productsParser: ProductsParser
) {
    private val _state = MutableStateFlow(ProductServiceState())

    val state: StateFlow<ProductServiceState> = _state

    suspend fun loadProducts() {
        _state.value = _state.value.copy(
            isLoadingProducts = true,
            errorMessage = null
        )

        val response = productsAPI.retrieveProducts()

        if (!response.success || response.responseBodyString == null) {
            _state.value = _state.value.copy(
                isLoadingProducts = false,
                errorMessage = response.errorMessage ?: "Could not load products"
            )
            return
        }

        try {
            val products = productsParser.parseProducts(response.responseBodyString)

            _state.value = _state.value.copy(
                isLoadingProducts = false,
                products = products,
                errorMessage = null
            )
        } catch (throwable: Throwable) {
            _state.value = _state.value.copy(
                isLoadingProducts = false,
                errorMessage = throwable.message ?: "Could not parse products"
            )
        }
    }

    suspend fun loadCategories() {
        _state.value = _state.value.copy(
            isLoadingCategories = true,
            errorMessage = null
        )

        val response = productsAPI.retrieveCategories()

        if (!response.success || response.responseBodyString == null) {
            _state.value = _state.value.copy(
                isLoadingCategories = false,
                errorMessage = response.errorMessage ?: "Could not load categories"
            )
            return
        }

        try {
            val categories = productsParser.parseCategories(response.responseBodyString)

            _state.value = _state.value.copy(
                isLoadingCategories = false,
                categories = categories,
                errorMessage = null
            )
        } catch (throwable: Throwable) {
            _state.value = _state.value.copy(
                isLoadingCategories = false,
                errorMessage = throwable.message ?: "Could not parse categories"
            )
        }
    }

    suspend fun loadProductsForCategory(categoryId: String) {
        if (_state.value.productsByCategoryId.containsKey(categoryId)) {
            return
        }

        if (_state.value.loadingCategoryIds.contains(categoryId)) {
            return
        }

        _state.value = _state.value.copy(
            loadingCategoryIds = _state.value.loadingCategoryIds + categoryId,
            errorMessage = null
        )

        val response = productsAPI.retrieveProductsForCategory(categoryId)

        if (!response.success || response.responseBodyString == null) {
            _state.value = _state.value.copy(
                loadingCategoryIds = _state.value.loadingCategoryIds - categoryId,
                errorMessage = response.errorMessage ?: "Could not load products for category"
            )
            return
        }

        try {
            val products = productsParser.parseProducts(response.responseBodyString)

            _state.value = _state.value.copy(
                productsByCategoryId = _state.value.productsByCategoryId + (categoryId to products),
                loadingCategoryIds = _state.value.loadingCategoryIds - categoryId,
                errorMessage = null
            )
        } catch (throwable: Throwable) {
            _state.value = _state.value.copy(
                loadingCategoryIds = _state.value.loadingCategoryIds - categoryId,
                errorMessage = throwable.message ?: "Could not parse products for category"
            )
        }
    }
}
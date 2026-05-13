package com.hunterlindsay.kmpcommercedemo.concerns.cart

import com.hunterlindsay.kmpcommercedemo.concerns.products.Product
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class CartServiceTest {
    @Test
    fun addProductAddsNewItemWithQuantityOne() {
        val cartService = CartService()
        val product = makeTestProduct(id = 1, price = 10.0)

        cartService.addProduct(product)

        val state = cartService.state.value

        assertEquals(1, state.items.size)
        assertEquals(product.id, state.items.first().product.id)
        assertEquals(1, state.items.first().quantity)
    }

    @Test
    fun addProductAgainIncreasesQuantity() {
        val cartService = CartService()
        val product = makeTestProduct(id = 1, price = 10.0)

        cartService.addProduct(product)
        cartService.addProduct(product)

        val state = cartService.state.value

        assertEquals(1, state.items.size)
        assertEquals(2, state.items.first().quantity)
    }

    @Test
    fun increaseQuantityIncreasesExistingItemQuantity() {
        val cartService = CartService()
        val product = makeTestProduct(id = 1, price = 10.0)

        cartService.addProduct(product)
        cartService.increaseQuantity(product.id)

        val state = cartService.state.value

        assertEquals(2, state.items.first().quantity)
    }

    @Test
    fun decreaseQuantityDecreasesExistingItemQuantity() {
        val cartService = CartService()
        val product = makeTestProduct(id = 1, price = 10.0)

        cartService.addProduct(product)
        cartService.addProduct(product)
        cartService.decreaseQuantity(product.id)

        val state = cartService.state.value

        assertEquals(1, state.items.first().quantity)
    }

    @Test
    fun decreaseQuantityRemovesItemWhenQuantityReachesZero() {
        val cartService = CartService()
        val product = makeTestProduct(id = 1, price = 10.0)

        cartService.addProduct(product)
        cartService.decreaseQuantity(product.id)

        val state = cartService.state.value

        assertTrue(state.items.isEmpty())
        assertTrue(state.isEmpty)
    }

    @Test
    fun removeProductRemovesMatchingItem() {
        val cartService = CartService()
        val productOne = makeTestProduct(id = 1, price = 10.0)
        val productTwo = makeTestProduct(id = 2, price = 20.0)

        cartService.addProduct(productOne)
        cartService.addProduct(productTwo)
        cartService.removeProduct(productOne.id)

        val state = cartService.state.value

        assertEquals(1, state.items.size)
        assertEquals(productTwo.id, state.items.first().product.id)
    }

    @Test
    fun clearCartRemovesAllItems() {
        val cartService = CartService()
        val productOne = makeTestProduct(id = 1, price = 10.0)
        val productTwo = makeTestProduct(id = 2, price = 20.0)

        cartService.addProduct(productOne)
        cartService.addProduct(productTwo)
        cartService.clearCart()

        val state = cartService.state.value

        assertTrue(state.items.isEmpty())
        assertEquals(0, state.totalItemCount)
        assertEquals(0.0, state.subtotal)
    }

    @Test
    fun cartTotalsAreCalculatedCorrectly() {
        val cartService = CartService()
        val productOne = makeTestProduct(
            id = 1,
            price = 10.0,
            discountPercentage = 10.0
        )
        val productTwo = makeTestProduct(
            id = 2,
            price = 20.0,
            discountPercentage = 25.0
        )

        cartService.addProduct(productOne)
        cartService.addProduct(productOne)
        cartService.addProduct(productTwo)

        val state = cartService.state.value

        assertEquals(3, state.totalItemCount)
        assertEquals(40.0, state.subtotal)
        assertEquals(33.0, state.discountedTotal)
        assertEquals(7.0, state.totalDiscount)
    }

    private fun makeTestProduct(
        id: Int,
        price: Double,
        discountPercentage: Double = 0.0
    ): Product {
        return Product(
            id = id,
            title = "Product $id",
            description = "Test product $id",
            categoryId = "test-category",
            price = price,
            discountPercentage = discountPercentage,
            rating = 4.0,
            stock = 10,
            brand = "Test Brand",
            sku = "SKU-$id",
            thumbnail = "https://example.com/product-$id-thumbnail.webp",
            images = emptyList(),
            tags = emptyList(),
            dimensions = null,
            warrantyInformation = null,
            shippingInformation = null,
            availabilityStatus = "In Stock",
            returnPolicy = null,
            minimumOrderQuantity = null,
            reviews = emptyList()
        )
    }
}
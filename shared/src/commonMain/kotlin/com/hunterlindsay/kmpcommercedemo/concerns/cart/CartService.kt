package com.hunterlindsay.kmpcommercedemo.concerns.cart

import com.hunterlindsay.kmpcommercedemo.concerns.products.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class CartService {
    private val _state = MutableStateFlow(CartServiceState())
    val state: StateFlow<CartServiceState> = _state

    fun addProduct(product: Product) {
        // 1. Product is not already in cart
        //    -> add a new CartItem with quantity 1
        //
        // 2. Product is already in cart
        //    -> keep the same cart items, but increase that product’s quantity

        val existingItem = _state.value.items.firstOrNull { item ->
            item.product.id == product.id
        }

        val updatedItems = if (existingItem == null) {
            _state.value.items + CartItem(
                product = product,
                quantity = 1
            )
        } else {
            _state.value.items.map { item ->
                if (item.product.id == product.id) {
                    item.copy(quantity = item.quantity + 1)
                } else {
                    item
                }
            }
        }

        //We augment above we we do the actual change here so that we only update the state once, which is more efficient and also prevents any potential issues with multiple state updates in a row.
        _state.value = _state.value.copy(
            items = updatedItems
        )
    }

    fun removeProduct(productId: Int) {
        _state.value = _state.value.copy(
            items = _state.value.items.filterNot { item ->
                item.product.id == productId
            }
        )
    }

    fun increaseQuantity(productId: Int) {
        _state.value = _state.value.copy(
            items = _state.value.items.map { item ->
                if (item.product.id == productId) {
                    item.copy(quantity = item.quantity + 1)
                } else {
                    item
                }
            }
        )
    }

    fun decreaseQuantity(productId: Int) {
        val updatedItems = _state.value.items.mapNotNull { item ->
            if (item.product.id != productId) {
                item
            } else {
                val updatedQuantity = item.quantity - 1

                if (updatedQuantity <= 0) {
                    null
                } else {
                    item.copy(quantity = updatedQuantity)
                }
            }
        }

        _state.value = _state.value.copy(
            items = updatedItems
        )
    }

    fun clearCart() {
        _state.value = CartServiceState()
    }
}
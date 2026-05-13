package com.hunterlindsay.kmpcommercedemo.concerns.saved

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class SavedProductService {
    private val _state = MutableStateFlow(SavedProductServiceState())
    val state: StateFlow<SavedProductServiceState> = _state

    fun saveProduct(productId: Int) {
        _state.value = _state.value.copy(
            savedProductIds = _state.value.savedProductIds + productId
        )
    }

    fun unsaveProduct(productId: Int) {
        _state.value = _state.value.copy(
            savedProductIds = _state.value.savedProductIds - productId
        )
    }

    fun toggleSaved(productId: Int) {
        if (isSaved(productId)) {
            unsaveProduct(productId)
        } else {
            saveProduct(productId)
        }
    }

    fun isSaved(productId: Int): Boolean {
        return _state.value.savedProductIds.contains(productId)
    }

    fun clearSaved() {
        _state.value = SavedProductServiceState()
    }
}
package com.hunterlindsay.kmpcommercedemo.kotlin

import com.hunterlindsay.kmpcommercedemo.concerns.saved.SavedProductService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class SavedProductServiceTest {

    @Test
    fun saveProductAddsProductId() {
        val savedProductService = SavedProductService()

        savedProductService.saveProduct(1)

        val state = savedProductService.state.value

        assertTrue(state.savedProductIds.contains(1))
        assertEquals(1, state.savedCount)
        assertFalse(state.isEmpty)
    }

    @Test
    fun unsaveProductRemovesProductId() {
        val savedProductService = SavedProductService()

        savedProductService.saveProduct(1)
        savedProductService.unsaveProduct(1)

        val state = savedProductService.state.value

        assertFalse(state.savedProductIds.contains(1))
        assertEquals(0, state.savedCount)
        assertTrue(state.isEmpty)
    }

    @Test
    fun toggleSavedAddsProductWhenNotSaved() {
        val savedProductService = SavedProductService()

        savedProductService.toggleSaved(1)

        assertTrue(savedProductService.isSaved(1))
        assertEquals(setOf(1), savedProductService.state.value.savedProductIds)
    }

    @Test
    fun toggleSavedRemovesProductWhenAlreadySaved() {
        val savedProductService = SavedProductService()

        savedProductService.saveProduct(1)
        savedProductService.toggleSaved(1)

        assertFalse(savedProductService.isSaved(1))
        assertTrue(savedProductService.state.value.savedProductIds.isEmpty())
    }

    @Test
    fun savingSameProductTwiceDoesNotDuplicateIt() {
        val savedProductService = SavedProductService()

        savedProductService.saveProduct(1)
        savedProductService.saveProduct(1)

        val state = savedProductService.state.value

        assertEquals(1, state.savedCount)
        assertEquals(setOf(1), state.savedProductIds)
    }

    @Test
    fun clearSavedRemovesAllSavedProducts() {
        val savedProductService = SavedProductService()

        savedProductService.saveProduct(1)
        savedProductService.saveProduct(2)
        savedProductService.clearSaved()

        val state = savedProductService.state.value

        assertTrue(state.savedProductIds.isEmpty())
        assertEquals(0, state.savedCount)
        assertTrue(state.isEmpty)
    }
}
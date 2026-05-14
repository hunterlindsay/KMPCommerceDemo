package com.hunterlindsay.kmpcommercedemo.android.ui.core.saved

import android.content.Context

/**
 * Created by Hunter Lindsay on 14/05/2026.
 */

class SavedProductsPersistence(
    context: Context
) {
    private val sharedPreferences = context.applicationContext.getSharedPreferences(
        sharedPreferencesName,
        Context.MODE_PRIVATE
    )

    fun loadSavedProductIds(): Set<Int> {
        val storedValue = sharedPreferences.getString(
            savedProductIdsKey,
            ""
        ).orEmpty()

        if (storedValue.isBlank()) {
            return emptySet()
        }

        return storedValue
            .split(idSeparator)
            .mapNotNull { value ->
                value.toIntOrNull()
            }
            .toSet()
    }

    fun saveProductIds(
        productIds: Set<Int>
    ) {
        val storedValue = productIds
            .sorted()
            .joinToString(separator = idSeparator)

        sharedPreferences
            .edit()
            .putString(
                savedProductIdsKey,
                storedValue
            )
            .apply()
    }

    private companion object {
        const val sharedPreferencesName = "KMPCommerceDemoSavedProducts"
        const val savedProductIdsKey = "savedProductIds"
        const val idSeparator = ","
    }
}
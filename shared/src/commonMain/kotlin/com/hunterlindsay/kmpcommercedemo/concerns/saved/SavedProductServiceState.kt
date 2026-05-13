package com.hunterlindsay.kmpcommercedemo.concerns.saved

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

data class SavedProductServiceState(
    val savedProductIds: Set<Int> = emptySet()
) {
    val savedCount: Int
        get() = savedProductIds.size

    val isEmpty: Boolean
        get() = savedProductIds.isEmpty()
}
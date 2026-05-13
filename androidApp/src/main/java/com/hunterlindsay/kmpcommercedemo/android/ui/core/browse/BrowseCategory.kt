package com.hunterlindsay.kmpcommercedemo.android.ui.core.browse

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

data class BrowseCategory(
    val id: String,
    val title: String,
    val children: List<BrowseCategory> = emptyList()
)
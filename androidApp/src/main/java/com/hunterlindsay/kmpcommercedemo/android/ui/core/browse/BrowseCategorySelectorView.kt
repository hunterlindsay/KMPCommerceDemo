package com.hunterlindsay.kmpcommercedemo.android.ui.browse

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hunterlindsay.kmpcommercedemo.android.ui.core.browse.BrowseCategory

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BrowseCategorySelectorView(
    categories: List<BrowseCategory>,
    loadingCategoryIds: Set<String>,
    revealedCategoryCount: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onCategoryExpanded: (String) -> Unit = {},
    onCategorySelected: (BrowseCategory) -> Unit = {}
) {
    val listState = rememberLazyListState()

    var expandedCategoryId by remember {
        mutableStateOf<String?>(null)
    }

    var selectedCategoryId by remember {
        mutableStateOf<String?>(null)
    }

    val revealedCategories = categories.take(revealedCategoryCount)

    LaunchedEffect(expandedCategoryId, revealedCategories) {
        val categoryId = expandedCategoryId ?: return@LaunchedEffect

        val index = revealedCategories.indexOfFirst { category ->
            category.id == categoryId
        }

        if (index >= 0) {
            listState.animateScrollToItem(index)
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(
            items = revealedCategories,
            key = { _, category ->
                category.id
            }
        ) { index, category ->
            AnimatedVisibility(
                visible = index < revealedCategoryCount,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 170)
                ) + slideInVertically(
                    animationSpec = tween(durationMillis = 260),
                    initialOffsetY = { fullHeight ->
                        fullHeight * 2
                    }
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 120)
                ) + slideOutVertically(
                    animationSpec = tween(durationMillis = 180),
                    targetOffsetY = { fullHeight ->
                        fullHeight
                    }
                )
            ) {
                BrowseCategoryExpandableRowView(
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(durationMillis = 320)
                    ),
                    category = category,
                    isExpanded = expandedCategoryId == category.id,
                    isSelected = selectedCategoryId == category.id,
                    selectedCategoryId = selectedCategoryId,
                    isLoadingChildren = loadingCategoryIds.contains(category.id),
                    onParentClicked = {
                        selectedCategoryId = category.id

                        val isCurrentlyExpanded = expandedCategoryId == category.id

                        expandedCategoryId = if (isCurrentlyExpanded) {
                            null
                        } else {
                            category.id
                        }

                        if (!isCurrentlyExpanded) {
                            onCategoryExpanded(category.id)
                        }

                        onCategorySelected(category)
                    },
                    onChildClicked = { childCategory ->
                        selectedCategoryId = childCategory.id
                        onCategorySelected(childCategory)
                    }
                )
            }
        }
    }
}

@Composable
private fun BrowseCategoryExpandableRowView(
    category: BrowseCategory,
    isExpanded: Boolean,
    isSelected: Boolean,
    selectedCategoryId: String?,
    isLoadingChildren: Boolean,
    modifier: Modifier = Modifier,
    onParentClicked: () -> Unit,
    onChildClicked: (BrowseCategory) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        BrowseCategoryPillView(
            title = category.title,
            isSelected = isSelected,
            isChild = false,
            isExpanded = isExpanded,
            showsChevron = true,
            onClick = onParentClicked
        )

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn(
                animationSpec = tween(durationMillis = 160)
            ) + expandVertically(
                animationSpec = tween(durationMillis = 320),
                expandFrom = Alignment.Top
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 120)
            ) + shrinkVertically(
                animationSpec = tween(durationMillis = 300),
                shrinkTowards = Alignment.Top
            )
        ) {
            AnimatedContent(
                targetState = ChildContentState(
                    isLoading = isLoadingChildren,
                    children = category.children
                ),
                transitionSpec = {
                    fadeIn(
                        animationSpec = tween(durationMillis = 160)
                    ) + expandVertically(
                        animationSpec = tween(durationMillis = 280),
                        expandFrom = Alignment.Top
                    ) togetherWith fadeOut(
                        animationSpec = tween(durationMillis = 120)
                    ) + shrinkVertically(
                        animationSpec = tween(durationMillis = 240),
                        shrinkTowards = Alignment.Top
                    ) using SizeTransform(
                        clip = false,
                        sizeAnimationSpec = { _, _ ->
                            tween(durationMillis = 300)
                        }
                    )
                },
                label = "BrowseCategoryChildContent"
            ) { childContentState ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 26.dp),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    if (childContentState.isLoading) {
                        BrowseChildCategorySkeletonView()
                    } else {
                        childContentState.children.forEach { childCategory ->
                            BrowseCategoryPillView(
                                title = childCategory.title,
                                isSelected = selectedCategoryId == childCategory.id,
                                isChild = true,
                                onClick = {
                                    onChildClicked(childCategory)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class ChildContentState(
    val isLoading: Boolean,
    val children: List<BrowseCategory>
)
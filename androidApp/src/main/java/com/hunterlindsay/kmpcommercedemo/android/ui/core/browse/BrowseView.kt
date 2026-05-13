package com.hunterlindsay.kmpcommercedemo.android.ui.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue
import com.hunterlindsay.kmpcommercedemo.android.ui.core.browse.BrowseCategoryMapper
import com.hunterlindsay.kmpcommercedemo.android.ui.core.browse.BrowseCategorySkeletonView
import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductService

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun BrowseView(
    productService: ProductService,
    revealedCategoryCount: Int,
    topOverlayHeight: Dp,
    bottomOverlayHeight: Dp,
    modifier: Modifier = Modifier,
    onCategoryExpanded: (String) -> Unit
) {
    val density = LocalDensity.current
    val productServiceState by productService.state.collectAsState()

    var browseTitleHeightPx by remember {
        mutableFloatStateOf(0f)
    }

    val browseTitleHeight = with(density) {
        browseTitleHeightPx.toDp()
    }

    val horizontalPadding = 22.dp

    val titleTopPadding = if (topOverlayHeight > 18.dp) {
        topOverlayHeight - 18.dp
    } else {
        0.dp
    }

    val titleToListSpacing = 8.dp

    val listTopPadding =
        titleTopPadding +
                browseTitleHeight +
                titleToListSpacing

    val listBottomPadding = bottomOverlayHeight + 24.dp

    val topFadeHeight =
        titleTopPadding +
                browseTitleHeight +
                40.dp

    val mapper = remember {
        BrowseCategoryMapper()
    }

    val browseCategories = remember(
        productServiceState.categories,
        productServiceState.productsByCategoryId
    ) {
        mapper.mapProductCategories(
            productCategories = productServiceState.categories,
            productsByCategoryId = productServiceState.productsByCategoryId
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding)
    ) {
        if (productServiceState.categories.isEmpty()) {
            BrowseCategorySkeletonView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = listTopPadding)
            )
        } else {
            BrowseCategorySelectorView(
                categories = browseCategories,
                loadingCategoryIds = productServiceState.loadingCategoryIds,
                revealedCategoryCount = revealedCategoryCount,
                contentPadding = PaddingValues(
                    top = listTopPadding,
                    bottom = listBottomPadding
                ),
                modifier = Modifier.fillMaxSize(),
                onCategoryExpanded = onCategoryExpanded
            )
        }

        BrowseTopFade(
            modifier = Modifier
                .fillMaxWidth()
                .height(topFadeHeight)
                .zIndex(1f)
        )

        BrowseTitleView(
            modifier = Modifier
                .padding(top = titleTopPadding)
                .onGloballyPositioned { coordinates ->
                    browseTitleHeightPx = coordinates.boundsInParent().height
                }
                .zIndex(2f)
        )
    }
}

@Composable
private fun BrowseTopFade(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            Brush.verticalGradient(
                colorStops = arrayOf(
                    0.00f to CommerceDarkBlue,
                    0.42f to CommerceDarkBlue.copy(alpha = 0.98f),
                    0.68f to CommerceDarkBlue.copy(alpha = 0.74f),
                    0.86f to CommerceDarkBlue.copy(alpha = 0.32f),
                    1.00f to CommerceDarkBlue.copy(alpha = 0f)
                )
            )
        )
    )
}

@Composable
private fun BrowseTitleView(
    modifier: Modifier = Modifier
) {
    val title = "BROWSE"
    val color = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.offset(x = (-0.5).dp),
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 38.sp,
            lineHeight = 38.sp,
            letterSpacing = 1.5.sp,
            maxLines = 1,
            color = color
        )

        Text(
            modifier = Modifier.offset(x = 0.5.dp),
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 38.sp,
            lineHeight = 38.sp,
            letterSpacing = 1.5.sp,
            maxLines = 1,
            color = color
        )

        Text(
            modifier = Modifier.offset(y = (-0.5).dp),
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 38.sp,
            lineHeight = 38.sp,
            letterSpacing = 1.5.sp,
            maxLines = 1,
            color = color
        )

        Text(
            modifier = Modifier.offset(y = 0.5.dp),
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 38.sp,
            lineHeight = 38.sp,
            letterSpacing = 1.5.sp,
            maxLines = 1,
            color = color
        )

        Text(
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 38.sp,
            lineHeight = 38.sp,
            letterSpacing = 1.5.sp,
            maxLines = 1,
            color = color
        )
    }
}
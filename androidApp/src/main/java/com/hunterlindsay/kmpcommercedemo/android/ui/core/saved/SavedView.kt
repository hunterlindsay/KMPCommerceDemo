package com.hunterlindsay.kmpcommercedemo.android.ui.saved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceWhite
import com.hunterlindsay.kmpcommercedemo.android.ui.core.browse.BrowseProductRowView
import com.hunterlindsay.kmpcommercedemo.concerns.products.Product

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun SavedView(
    products: List<Product>,
    topOverlayHeight: Dp,
    bottomOverlayHeight: Dp,
    modifier: Modifier = Modifier,
    onProductSelected: (Product, Rect) -> Unit
) {
    val horizontalPadding = 22.dp
    val titleTopPadding = topOverlayHeight
    val titleHeight = 38.dp
    val titleToListSpacing = 40.dp

    val listTopPadding =
        titleTopPadding +
                titleHeight +
                titleToListSpacing

    val listBottomPadding = bottomOverlayHeight + 220.dp

    val topFadeHeight =
        titleTopPadding +
                titleHeight +
                20.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding)
    ) {
        if (products.isEmpty()) {
            EmptySavedView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = listTopPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = listTopPadding,
                    bottom = listBottomPadding
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = products,
                    key = { product ->
                        product.id
                    }
                ) { product ->
                    BrowseProductRowView(
                        product = product,
                        onProductSelected = onProductSelected
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier.height(1.dp)
                    )
                }
            }
        }

        SavedTopFade(
            modifier = Modifier
                .fillMaxWidth()
                .height(topFadeHeight)
                .zIndex(1f)
        )

        SavedTitleView(
            modifier = Modifier
                .padding(top = titleTopPadding)
                .zIndex(2f)
        )
    }
}

@Composable
private fun EmptySavedView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = CommerceWhite.copy(alpha = 0.16f),
                shape = RoundedCornerShape(32.dp)
            )
            .padding(
                horizontal = 24.dp,
                vertical = 34.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Nothing saved yet",
                fontWeight = FontWeight.Black,
                fontSize = 24.sp,
                lineHeight = 25.sp,
                textAlign = TextAlign.Center,
                color = CommerceWhite
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Tap the star on a product to keep it here.",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center,
                color = CommerceWhite.copy(alpha = 0.66f)
            )
        }
    }
}

@Composable
private fun SavedTopFade(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            Brush.verticalGradient(
                colorStops = arrayOf(
                    0.00f to CommerceDarkBlue,
                    0.68f to CommerceDarkBlue,
                    0.86f to CommerceDarkBlue.copy(alpha = 0.70f),
                    0.96f to CommerceDarkBlue.copy(alpha = 0.28f),
                    1.00f to CommerceDarkBlue.copy(alpha = 0f)
                )
            )
        )
    )
}

@Composable
private fun SavedTitleView(
    modifier: Modifier = Modifier
) {
    val title = "SAVED"
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
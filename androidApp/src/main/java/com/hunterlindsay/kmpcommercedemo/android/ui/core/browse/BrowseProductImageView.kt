package com.hunterlindsay.kmpcommercedemo.android.ui.core.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue
import com.hunterlindsay.kmpcommercedemo.concerns.products.Product

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun BrowseProductImageView(
    product: Product,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(92.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(92.dp)
                .background(
                    color = CommerceDarkBlue.copy(alpha = 0.05f),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(84.dp)
                .background(
                    color = CommerceDarkBlue.copy(alpha = 0.07f),
                    shape = CircleShape
                )
        )

        AsyncImage(
            modifier = Modifier
                .size(82.dp)
                .clip(CircleShape),
            model = product.thumbnail,
            contentDescription = product.title,
            contentScale = ContentScale.Crop
        )
    }
}
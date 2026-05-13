package com.hunterlindsay.kmpcommercedemo.android.ui.introduction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun StartShoppingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(999.dp)

    Box(
        modifier = modifier
            .height(76.dp)
            .clip(shape)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = shape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.offset(y = (-1).dp),
            text = "Start Shopping!",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Black,
            fontSize = 24.sp,
            lineHeight = 24.sp,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
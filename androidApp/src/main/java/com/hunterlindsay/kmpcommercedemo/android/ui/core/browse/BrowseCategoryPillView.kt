package com.hunterlindsay.kmpcommercedemo.android.ui.browse

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceWhite

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun BrowseCategoryPillView(
    title: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    isChild: Boolean,
    isExpanded: Boolean = false,
    showsChevron: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            CommerceWhite
        },
        animationSpec = tween(durationMillis = 180),
        label = "BrowseCategoryPillBackgroundColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            CommerceDarkBlue
        },
        animationSpec = tween(durationMillis = 180),
        label = "BrowseCategoryPillTextColor"
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) {
            0.97f
        } else {
            1f
        },
        animationSpec = tween(durationMillis = 90),
        label = "BrowseCategoryPillScale"
    )

    val chevronRotation by animateFloatAsState(
        targetValue = if (isExpanded) {
            90f
        } else {
            0f
        },
        animationSpec = tween(durationMillis = 180),
        label = "BrowseCategoryChevronRotation"
    )

    val height: Dp = if (isChild) {
        44.dp
    } else {
        58.dp
    }

    val textSize = if (isChild) {
        15.sp
    } else {
        19.sp
    }

    val shape = RoundedCornerShape(999.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .height(height)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(
                start = if (isChild) 18.dp else 24.dp,
                end = if (isChild) 18.dp else 22.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.offset(y = (-1).dp),
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = textSize,
            lineHeight = textSize,
            maxLines = 1,
            color = textColor
        )

        Spacer(modifier = Modifier.weight(1f))

        if (showsChevron) {
            BrowseCategoryChevronView(
                modifier = Modifier
                    .size(18.dp)
                    .rotate(chevronRotation),
                color = CommerceDarkBlue
            )
        }
    }
}

@Composable
private fun BrowseCategoryChevronView(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
    ) {
        val strokeWidth = size.width * 0.18f

        drawLine(
            color = color,
            start = Offset(size.width * 0.35f, size.height * 0.20f),
            end = Offset(size.width * 0.65f, size.height * 0.50f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        drawLine(
            color = color,
            start = Offset(size.width * 0.65f, size.height * 0.50f),
            end = Offset(size.width * 0.35f, size.height * 0.80f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}
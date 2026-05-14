package com.hunterlindsay.kmpcommercedemo.android.ui.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceWhite

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun RibbonButtonView(
    title: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isPressed by interactionSource.collectIsPressedAsState()

    val selectedBackgroundColor = MaterialTheme.colorScheme.secondary

    val scale by animateFloatAsState(
        targetValue = if (isPressed) {
            0.94f
        } else {
            1f
        },
        animationSpec = tween(durationMillis = 90),
        label = "RibbonButtonScale"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            selectedBackgroundColor
        } else {
            CommerceWhite.copy(alpha = 0.16f)
        },
        animationSpec = tween(durationMillis = 160),
        label = "RibbonButtonBackgroundColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) {
            CommerceDarkBlue
        } else {
            CommerceWhite.copy(alpha = 0.76f)
        },
        animationSpec = tween(durationMillis = 160),
        label = "RibbonButtonTextColor"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .height(40.dp)
            .widthIn(min = 88.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(999.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = title,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(durationMillis = 140)
                ) togetherWith fadeOut(
                    animationSpec = tween(durationMillis = 100)
                )
            },
            label = "RibbonButtonTitle"
        ) { currentTitle ->
            Text(
                text = currentTitle,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                letterSpacing = 0.25.sp,
                maxLines = 1,
                color = textColor
            )
        }
    }
}
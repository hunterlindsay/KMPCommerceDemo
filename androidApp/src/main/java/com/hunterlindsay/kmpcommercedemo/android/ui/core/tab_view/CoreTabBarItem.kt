package com.hunterlindsay.kmpcommercedemo.android.ui.core.tab_view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceDarkBlue
import com.hunterlindsay.kmpcommercedemo.android.ui.CommerceWhite
import com.hunterlindsay.kmpcommercedemo.android.ui.core.CoreTabIconView

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun CoreTabBarItem(
    tab: CoreTab,
    isSelected: Boolean,
    badgeCount: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isPressed by interactionSource.collectIsPressedAsState()

    val contentColor by animateColorAsState(
        targetValue = if (isSelected) {
            CommerceWhite
        } else {
            MaterialTheme.colorScheme.primary
        },
        animationSpec = tween(durationMillis = 180),
        label = "CoreTabBarItemContentColor"
    )

    val contentScale by animateFloatAsState(
        targetValue = if (isPressed) {
            0.90f
        } else {
            1.0f
        },
        animationSpec = tween(durationMillis = 90),
        label = "CoreTabBarItemContentScale"
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (isPressed) {
            0.68f
        } else {
            1.0f
        },
        animationSpec = tween(durationMillis = 90),
        label = "CoreTabBarItemContentAlpha"
    )

    Column(
        modifier = modifier
            .width(104.dp)
            .height(72.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .scale(contentScale)
                .alpha(contentAlpha),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(36.dp),
                contentAlignment = Alignment.Center
            ) {
                CoreTabIconView(
                    tab = tab,
                    color = contentColor,
                    modifier = Modifier.size(30.dp)
                )

                if (badgeCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(
                                x = 3.dp,
                                y = (-3).dp
                            )
                            .size(19.dp)
                            .background(
                                color = CommerceWhite,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (badgeCount > 9) {
                                "9+"
                            } else {
                                badgeCount.toString()
                            },
                            fontWeight = FontWeight.Black,
                            fontSize = 9.sp,
                            lineHeight = 9.sp,
                            maxLines = 1,
                            color = CommerceDarkBlue
                        )
                    }
                }
            }

            Text(
                modifier = Modifier.offset(y = 0.dp),
                text = tab.title,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                maxLines = 1,
                color = contentColor
            )
        }
    }
}
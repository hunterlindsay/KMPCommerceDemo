package com.hunterlindsay.kmpcommercedemo.android.ui.introduction

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hunterlindsay.kmpcommercedemo.android.ui.RotatingDemoView
import com.hunterlindsay.kmpcommercedemo.android.ui.core.CoreView
import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductService
import kotlinx.coroutines.delay

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun OpeningScreen(
    productService: ProductService
) {
    var hasStartedShopping by remember {
        mutableStateOf(false)
    }

    var showCoreView by remember {
        mutableStateOf(false)
    }

    var startButtonBounds by remember {
        mutableStateOf<Rect?>(null)
    }

    val transitionDurationMillis = 850

    val outlineWidth by animateDpAsState(
        targetValue = if (hasStartedShopping) {
            0.dp
        } else {
            42.dp
        },
        animationSpec = tween(
            durationMillis = transitionDurationMillis
        ),
        label = "OpeningScreenOutlineWidth"
    )

    val titleOffsetY by animateDpAsState(
        targetValue = if (hasStartedShopping) {
            (-260).dp
        } else {
            0.dp
        },
        animationSpec = tween(
            durationMillis = transitionDurationMillis
        ),
        label = "OpeningScreenTitleOffset"
    )

    val nameOffsetY by animateDpAsState(
        targetValue = if (hasStartedShopping) {
            260.dp
        } else {
            0.dp
        },
        animationSpec = tween(
            durationMillis = transitionDurationMillis
        ),
        label = "OpeningScreenNameOffset"
    )

    LaunchedEffect(hasStartedShopping) {
        if (hasStartedShopping) {
            delay(transitionDurationMillis.toLong())
            showCoreView = true
        }
    }

    if (showCoreView) {
        CoreView(
            productService = productService,
            initialStartButtonBounds = startButtonBounds
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(22.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            RotatingDemoView(
                text = "KOTLIN  MULTIPLATFORM  DEMO",
                modifier = Modifier.fillMaxSize(),
                borderColor = MaterialTheme.colorScheme.onBackground,
                textColor = MaterialTheme.colorScheme.background,
                borderWidth = outlineWidth,
                textPathBorderWidth = 42.dp,
                cornerRadius = 56.dp,
                fontSize = 32.sp,
                minimumTextGroupGap = 220.dp,
                animationDurationMillis = 40000
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(58.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = titleOffsetY)
                        .alpha(if (hasStartedShopping) 0f else 1f),
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    text = "Commerce App Demo",
                    fontSize = 42.sp,
                    lineHeight = 48.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.weight(1f))

                StartShoppingButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .onGloballyPositioned { coordinates ->
                            startButtonBounds = coordinates.boundsInRoot()
                        },
                    onClick = {
                        hasStartedShopping = true
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = nameOffsetY)
                        .alpha(if (hasStartedShopping) 0f else 1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Hunter Lindsay",
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        text = "Senior Mobile Engineer",
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}
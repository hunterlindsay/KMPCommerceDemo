package com.hunterlindsay.kmpcommercedemo.android.ui.core.transition_supporting_views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.hunterlindsay.kmpcommercedemo.android.ui.introduction.StartShoppingButton

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun IntroMatchedStartButtonMeasurementView(
    sourceButtonAlpha: Float,
    onStartButtonPositioned: (Rect) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(58.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0f),
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
                        .alpha(sourceButtonAlpha)
                        .onGloballyPositioned { coordinates ->
                            onStartButtonPositioned(coordinates.boundsInRoot())
                        },
                    onClick = {}
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0f),
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
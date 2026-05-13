package com.hunterlindsay.kmpcommercedemo.android.ui.core

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.hunterlindsay.kmpcommercedemo.android.ui.core.tab_view.CoreTab

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Composable
fun CoreTabIconView(
    tab: CoreTab,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.size(30.dp)
    ) {
        when (tab) {
            CoreTab.Browse -> {
                val iconScale = 0.85f
                val scaledWidth = size.width * iconScale
                val scaledHeight = size.height * iconScale
                val startX = (size.width - scaledWidth) / 2f
                val startY = (size.height - scaledHeight) / 2f

                val gap = scaledWidth * 0.12f
                val itemSize = (scaledWidth - gap) / 2f

                drawRoundRect(
                    color = color,
                    topLeft = Offset(startX, startY),
                    size = Size(itemSize, itemSize),
                    cornerRadius = CornerRadius(5f, 5f)
                )

                drawRoundRect(
                    color = color,
                    topLeft = Offset(startX + itemSize + gap, startY),
                    size = Size(itemSize, itemSize),
                    cornerRadius = CornerRadius(5f, 5f)
                )

                drawRoundRect(
                    color = color,
                    topLeft = Offset(startX, startY + itemSize + gap),
                    size = Size(itemSize, itemSize),
                    cornerRadius = CornerRadius(5f, 5f)
                )

                drawRoundRect(
                    color = color,
                    topLeft = Offset(startX + itemSize + gap, startY + itemSize + gap),
                    size = Size(itemSize, itemSize),
                    cornerRadius = CornerRadius(5f, 5f)
                )
            }

            CoreTab.Saved -> {
                val path = Path().apply {
                    moveTo(size.width * 0.50f, size.height * 0.88f)
                    cubicTo(
                        size.width * 0.08f,
                        size.height * 0.58f,
                        size.width * 0.00f,
                        size.height * 0.28f,
                        size.width * 0.22f,
                        size.height * 0.14f
                    )
                    cubicTo(
                        size.width * 0.36f,
                        size.height * 0.05f,
                        size.width * 0.48f,
                        size.height * 0.15f,
                        size.width * 0.50f,
                        size.height * 0.29f
                    )
                    cubicTo(
                        size.width * 0.52f,
                        size.height * 0.15f,
                        size.width * 0.64f,
                        size.height * 0.05f,
                        size.width * 0.78f,
                        size.height * 0.14f
                    )
                    cubicTo(
                        size.width * 1.00f,
                        size.height * 0.28f,
                        size.width * 0.92f,
                        size.height * 0.58f,
                        size.width * 0.50f,
                        size.height * 0.88f
                    )
                    close()
                }

                drawPath(
                    path = path,
                    color = color
                )
            }

            CoreTab.Cart -> {
                val strokeWidth = size.width * 0.13f

                drawLine(
                    color = color,
                    start = Offset(size.width * 0.10f, size.height * 0.18f),
                    end = Offset(size.width * 0.24f, size.height * 0.18f),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )

                drawLine(
                    color = color,
                    start = Offset(size.width * 0.24f, size.height * 0.18f),
                    end = Offset(size.width * 0.34f, size.height * 0.62f),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )

                drawRoundRect(
                    color = color,
                    topLeft = Offset(size.width * 0.34f, size.height * 0.28f),
                    size = Size(size.width * 0.50f, size.height * 0.30f),
                    cornerRadius = CornerRadius(6f, 6f),
                    style = Stroke(
                        width = strokeWidth
                    )
                )

                drawCircle(
                    color = color,
                    radius = size.width * 0.08f,
                    center = Offset(size.width * 0.42f, size.height * 0.80f)
                )

                drawCircle(
                    color = color,
                    radius = size.width * 0.08f,
                    center = Offset(size.width * 0.76f, size.height * 0.80f)
                )
            }
        }
    }
}
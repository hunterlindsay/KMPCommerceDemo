package com.hunterlindsay.kmpcommercedemo.android.ui

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.hunterlindsay.kmpcommercedemo.android.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

// Within the time constraints of this demo build, I'm using AI to help implement this concept I designed for the opening screen animation. Its an isolated animation and easily validated.

@Composable
fun RotatingDemoView(
    text: String,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.White,
    textColor: Color = Color.White,
    borderWidth: Dp = 36.dp,
    textPathBorderWidth: Dp = borderWidth,
    cornerRadius: Dp = 44.dp,
    fontSize: TextUnit = 18.sp,
    characterSpacing: Dp = 4.dp,
    minimumTextGroupGap: Dp = 160.dp,
    animationDurationMillis: Int = 40000,
    typeface: Typeface? = null
) {
    val context = LocalContext.current

    val resolvedTypeface = remember(context, typeface) {
        typeface ?: ResourcesCompat.getFont(
            context,
            R.font.sora
        ) ?: Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
    }

    val infiniteTransition = rememberInfiniteTransition(
        label = "RotatingDemoViewTransition"
    )

    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDurationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "RotatingDemoViewProgress"
    )

    val paint = remember {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val strokeWidthPx = borderWidth.toPx()
        val textPathStrokeWidthPx = textPathBorderWidth.toPx()
        val halfTextPathStroke = textPathStrokeWidthPx / 2f

        val left = halfTextPathStroke
        val top = halfTextPathStroke
        val right = size.width - halfTextPathStroke
        val bottom = size.height - halfTextPathStroke

        val availableRadius = min(
            (right - left) / 2f,
            (bottom - top) / 2f
        )

        val radius = min(
            cornerRadius.toPx(),
            availableRadius
        )

        if (strokeWidthPx > 0.5f) {
            drawRoundRect(
                color = borderColor,
                topLeft = Offset(
                    x = left,
                    y = top
                ),
                size = Size(
                    width = right - left,
                    height = bottom - top
                ),
                cornerRadius = CornerRadius(
                    x = radius,
                    y = radius
                ),
                style = Stroke(
                    width = strokeWidthPx
                )
            )
        }

        val pathMetrics = RoundedRectanglePathMetrics(
            left = left,
            top = top,
            right = right,
            bottom = bottom,
            radius = radius
        )

        val perimeter = pathMetrics.perimeter

        if (perimeter <= 0f || text.isBlank()) {
            return@Canvas
        }

        paint.color = textColor.toArgb()
        paint.textSize = fontSize.toPx()
        paint.typeface = resolvedTypeface
        paint.textAlign = Paint.Align.CENTER

        // Makes the Sora variable font appear much heavier when drawn through native Canvas.
        paint.isFakeBoldText = true
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = fontSize.toPx() * 0.13f

        val baselineOffset = -((paint.ascent() + paint.descent()) / 2f) + (fontSize.toPx() * 0.04f)
        val characterSpacingPx = characterSpacing.toPx()
        val minimumGapPx = minimumTextGroupGap.toPx()

        val characters = text.toList()
        val characterWidths = characters.map { character ->
            paint.measureText(character.toString())
        }

        val textPathLength = characterWidths.sum() +
                (characterSpacingPx * max(0, characters.size - 1))

        if (textPathLength <= 0f) {
            return@Canvas
        }

        val desiredGroupLength = textPathLength + minimumGapPx

        val textGroupCount = max(
            1,
            floor(perimeter / desiredGroupLength).toInt()
        )

        val distanceBetweenGroups = perimeter / textGroupCount

        val animatedOffset = perimeter * animationProgress

        repeat(textGroupCount) { groupIndex ->
            val groupCenterDistance = (
                    groupIndex * distanceBetweenGroups +
                            animatedOffset
                    ).mod(perimeter)

            drawTextGroupOnPath(
                characters = characters,
                characterWidths = characterWidths,
                characterSpacingPx = characterSpacingPx,
                groupCenterDistance = groupCenterDistance,
                textPathLength = textPathLength,
                pathMetrics = pathMetrics,
                perimeter = perimeter,
                baselineOffset = baselineOffset,
                paint = paint
            )
        }
    }
}

private fun DrawScope.drawTextGroupOnPath(
    characters: List<Char>,
    characterWidths: List<Float>,
    characterSpacingPx: Float,
    groupCenterDistance: Float,
    textPathLength: Float,
    pathMetrics: RoundedRectanglePathMetrics,
    perimeter: Float,
    baselineOffset: Float,
    paint: Paint
) {
    var characterStartDistance = groupCenterDistance - (textPathLength / 2f)

    characters.forEachIndexed { index, character ->
        val characterWidth = characterWidths[index]
        val characterCenterDistance = characterStartDistance + (characterWidth / 2f)
        val wrappedDistance = characterCenterDistance.mod(perimeter)

        val pathPosition = pathMetrics.positionAt(wrappedDistance)

        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.save()
            canvas.nativeCanvas.translate(
                pathPosition.x,
                pathPosition.y
            )
            canvas.nativeCanvas.rotate(pathPosition.angleDegrees)
            canvas.nativeCanvas.drawText(
                character.toString(),
                0f,
                baselineOffset,
                paint
            )
            canvas.nativeCanvas.restore()
        }

        characterStartDistance += characterWidth + characterSpacingPx
    }
}

private data class RoundedRectanglePathPosition(
    val x: Float,
    val y: Float,
    val angleDegrees: Float
)

private class RoundedRectanglePathMetrics(
    private val left: Float,
    private val top: Float,
    private val right: Float,
    private val bottom: Float,
    private val radius: Float
) {
    private val topLineLength = max(0f, right - left - (2f * radius))
    private val rightLineLength = max(0f, bottom - top - (2f * radius))
    private val bottomLineLength = topLineLength
    private val leftLineLength = rightLineLength
    private val cornerLength = (PI.toFloat() * radius) / 2f

    val perimeter: Float =
        topLineLength +
                cornerLength +
                rightLineLength +
                cornerLength +
                bottomLineLength +
                cornerLength +
                leftLineLength +
                cornerLength

    fun positionAt(distance: Float): RoundedRectanglePathPosition {
        var remainingDistance = distance.mod(perimeter)

        if (remainingDistance <= topLineLength) {
            return RoundedRectanglePathPosition(
                x = left + radius + remainingDistance,
                y = top,
                angleDegrees = 0f
            )
        }

        remainingDistance -= topLineLength

        if (remainingDistance <= cornerLength) {
            return arcPosition(
                centerX = right - radius,
                centerY = top + radius,
                startDegrees = -90f,
                travelled = remainingDistance
            )
        }

        remainingDistance -= cornerLength

        if (remainingDistance <= rightLineLength) {
            return RoundedRectanglePathPosition(
                x = right,
                y = top + radius + remainingDistance,
                angleDegrees = 90f
            )
        }

        remainingDistance -= rightLineLength

        if (remainingDistance <= cornerLength) {
            return arcPosition(
                centerX = right - radius,
                centerY = bottom - radius,
                startDegrees = 0f,
                travelled = remainingDistance
            )
        }

        remainingDistance -= cornerLength

        if (remainingDistance <= bottomLineLength) {
            return RoundedRectanglePathPosition(
                x = right - radius - remainingDistance,
                y = bottom,
                angleDegrees = 180f
            )
        }

        remainingDistance -= bottomLineLength

        if (remainingDistance <= cornerLength) {
            return arcPosition(
                centerX = left + radius,
                centerY = bottom - radius,
                startDegrees = 90f,
                travelled = remainingDistance
            )
        }

        remainingDistance -= cornerLength

        if (remainingDistance <= leftLineLength) {
            return RoundedRectanglePathPosition(
                x = left,
                y = bottom - radius - remainingDistance,
                angleDegrees = 270f
            )
        }

        remainingDistance -= leftLineLength

        return arcPosition(
            centerX = left + radius,
            centerY = top + radius,
            startDegrees = 180f,
            travelled = remainingDistance
        )
    }

    private fun arcPosition(
        centerX: Float,
        centerY: Float,
        startDegrees: Float,
        travelled: Float
    ): RoundedRectanglePathPosition {
        val travelledDegrees = (travelled / cornerLength) * 90f
        val angleDegrees = startDegrees + travelledDegrees
        val angleRadians = angleDegrees.toRadians()

        return RoundedRectanglePathPosition(
            x = centerX + (cos(angleRadians) * radius),
            y = centerY + (sin(angleRadians) * radius),
            angleDegrees = angleDegrees + 90f
        )
    }
}

private fun Float.toRadians(): Float {
    return this * (PI.toFloat() / 180f)
}

private fun Float.mod(other: Float): Float {
    return ((this % other) + other) % other
}
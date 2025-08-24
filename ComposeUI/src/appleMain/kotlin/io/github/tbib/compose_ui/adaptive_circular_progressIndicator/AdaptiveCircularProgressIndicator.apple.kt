package io.github.tbib.compose_ui.adaptive_circular_progressIndicator

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun AdaptiveCircularProgressIndicator(
    modifier: Modifier,
    color: Color,
    strokeWidth: Dp,
    trackColor: Color,
    strokeCap: StrokeCap
) {
    CupertinoActivityIndicator(
        modifier = modifier,
        color = color,
        trackColor = trackColor,

    )
}

/**
 * @param modifier indicator modifier
 * @param color color of the indicator
 * */
@Composable
private fun CupertinoActivityIndicator(
    color: Color = Color.Gray,
    modifier: Modifier = Modifier,
    trackColor: Color
) {
    // number of paths of the activity indicator
    val pathCount = 8
    // duration of a single animation cycle
    val durationMillis = 1000
    val minAlpha = .1f
    val animatedPathCount = (pathCount / 2).coerceIn(1, pathCount)

    val coefficient = 360f / pathCount

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = pathCount.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
    )

    Canvas(
        modifier = modifier
            .progressSemantics()
            .size(ActivityIndicatorDiameter),
    ) {
        var canvasWidth = size.width
        var canvasHeight = size.height

        if (canvasHeight < canvasWidth) {
            canvasWidth = canvasHeight
        } else {
            canvasHeight = canvasWidth
        }

        val itemWidth = canvasWidth / 3f
        val itemHeight = canvasHeight / pathCount

        val cornerRadius = itemWidth.coerceAtMost(itemHeight) / 2

        val horizontalOffset = (size.width - size.height).coerceAtLeast(0f) / 2
        val verticalOffset = (size.height - size.width).coerceAtLeast(0f) / 2

        val topLeftOffset = Offset(
            x = horizontalOffset + canvasWidth - itemWidth,
            y = verticalOffset + (canvasHeight - itemHeight) / 2,
        )

        val size = Size(itemWidth, itemHeight)

        for (i in 0..360 step 360 / pathCount) {
            rotate(i.toFloat()) {
                drawRoundRect(
                    color = trackColor.copy(alpha = minAlpha.coerceIn(0f, 1f)),
                    topLeft = topLeftOffset,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                )
            }
        }

        for (i in 0..animatedPathCount) {
            rotate((angle.toInt() + i) * coefficient) {
                drawRoundRect(
                    color = color.copy(
                        alpha = (1f / (pathCount / 2) * i).coerceIn(0f, 1f),
                    ),
                    topLeft = topLeftOffset,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                )
            }
        }
    }
}

internal val ActivityIndicatorDiameter =
    ActivityIndicatorTokens.Size - ActivityIndicatorTokens.ActiveIndicatorWidth * 2

internal object ActivityIndicatorTokens {
    val ActiveIndicatorWidth = 4.0.dp
    val Size = 38.0.dp
}

@Composable
actual fun AdaptiveCircularProgressIndicator(
    progress: Float,
    modifier: Modifier,
    color: Color,
    strokeWidth: Dp,
    trackColor: Color,
    strokeCap: StrokeCap
) {
    // رسم دائرة بسيطة لprogress determinate
    Canvas(
        modifier = modifier
            .progressSemantics(progress)
            .size(ActivityIndicatorDiameter),
    ) {
        val diameter = size.minDimension
        val radius = diameter / 2f
        val stroke = strokeWidth.toPx()

        // Draw track circle
        drawCircle(
            color = trackColor,
            radius = radius,
            center = center,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = stroke, cap = strokeCap)
        )

        // Draw progress arc
        drawArc(
            color = color,
            startAngle = -90f, // start from top
            sweepAngle = progress * 360f, // convert progress 0f..1f to 360 degrees
            useCenter = false,
            topLeft = Offset.Zero,
            size = Size(diameter, diameter),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = stroke, cap = strokeCap)
        )
    }
}

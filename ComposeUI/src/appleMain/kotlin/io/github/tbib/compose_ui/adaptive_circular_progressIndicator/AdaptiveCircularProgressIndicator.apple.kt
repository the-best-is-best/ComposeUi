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
        strokeCap = strokeCap,
        trackColor = trackColor,
        strokeWidth = strokeWidth

    )
}

/**
 * @param modifier indicator modifier
 * @param color color of the indicator
 * */
@Composable
private fun CupertinoActivityIndicator(
    color: Color,
    modifier: Modifier,
    strokeWidth: Dp,
    trackColor: Color,
    strokeCap: StrokeCap,
) {
    val pathCount = 8
    val durationMillis = 1000
    val minAlpha = .1f
    val animatedPathCount = (pathCount / 2).coerceIn(1, pathCount)
    val coefficient = 360f / pathCount

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = pathCount.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
    )

    Canvas(
        modifier = modifier
            .progressSemantics()
            .size(ActivityIndicatorDiameter),
    ) {
        val canvasSize = minOf(size.width, size.height)
        val itemWidth = (strokeWidth * 3).toPx()
        val itemHeight = canvasSize / pathCount
        val cornerRadius = if (strokeCap == StrokeCap.Round) itemWidth / 2 else 0f

        val horizontalOffset = (size.width - canvasSize) / 2
        val verticalOffset = (size.height - canvasSize) / 2
        val topLeftOffset = Offset(
            x = horizontalOffset + canvasSize - itemWidth,
            y = verticalOffset + (canvasSize - itemHeight) / 2
        )
        val rectSize = Size(itemWidth, itemHeight)

        // Draw track
        for (i in 0..360 step 360 / pathCount) {
            rotate(i.toFloat()) {
                drawRoundRect(
                    color = trackColor.copy(alpha = minAlpha.coerceIn(0f, 1f)),
                    topLeft = topLeftOffset,
                    size = rectSize,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
        }

        // Draw animated paths
        for (i in 0..animatedPathCount) {
            rotate((angle.toInt() + i) * coefficient) {
                drawRoundRect(
                    color = color.copy(alpha = (1f / (pathCount / 2) * i).coerceIn(0f, 1f)),
                    topLeft = topLeftOffset,
                    size = rectSize,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
        }
    }
}

private val ActivityIndicatorDiameter =
    ActivityIndicatorTokens.Size - ActivityIndicatorTokens.ActiveIndicatorWidth * 2

private object ActivityIndicatorTokens {
    val ActiveIndicatorWidth = 4.0.dp
    val Size = 38.0.dp
}
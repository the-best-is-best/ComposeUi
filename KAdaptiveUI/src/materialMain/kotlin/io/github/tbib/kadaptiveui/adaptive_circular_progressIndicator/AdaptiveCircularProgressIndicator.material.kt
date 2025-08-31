package io.github.tbib.kadaptiveui.adaptive_circular_progressIndicator

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp

@Composable
actual fun AdaptiveCircularProgressIndicator(
    modifier: Modifier,
    color: Color,
    strokeWidth: Dp,
    trackColor: Color,
    strokeCap: StrokeCap
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        strokeWidth = strokeWidth,
        trackColor = trackColor,
        strokeCap = strokeCap,
    )
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
    CircularProgressIndicator(
        progress = {
            progress
        },
        modifier = modifier,
        trackColor = trackColor,
        color = color,
        strokeWidth = strokeWidth,
        strokeCap = strokeCap,
    )
}
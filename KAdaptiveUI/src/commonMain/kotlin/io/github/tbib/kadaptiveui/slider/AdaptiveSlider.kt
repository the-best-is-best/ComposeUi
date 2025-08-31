package io.github.tbib.kadaptiveui.slider

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun AdaptiveSlider(
    modifier: Modifier = Modifier,
    value: Float,
    minValue: Float = 0f,
    maxValue: Float = 1f,
    colors: AdaptiveSliderColor = AdaptiveSliderDefaults.colors(),
    onValueChanged: (Float) -> Unit
)
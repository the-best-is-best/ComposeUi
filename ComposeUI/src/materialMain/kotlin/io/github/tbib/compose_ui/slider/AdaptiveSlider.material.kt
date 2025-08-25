package io.github.tbib.compose_ui.slider

import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun AdaptiveSlider(
    modifier: Modifier,
    value: Float,
    minValue: Float,
    maxValue: Float,
    colors: AdaptiveSliderColor,
    onValueChanged: (Float) -> Unit
) {
    Slider(
        value = value,
        onValueChange = onValueChanged,
        valueRange = minValue..maxValue,
        modifier = modifier,
        colors = SliderDefaults.colors(
            activeTrackColor = colors.activeTrackColor,
            inactiveTrackColor = colors.inactiveTrackColor,
            thumbColor = colors.thumbColor,
            disabledActiveTrackColor = colors.disabledActiveTrackColor,
            disabledInactiveTrackColor = colors.disabledInactiveTrackColor,
            disabledThumbColor = colors.disabledThumbColor
        )
    )
}
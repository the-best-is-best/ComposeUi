package io.github.tbib.compose_ui.slider

import androidx.compose.ui.graphics.Color

data class AdaptiveSliderColor(
    val activeTrackColor: Color,
    val inactiveTrackColor: Color,
    val thumbColor: Color,
    val disabledActiveTrackColor: Color,
    val disabledInactiveTrackColor: Color,
    val disabledThumbColor: Color
)


/**
 * Contains default values and helper functions for configuring the [AdaptiveSlider].
 */
object AdaptiveSliderDefaults {

    /**
     * Returns a [AdaptiveSlider] instance with provided or default color values.
     *
     * @param activeTrackColor Color of the filled track when enabled.
     * @param inactiveTrackColor Color of the unfilled track when enabled.
     * @param thumbColor Color of the thumb when enabled.
     * @param disabledActiveTrackColor Color of the filled track when disabled.
     * @param disabledInactiveTrackColor Color of the unfilled track when disabled.
     * @param disabledThumbColor Color of the thumb when disabled.
     */
    fun colors(
        activeTrackColor: Color = Color.Blue,
        inactiveTrackColor: Color = Color.Gray,
        thumbColor: Color = Color.White,
        disabledActiveTrackColor: Color = activeTrackColor.copy(alpha = 0.3f),
        disabledInactiveTrackColor: Color = inactiveTrackColor.copy(alpha = 0.3f),
        disabledThumbColor: Color = thumbColor.copy(alpha = 0.3f)
    ): AdaptiveSliderColor = AdaptiveSliderColor(
        activeTrackColor = activeTrackColor,
        inactiveTrackColor = inactiveTrackColor,
        thumbColor = thumbColor,
        disabledActiveTrackColor = disabledActiveTrackColor,
        disabledInactiveTrackColor = disabledInactiveTrackColor,
        disabledThumbColor = disabledThumbColor
    )
}

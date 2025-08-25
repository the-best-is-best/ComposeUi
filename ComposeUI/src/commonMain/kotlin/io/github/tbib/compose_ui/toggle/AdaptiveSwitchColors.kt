package io.github.tbib.compose_ui.toggle

import androidx.compose.ui.graphics.Color

data class AdaptiveSwitchColors(
    val thumbColor: Color,
    val trackColorChecked: Color,
    val trackColorUnchecked: Color,
)

object AdaptiveSwitchDefaults {

    /**
     * Creates a [AdaptiveSwitchColors] instance with optional custom or default values.
     *
     * @param thumbColor Color of the thumb (toggle knob), default is [Color.White].
     * @param trackColorUnchecked Track color when unchecked, default is [Color.Gray].
     * @return A [AdaptiveSwitchColors] instance with the specified color configuration.
     */
    fun colors(
        thumbColor: Color = Color.White,
        trackColorChecked: Color = Color.Blue,
        trackColorUnchecked: Color = Color.Gray,
    ): AdaptiveSwitchColors = AdaptiveSwitchColors(
        thumbColor = thumbColor,
        trackColorChecked = trackColorChecked,
        trackColorUnchecked = trackColorUnchecked
    )
}
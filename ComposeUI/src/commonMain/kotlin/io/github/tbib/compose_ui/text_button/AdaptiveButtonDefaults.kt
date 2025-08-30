package io.github.tbib.compose_ui.text_button

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class AdaptiveTextButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color
)

object AdaptiveButtonDefaults {

    /**
     * Returns a [AdaptiveTextButtonColors] instance with the given customization options.
     *
     * @param containerColor The background color for the enabled button.
     * @param contentColor The text color for the enabled button.
     * @param disabledBackgroundColor The background color when the button is disabled.
     * @param disabledTextColor The text color when the button is disabled.
     */
    fun colors(
        containerColor: Color = Color.White,
        contentColor: Color = Color.Black,
        disabledBackgroundColor: Color = containerColor.copy(alpha = 0.3f),
        disabledTextColor: Color = contentColor.copy(alpha = 0.3f)
    ): AdaptiveTextButtonColors = AdaptiveTextButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledBackgroundColor,
        disabledContentColor = disabledTextColor
    )

    /**
     * The default font size used in [AdaptiveTextButton] text, expressed in sp.
     */
    val DefaultFontSize: TextUnit = 14.sp

    /**
     * The default corner radius for [AdaptiveTextButton], expressed in dp.
     */
    const val CornerRadius = 8.0


}

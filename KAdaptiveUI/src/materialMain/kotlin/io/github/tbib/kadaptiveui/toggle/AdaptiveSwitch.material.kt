package io.github.tbib.kadaptiveui.toggle

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun AdaptiveSwitch(
    modifier: Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    androidThumbContent: @Composable (() -> Unit)?,
    colors: AdaptiveSwitchColors,
    isEnabled: Boolean,
    interactionSource: MutableInteractionSource
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        thumbContent = androidThumbContent,
        enabled = isEnabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = colors.thumbColor,
            uncheckedThumbColor = colors.thumbColor,
            checkedTrackColor = colors.trackColorChecked,
            uncheckedTrackColor = colors.trackColorUnchecked,
            disabledCheckedThumbColor = colors.thumbColor.copy(alpha = 0.5f),
            disabledUncheckedThumbColor = colors.thumbColor.copy(alpha = 0.5f),
            disabledCheckedTrackColor = colors.trackColorChecked.copy(alpha = 0.5f),
            disabledUncheckedTrackColor = colors.trackColorUnchecked.copy(alpha = 0.5f)
        ),
        interactionSource = interactionSource,
    )
}
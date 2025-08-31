package io.github.tbib.kadaptiveui.toggle

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
expect fun AdaptiveSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    androidThumbContent: @Composable (() -> Unit)? = null,
    colors: AdaptiveSwitchColors = AdaptiveSwitchDefaults.colors(),
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
)
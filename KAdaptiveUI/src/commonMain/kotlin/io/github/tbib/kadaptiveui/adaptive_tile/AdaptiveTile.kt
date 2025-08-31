package io.github.tbib.kadaptiveui.adaptive_tile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import io.github.tbib.kadaptiveui.icons.AdaptiveIcons
import io.github.tbib.kadaptiveui.text_button.AdaptiveButtonDefaults
import io.github.tbib.kadaptiveui.text_button.AdaptiveTextButtonColors

@Composable
expect fun AdaptiveTile(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    leadingIcon: AdaptiveIcons? = null,
    trailingIcon: AdaptiveIcons? = null,
    title: String,
    subtitle: String? = null,
    fontSize: TextUnit = AdaptiveButtonDefaults.DefaultFontSize,
    colors: AdaptiveTextButtonColors = AdaptiveButtonDefaults.colors()
)
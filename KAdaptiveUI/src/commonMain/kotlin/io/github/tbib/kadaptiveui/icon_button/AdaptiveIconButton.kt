package io.github.tbib.kadaptiveui.icon_button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.TextUnit
import io.github.tbib.kadaptiveui.icons.AdaptiveIcons
import io.github.tbib.kadaptiveui.text_button.AdaptiveButtonDefaults
import io.github.tbib.kadaptiveui.text_button.AdaptiveTextButtonColors

@Composable
expect fun AdaptiveIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    text: String? = null,
    fontSize: TextUnit = AdaptiveButtonDefaults.DefaultFontSize,
    shape: Shape = androidx.compose.material3.Shapes().small,
    colors: AdaptiveTextButtonColors = AdaptiveButtonDefaults.colors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(),
    adaptiveIcons: AdaptiveIcons
)
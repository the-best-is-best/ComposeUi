package io.github.tbib.kadaptiveui.text_button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.TextUnit


@Composable
expect fun AdaptiveTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    text: String,
    fontSize: TextUnit = AdaptiveButtonDefaults.DefaultFontSize,
    shape: Shape = androidx.compose.material3.Shapes().small,
    colors: AdaptiveTextButtonColors = AdaptiveButtonDefaults.colors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues()
)

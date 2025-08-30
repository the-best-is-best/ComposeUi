package io.github.tbib.compose_ui.icon_button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import io.github.tbib.compose_ui.icons.AdaptiveIcon
import io.github.tbib.compose_ui.icons.IosIcon
import io.github.tbib.compose_ui.text_button.AdaptiveTextButtonColors

@Composable
actual fun AdaptiveIconButton(
    onClick: () -> Unit,
    modifier: Modifier,
    isEnabled: Boolean,
    text: String?,
    fontSize: TextUnit,
    cornerRadius: Double,
    shape: Shape,
    colors: AdaptiveTextButtonColors,
    elevation: ButtonElevation?,
    border: BorderStroke?,
    contentPadding: androidx.compose.foundation.layout.PaddingValues,
    androidIcon: @Composable () -> Unit,
    iosIcon: IosIcon
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        shape = shape,
        colors = colors.toButtonColors(),
        elevation = elevation ?: ButtonDefaults.buttonElevation(),
        border = border,
        contentPadding = contentPadding,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Row {
            AdaptiveIcon(androidIcon = androidIcon, iosIcon = iosIcon)
            if (text != null) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = text,
                    fontSize = fontSize,
                    color = if (isEnabled) colors.contentColor else colors.disabledContentColor
                )
            }
        }
    }
}

@Composable
private fun AdaptiveTextButtonColors.toButtonColors() =
    ButtonDefaults.textButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )

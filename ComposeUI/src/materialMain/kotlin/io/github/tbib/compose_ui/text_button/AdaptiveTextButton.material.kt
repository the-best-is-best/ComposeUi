package io.github.tbib.compose_ui.text_button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit

@Composable
actual fun AdaptiveTextButton(
    onClick: () -> Unit,
    modifier: Modifier,
    isEnabled: Boolean,
    text: String,
    fontSize: TextUnit,
    shape: Shape,
    colors: AdaptiveTextButtonColors,
    elevation: ButtonElevation?,
    border: BorderStroke?,
    contentPadding: PaddingValues,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        shape = shape,
        colors = colors.toButtonColors(), // تحويل AdaptiveTextButtonColors لـ ButtonColors
        elevation = elevation ?: ButtonDefaults.buttonElevation(),
        border = border,
        contentPadding = contentPadding,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize,
                color = if (isEnabled) colors.contentColor else colors.disabledContentColor
            )
        )
    }
}


@Composable
private fun AdaptiveTextButtonColors.toButtonColors(): ButtonColors {
    return ButtonDefaults.textButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )
}
package io.github.tbib.compose_ui.adaptive_tile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import io.github.tbib.compose_ui.icons.AdaptiveIcons
import io.github.tbib.compose_ui.text_button.AdaptiveTextButtonColors

@Composable
actual fun AdaptiveTile(
    onClick: () -> Unit,
    modifier: Modifier,
    isEnabled: Boolean,
    leadingIcon: AdaptiveIcons?,
    trailingIcon: AdaptiveIcons?,
    title: String,
    subtitle: String?,
    fontSize: TextUnit,
    colors: AdaptiveTextButtonColors
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isEnabled) colors.containerColor else colors.disabledContainerColor
            )
            .clickable(enabled = isEnabled, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        // Leading icon
        leadingIcon?.let {
            it.androidIcon()
            Spacer(modifier = Modifier.width(8.dp))
        }

        // Title + Subtitle
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = fontSize,
                color = if (isEnabled) colors.contentColor else colors.disabledContentColor
            )
            subtitle?.let { sub ->
                Text(
                    text = sub,
                    fontSize = fontSize * 0.8f,
                    color = if (isEnabled) colors.contentColor else colors.disabledContentColor,
                    lineHeight = fontSize * 0.9f
                )
            }
        }
        // Trailing icon
        trailingIcon?.let {
            Spacer(modifier = Modifier.width(8.dp))
            it.androidIcon()
        }
    }
}

package io.github.tbib.compose_ui.icons

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView

@Composable
actual fun AdaptiveIcon(
    adaptiveIcons: AdaptiveIcons
) {
    UIKitView(
        factory = {
            adaptiveIcons.iosIcon.render()
        },
        modifier = Modifier.defaultMinSize(32.dp, 32.dp)
    )
}
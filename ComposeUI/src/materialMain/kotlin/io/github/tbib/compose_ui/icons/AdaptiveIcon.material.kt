package io.github.tbib.compose_ui.icons

import androidx.compose.runtime.Composable

@Composable
actual fun AdaptiveIcon(
    androidIcon: @Composable () -> Unit,
    iosIcon: IosIcon
) {
    androidIcon()
}
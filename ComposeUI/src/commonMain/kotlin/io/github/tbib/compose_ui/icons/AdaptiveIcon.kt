package io.github.tbib.compose_ui.icons

import androidx.compose.runtime.Composable

@Composable
expect fun AdaptiveIcon(
    androidIcon: @Composable () -> Unit,
    iosIcon: IosIcon
)
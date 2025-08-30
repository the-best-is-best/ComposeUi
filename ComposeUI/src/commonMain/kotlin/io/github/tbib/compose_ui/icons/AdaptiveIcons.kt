package io.github.tbib.compose_ui.icons

import androidx.compose.runtime.Composable

data class AdaptiveIcons(
    val androidIcon: @Composable () -> Unit,
    val iosIcon: IosIcon
)
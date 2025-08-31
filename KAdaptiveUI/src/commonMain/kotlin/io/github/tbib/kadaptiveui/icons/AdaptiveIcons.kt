package io.github.tbib.kadaptiveui.icons

import androidx.compose.runtime.Composable

data class AdaptiveIcons(
    val androidIcon: @Composable () -> Unit,
    val iosIcon: IosIcon
)
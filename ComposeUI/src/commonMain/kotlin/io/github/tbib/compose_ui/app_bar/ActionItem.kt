package io.github.tbib.compose_ui.app_bar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ActionItem(
    val androidIcon: ImageVector,  // Compose vector icon
    val iosIconName: String,       // SF Symbol name
    val onClick: () -> Unit,
    val tint: Color = Color.Black          // optional icon tint

)

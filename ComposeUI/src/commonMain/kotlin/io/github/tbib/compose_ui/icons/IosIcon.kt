package io.github.tbib.compose_ui.icons

import androidx.compose.ui.graphics.Color


sealed class IosIcon {
    data class SystemIcon(val name: String) : IosIcon()   // SF Symbols
    data class CustomIcon(
        val name: String,
        val fontFamily: String,
        val size: Int,
        val tint: Color? = null
    ) : IosIcon()
}
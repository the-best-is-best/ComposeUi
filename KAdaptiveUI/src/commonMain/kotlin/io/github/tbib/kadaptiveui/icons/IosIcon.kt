package io.github.tbib.kadaptiveui.icons

import androidx.compose.ui.graphics.Color


sealed class IosIcon {
    data class SystemIcon(val name: String, val tint: Color) : IosIcon()   // SF Symbols
    data class CustomIcon(
        val name: String,
        val fontFamily: String,
        val size: Int,
        val tint: Color? = null
    ) : IosIcon()
}
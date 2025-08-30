package io.github.tbib.compose_ui.app_bar

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
expect fun AdaptiveAppBar(
    title: String,
    titleStyle: TextStyle = TextStyle.Default,
    leading: ActionItem? = null,
    showBackIfNull: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    actions: List<ActionItem> = listOf(),
    )
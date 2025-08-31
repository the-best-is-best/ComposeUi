package io.github.tbib.kadaptiveui.app_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun AdaptiveAppBar(
    title: String,
    titleStyle: TextStyle,
    leading: ActionItem?,
    showBackIfNull: Boolean,
    onBackClick: (() -> Unit)?,
    actions: List<ActionItem>
) {
    TopAppBar(
        title = { Text(title, style = titleStyle) },
        navigationIcon = {
            when {
                leading != null -> {
                    leading.androidIcon.let { icon ->
                        IconButton(onClick = leading.onClick) {
                            Icon(imageVector = icon, contentDescription = null, tint = leading.tint)
                        }
                    }
                }

                showBackIfNull && onBackClick != null -> {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }

                else -> {} // فارغة بدل null
            }
        },
        actions = {
            actions.forEach { action ->
                action.androidIcon.let { icon ->
                    IconButton(onClick = action.onClick) {
                        Icon(imageVector = icon, contentDescription = null, tint = action.tint)
                    }
                }
            }
        }
    )
}



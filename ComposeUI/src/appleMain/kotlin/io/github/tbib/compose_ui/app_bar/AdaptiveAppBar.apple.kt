package io.github.tbib.compose_ui.app_bar

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import io.github.tbib.compose_ui.utils.toUiColor
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIBarButtonItem
import platform.UIKit.UIBarButtonItemStyle
import platform.UIKit.UIFont
import platform.UIKit.UIImage
import platform.UIKit.UILabel
import platform.UIKit.UINavigationBar
import platform.UIKit.UINavigationItem
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun AdaptiveAppBar(
    title: String,
    titleStyle: TextStyle,
    leading: ActionItem?,
    showBackIfNull: Boolean,
    onBackClick: (() -> Unit)?,
    actions: List<ActionItem>,
) {
    // Keep handlers alive at Composable level
    val handlers = remember { mutableListOf<BarButtonHandler>() }
    handlers.clear() // clean old ones on recomposition

    UIKitView(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .defaultMinSize(minHeight = 50.dp),
        factory = {
            val navBar = UINavigationBar()
            val navItem = UINavigationItem()

            // Title with style
            val titleLabel = UILabel().apply {
                text = title
                textColor = titleStyle.color.toUiColor()
                font = UIFont.systemFontOfSize(titleStyle.fontSize.value.toDouble())
            }
            navItem.titleView = titleLabel

            // Leading / Back button
            if (leading?.iosIconName != null) {
                val handler = BarButtonHandler(leading.onClick)
                handlers.add(handler)
                val image = UIImage.systemImageNamed(leading.iosIconName)
                val barButton = UIBarButtonItem(
                    image = image,
                    style = UIBarButtonItemStyle.UIBarButtonItemStylePlain,
                    target = handler,
                    action = NSSelectorFromString("tapped:")
                )
                leading.tint.let { color -> barButton.tintColor = color.toUiColor() }
                navItem.leftBarButtonItem = barButton
            } else if (showBackIfNull && onBackClick != null) {
                val handler = BarButtonHandler(onBackClick)
                handlers.add(handler)
                navItem.leftBarButtonItem = UIBarButtonItem(
                    title = "Back",
                    style = UIBarButtonItemStyle.UIBarButtonItemStylePlain,
                    target = handler,
                    action = NSSelectorFromString("tapped:")
                )
            }

            // Right actions
            val rightButtons = actions.map { action ->
                val handler = BarButtonHandler(action.onClick)
                handlers.add(handler)
                val image = UIImage.systemImageNamed(action.iosIconName)
                val barButton = UIBarButtonItem(
                    image = image,
                    style = UIBarButtonItemStyle.UIBarButtonItemStylePlain,
                    target = handler,
                    action = NSSelectorFromString("tapped:")
                )
                action.tint.let { color -> barButton.tintColor = color.toUiColor() }
                barButton
            }
            navItem.rightBarButtonItems = rightButtons

            navBar.items = listOf(navItem)
            navBar
        }
    )
}

// Handler class to keep callbacks alive
private class BarButtonHandler(val callback: () -> Unit) : NSObject() {
    @ObjCAction
    fun tapped(sender: UIBarButtonItem) {
        callback()
    }
}

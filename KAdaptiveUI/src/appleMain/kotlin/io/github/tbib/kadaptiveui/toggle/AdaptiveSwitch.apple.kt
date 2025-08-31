package io.github.tbib.kadaptiveui.toggle

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import io.github.tbib.kadaptiveui.utils.toUiColor
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UISwitch
import platform.UIKit.accessibilityHint
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Composable
actual fun AdaptiveSwitch(
    modifier: Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    androidThumbContent: @Composable (() -> Unit)?,
    colors: AdaptiveSwitchColors,
    isEnabled: Boolean,
    interactionSource: MutableInteractionSource
) {
    // Store the callback in a remembered mutable reference
    val changeCallback = remember { mutableStateOf(onCheckedChange) }
    changeCallback.value = onCheckedChange
    val width = 51.0
    val height = 31.0
    // Create UISwitch
    val uiSwitch = remember {
        UISwitch().apply {
            setFrame(CGRectMake(0.0, 0.0, width, height))// Standard UISwitch size
            on = checked
        }
    }

    val switchTarget = remember {
        object : NSObject() {
            @ObjCAction
            fun switchChanged() {
                if (isEnabled)
                changeCallback.value(uiSwitch.on)
            }
        }
    }

    UIKitView(
        factory = {
            uiSwitch.addTarget(
                target = switchTarget,
                action = NSSelectorFromString("switchChanged"),
                forControlEvents = UIControlEventValueChanged
            )
            uiSwitch.accessibilityHint = switchTarget.toString()
            uiSwitch
        },
        update = { view ->
            view.apply {
                // Update switch state
                on = checked
                onTintColor = colors.trackColorChecked.toUiColor()
                tintColor = colors.trackColorUnchecked.toUiColor()
                thumbTintColor = colors.thumbColor.toUiColor()

                // Alpha for disabled state
                alpha = if (isEnabled) 1.0 else 0.5
            }
        },
        modifier = modifier
            .defaultMinSize(minWidth = width.dp, minHeight = height.dp)
    )
}
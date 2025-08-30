package io.github.tbib.compose_ui.text_button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import io.github.tbib.compose_ui.utils.toUiColor
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIButton
import platform.UIKit.UIButtonTypeCustom
import platform.UIKit.UIButtonTypeSystem
import platform.UIKit.UIControlEventTouchUpInside
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIEdgeInsetsMake
import platform.UIKit.UIFont
import platform.UIKit.accessibilityHint
import platform.UIKit.contentEdgeInsets
import platform.darwin.NSObject
import kotlin.math.max

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Composable
actual fun AdaptiveTextButton(
    onClick: () -> Unit,
    modifier: Modifier,
    isEnabled: Boolean,
    text: String,
    fontSize: TextUnit,
    cornerRadius: Double,
    shape: Shape,
    colors: AdaptiveTextButtonColors,
    elevation: ButtonElevation?,
    border: BorderStroke?,
    contentPadding: PaddingValues
) {
    val clickCallback = remember { mutableStateOf(onClick) }
    val target = remember {
        object : NSObject() {
            @ObjCAction
            fun buttonTapped() {
                clickCallback.value()
            }
        }
    }
    clickCallback.value = onClick

    // Pre-calculate button size using intrinsicContentSize
    val buttonSize = remember(text, fontSize, contentPadding) {
        val tempButton = UIButton.buttonWithType(UIButtonTypeSystem).apply {
            setTitle(text, forState = UIControlStateNormal)
            titleLabel?.font = UIFont.systemFontOfSize(fontSize.value.toDouble())
            sizeToFit()
        }

        tempButton.intrinsicContentSize.useContents {
            IntSize(
                width = max(width.toInt() + 32, 44),
                height = max(height.toInt() + 16, 44)
            )
        }
    }

    val button = UIButton.buttonWithType(UIButtonTypeCustom).apply {
        setTitle(text, forState = UIControlStateNormal)
        titleLabel?.font = UIFont.systemFontOfSize(fontSize.value.toDouble())

        backgroundColor =
            if (isEnabled) colors.containerColor.toUiColor() else colors.disabledContainerColor.toUiColor()
        setTitleColor(
            if (isEnabled) colors.contentColor.toUiColor() else colors.disabledContentColor.toUiColor(),
            forState = UIControlStateNormal
        )

        // Padding
        contentEdgeInsets = UIEdgeInsetsMake(8.0, 16.0, 8.0, 16.0)

        // Corner as circle
        layer.cornerRadius = cornerRadius
        clipsToBounds = true

        // Border
        if (border != null) {
            layer.borderWidth = border.width.value.toDouble()
            layer.borderColor = border.brush.solidColorOr(colors.containerColor).toUiColor().CGColor
        }

        addTarget(target, NSSelectorFromString("buttonTapped"), UIControlEventTouchUpInside)
        enabled = isEnabled
    }

    UIKitView(
        factory = {
            button.enabled = isEnabled
            button.accessibilityHint = target.toString()
            button
        },
        update = { view ->
            view.setTitle(text, forState = UIControlStateNormal)
            view.enabled = isEnabled
            view.setTitleColor(
                if (isEnabled) colors.contentColor.toUiColor() else colors.disabledContentColor.toUiColor(),
                forState = UIControlStateNormal
            )
            view.backgroundColor =
                if (isEnabled) colors.containerColor.toUiColor() else colors.disabledContainerColor.toUiColor()
            view.titleLabel?.font = UIFont.systemFontOfSize(fontSize.value.toDouble())
            view.layer.cornerRadius = cornerRadius
        },
        modifier = modifier.defaultMinSize(
            minWidth = (buttonSize.width).dp,
            minHeight = buttonSize.height.dp
        )
    )
}

// Extension to extract color from Brush
private val androidx.compose.ui.graphics.Brush.solidColorOr: (androidx.compose.ui.graphics.Color) -> androidx.compose.ui.graphics.Color
    get() = { fallback ->
        when (this) {
            is androidx.compose.ui.graphics.SolidColor -> this.value
            else -> fallback
        }
    }

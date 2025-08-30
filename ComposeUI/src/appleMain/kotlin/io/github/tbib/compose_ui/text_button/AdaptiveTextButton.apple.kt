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
import androidx.compose.ui.unit.DpSize
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
import platform.UIKit.UIControlEventTouchUpInside
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIEdgeInsetsMake
import platform.UIKit.UIFont
import platform.UIKit.UILabel
import platform.UIKit.contentEdgeInsets
import platform.darwin.NSObject

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
            fun tapped() {
                clickCallback.value()
            }
        }
    }

    // حساب الحجم الديناميكي للنص + padding
    val buttonSize = remember(text, fontSize, contentPadding) {
        val label = UILabel().apply {
            this.text = text
            font = UIFont.systemFontOfSize(fontSize.value.toDouble())
            sizeToFit()
        }
        label.intrinsicContentSize.useContents {
            val width = width + 32.0 // padding افتراضي 16 + 16
            val height = height + 16.0 // padding أعلى + أسفل
            DpSize(width.dp, height.dp)
        }
    }

    UIKitView(
        factory = {
            val button = UIButton.buttonWithType(UIButtonTypeCustom).apply {
                setTitle(text, forState = UIControlStateNormal)
                titleLabel?.font = UIFont.systemFontOfSize(fontSize.value.toDouble())
                backgroundColor =
                    if (isEnabled) colors.containerColor.toUiColor() else colors.disabledContainerColor.toUiColor()
                setTitleColor(
                    if (isEnabled) colors.contentColor.toUiColor() else colors.disabledContentColor.toUiColor(),
                    forState = UIControlStateNormal
                )
                contentEdgeInsets = UIEdgeInsetsMake(8.0, 16.0, 8.0, 16.0)
                layer.cornerRadius = cornerRadius
                clipsToBounds = true

                if (border != null) {
                    layer.borderWidth = border.width.value.toDouble()
                    layer.borderColor =
                        border.brush.solidColorOr(colors.containerColor).toUiColor().CGColor
                }

                addTarget(target, NSSelectorFromString("tapped"), UIControlEventTouchUpInside)
                enabled = isEnabled
            }
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
        modifier = modifier.defaultMinSize(buttonSize.width, buttonSize.height)
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

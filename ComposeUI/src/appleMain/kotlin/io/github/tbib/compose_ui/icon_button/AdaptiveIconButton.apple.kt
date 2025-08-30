package io.github.tbib.compose_ui.icon_button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import io.github.tbib.compose_ui.icons.IosIcon
import io.github.tbib.compose_ui.icons.render
import io.github.tbib.compose_ui.text_button.AdaptiveTextButtonColors
import io.github.tbib.compose_ui.utils.toUiColor
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIFont
import platform.UIKit.UILabel
import platform.UIKit.UILayoutConstraintAxisHorizontal
import platform.UIKit.UIStackView
import platform.UIKit.UIStackViewAlignmentCenter
import platform.UIKit.UITapGestureRecognizer
import platform.UIKit.UIView
import platform.darwin.NSObject
import kotlin.math.max

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Composable
actual fun AdaptiveIconButton(
    onClick: () -> Unit,
    modifier: Modifier,
    isEnabled: Boolean,
    text: String?,
    fontSize: TextUnit,
    cornerRadius: Double,
    shape: Shape,
    colors: AdaptiveTextButtonColors,
    elevation: androidx.compose.material3.ButtonElevation?,
    border: BorderStroke?,
    contentPadding: PaddingValues,
    androidIcon: @Composable () -> Unit,
    iosIcon: IosIcon
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

    // حساب الحجم الديناميكي للنص + الأيقونة
    val buttonSize = remember(text, fontSize, iosIcon) {
        val labelWidth = text?.let {
            UILabel().apply {
                this.text = it
                font = UIFont.systemFontOfSize(fontSize.value.toDouble())
                sizeToFit()
            }.intrinsicContentSize.useContents { width }
        } ?: 0.0

        val labelHeight = text?.let {
            UILabel().apply {
                this.text = it
                font = UIFont.systemFontOfSize(fontSize.value.toDouble())
                sizeToFit()
            }.intrinsicContentSize.useContents { height }
        } ?: 0.0

        val iconSize =
            iosIcon.render().apply { sizeToFit() }.intrinsicContentSize.useContents { width }
        val iconHeight =
            iosIcon.render().apply { sizeToFit() }.intrinsicContentSize.useContents { height }

        val width = labelWidth + iconSize + 8.0 + 24.0 // spacing + padding
        val height = max(labelHeight, iconHeight) + 16.0 // top-bottom padding

        DpSize(width.dp, height.dp)
    }

    UIKitView(
        factory = {
            val view = UIView().apply {
                backgroundColor =
                    (if (isEnabled) colors.containerColor else colors.disabledContainerColor).toUiColor()
                layer.cornerRadius = cornerRadius
                clipsToBounds = true
            }

            val tap = UITapGestureRecognizer(target, NSSelectorFromString("tapped"))
            view.addGestureRecognizer(tap)

            val stack = UIStackView().apply {
                axis = UILayoutConstraintAxisHorizontal
                alignment = UIStackViewAlignmentCenter
                spacing = 8.0
                translatesAutoresizingMaskIntoConstraints = false
            }

            // أيقونة
            val iconView = iosIcon.render().apply {
                translatesAutoresizingMaskIntoConstraints = false
                widthAnchor.constraintEqualToConstant(24.0).active = true
                heightAnchor.constraintEqualToConstant(24.0).active = true
            }
            stack.addArrangedSubview(iconView)

            // نص إذا موجود
            text?.let {
                val label = UILabel().apply {
                    this.text = it
                    font = UIFont.systemFontOfSize(fontSize.value.toDouble())
                    textColor =
                        (if (isEnabled) colors.contentColor else colors.disabledContentColor).toUiColor()
                    translatesAutoresizingMaskIntoConstraints = false
                }
                stack.addArrangedSubview(label)
            }

            view.addSubview(stack)

            // Constraints لل stack
            stack.centerXAnchor.constraintEqualToAnchor(view.centerXAnchor).active = true
            stack.centerYAnchor.constraintEqualToAnchor(view.centerYAnchor).active = true

            view
        },
        update = { view ->
            view.backgroundColor =
                (if (isEnabled) colors.containerColor else colors.disabledContainerColor).toUiColor()
        },
        modifier = modifier.defaultMinSize(buttonSize.width, buttonSize.height)
    )
}

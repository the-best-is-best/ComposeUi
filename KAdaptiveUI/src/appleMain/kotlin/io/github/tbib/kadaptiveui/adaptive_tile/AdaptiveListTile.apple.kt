package io.github.tbib.kadaptiveui.adaptive_tile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import io.github.tbib.kadaptiveui.icons.AdaptiveIcons
import io.github.tbib.kadaptiveui.icons.render
import io.github.tbib.kadaptiveui.text_button.AdaptiveTextButtonColors
import io.github.tbib.kadaptiveui.utils.toUiColor
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIFont
import platform.UIKit.UILabel
import platform.UIKit.UILayoutConstraintAxisHorizontal
import platform.UIKit.UILayoutConstraintAxisVertical
import platform.UIKit.UIStackView
import platform.UIKit.UIStackViewAlignmentCenter
import platform.UIKit.UIStackViewAlignmentFill
import platform.UIKit.UITapGestureRecognizer
import platform.UIKit.UIView
import platform.darwin.NSObject

@OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
@Composable
actual fun AdaptiveTile(
    onClick: () -> Unit,
    modifier: Modifier,
    isEnabled: Boolean,
    leadingIcon: AdaptiveIcons?,
    trailingIcon: AdaptiveIcons?,
    title: String,
    subtitle: String?,
    fontSize: TextUnit,
    colors: AdaptiveTextButtonColors
) {
    val clickCallback = remember { mutableStateOf(onClick) }

    val target = remember {
        object : NSObject() {
            @ObjCAction
            fun tapped() {
                if (isEnabled) clickCallback.value()
            }
        }
    }

    val horizontalPadding = 12.0
    val verticalPadding = 8.0

    UIKitView(
        factory = {
            val view = UIView().apply {
                backgroundColor =
                    (if (isEnabled) colors.containerColor else colors.disabledContainerColor).toUiColor()
                layer.cornerRadius = 8.0
                clipsToBounds = true
            }

            val tap = UITapGestureRecognizer(target, NSSelectorFromString("tapped"))
            view.addGestureRecognizer(tap)

            // Stack for leading icon + labels + trailing icon
            val stack = UIStackView().apply {
                axis = UILayoutConstraintAxisHorizontal
                alignment = UIStackViewAlignmentCenter
                spacing = 8.0
                translatesAutoresizingMaskIntoConstraints = false
            }

            // Leading icon
            leadingIcon?.let {
                val iconView = it.iosIcon.render().apply {
                    translatesAutoresizingMaskIntoConstraints = false
                    widthAnchor.constraintEqualToConstant(24.0).active = true
                    heightAnchor.constraintEqualToConstant(24.0).active = true
                }
                stack.addArrangedSubview(iconView)
            }

            // Labels vertical stack
            val labelStack = UIStackView().apply {
                axis = UILayoutConstraintAxisVertical
                alignment = UIStackViewAlignmentFill
                spacing = 2.0
                translatesAutoresizingMaskIntoConstraints = false
            }

            val titleLabel = UILabel().apply {
                text = title
                font = UIFont.systemFontOfSize(fontSize.value.toDouble())
                textColor =
                    if (isEnabled) colors.contentColor.toUiColor() else colors.disabledContentColor.toUiColor()
                numberOfLines = 0
                translatesAutoresizingMaskIntoConstraints = false
            }
            labelStack.addArrangedSubview(titleLabel)

            subtitle?.let {
                val subLabel = UILabel().apply {
                    text = it
                    font = UIFont.systemFontOfSize((fontSize.value * 0.8))
                    textColor =
                        if (isEnabled) colors.contentColor.toUiColor() else colors.disabledContentColor.toUiColor()
                    numberOfLines = 0
                    translatesAutoresizingMaskIntoConstraints = false
                }
                labelStack.addArrangedSubview(subLabel)
            }

            stack.addArrangedSubview(labelStack)

            // Trailing icon
            trailingIcon?.let {
                val iconView = it.iosIcon.render().apply {
                    translatesAutoresizingMaskIntoConstraints = false
                    widthAnchor.constraintEqualToConstant(24.0).active = true
                    heightAnchor.constraintEqualToConstant(24.0).active = true
                }
                stack.addArrangedSubview(iconView)
            }

            // Container to apply padding
            val container = UIView().apply {
                translatesAutoresizingMaskIntoConstraints = false
                addSubview(stack)

                stack.topAnchor.constraintEqualToAnchor(
                    this.topAnchor,
                    constant = verticalPadding
                ).active = true
                stack.bottomAnchor.constraintEqualToAnchor(
                    this.bottomAnchor,
                    constant = -verticalPadding
                ).active = true
                stack.leadingAnchor.constraintEqualToAnchor(
                    this.leadingAnchor,
                    constant = horizontalPadding
                ).active = true
                stack.trailingAnchor.constraintEqualToAnchor(
                    this.trailingAnchor,
                    constant = -horizontalPadding
                ).active = true

            }

            view.addSubview(container)

            // Pin container to parent view
            container.topAnchor.constraintEqualToAnchor(view.topAnchor).active = true
            container.bottomAnchor.constraintEqualToAnchor(view.bottomAnchor).active = true
            container.leadingAnchor.constraintEqualToAnchor(view.leadingAnchor).active = true
            container.trailingAnchor.constraintEqualToAnchor(view.trailingAnchor).active = true

            view
        },
        update = { view ->
            view.backgroundColor =
                (if (isEnabled) colors.containerColor else colors.disabledContainerColor).toUiColor()

        },
        modifier = modifier.fillMaxWidth().height(56.dp)
    )
}

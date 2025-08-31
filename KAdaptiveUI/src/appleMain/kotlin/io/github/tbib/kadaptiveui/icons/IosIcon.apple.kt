package io.github.tbib.kadaptiveui.icons

import io.github.tbib.kadaptiveui.icons.IosIcon.CustomIcon
import io.github.tbib.kadaptiveui.icons.IosIcon.SystemIcon
import io.github.tbib.kadaptiveui.utils.toUiColor
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIFont
import platform.UIKit.UIImage
import platform.UIKit.UIImageRenderingMode
import platform.UIKit.UIImageView
import platform.UIKit.UILabel
import platform.UIKit.UIView
import platform.UIKit.UIViewContentMode


internal fun IosIcon.render(): UIView {
    return when (this) {
        is SystemIcon -> {
            val imageView = UIImageView()
            imageView.image = UIImage.systemImageNamed(this.name)
                ?.imageWithRenderingMode(UIImageRenderingMode.UIImageRenderingModeAlwaysTemplate)
            imageView.contentMode = UIViewContentMode.UIViewContentModeScaleAspectFit
            imageView.tintColor = this.tint.toUiColor()
            imageView
        }

        is CustomIcon -> {
            val label = UILabel()
            label.text = this.name
            label.font = UIFont.fontWithName(this.fontFamily, this.size.toDouble())!!
            label.textAlignment = NSTextAlignmentCenter
            this.tint?.let { label.textColor = it.toUiColor() }
            label
        }
    }
}

package io.github.tbib.compose_ui.icons

import io.github.tbib.compose_ui.icons.IosIcon.CustomIcon
import io.github.tbib.compose_ui.icons.IosIcon.SystemIcon
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIFont
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UILabel
import platform.UIKit.UIView
import platform.UIKit.UIViewContentMode


internal fun IosIcon.render(): UIView {
    return when (this) {
        is SystemIcon -> {
            val imageView = UIImageView()
            // Use UIImage.imageWithSystemName in Kotlin/Native
            imageView.image = UIImage.systemImageNamed(this.name)
            imageView.contentMode = UIViewContentMode.UIViewContentModeScaleAspectFit
            imageView
        }

        is CustomIcon -> {
            val label = UILabel()
            label.text = this.name
            label.font = UIFont.fontWithName(this.fontFamily, this.size.toDouble())!!
            label.textAlignment = NSTextAlignmentCenter
            label
        }
    }
}
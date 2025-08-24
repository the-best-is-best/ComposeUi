package io.github.tbib.compose_ui.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cValue
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSize
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
fun UIImage.resize(width: Double, height: Double): UIImage? {
    val size = cValue<CGSize> {
        this.width = width
        this.height = height
    }
    UIGraphicsBeginImageContextWithOptions(size, false, UIScreen.mainScreen.scale)
    this.drawInRect(CGRectMake(0.0, 0.0, width, height))
    val resizedImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()
    return resizedImage
}
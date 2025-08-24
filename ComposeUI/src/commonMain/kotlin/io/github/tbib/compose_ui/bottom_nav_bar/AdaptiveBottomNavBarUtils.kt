package io.github.tbib.compose_ui.bottom_nav_bar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Represents a single bottom navigation item
 */
data class AdaptiveBottomNavBarItem<Route>(
    val title: AdaptiveBottomNavItemTitle, // instead of raw Composable
    val icon: AdaptiveBottomNavItemIcon,
    val route: Route,
)

/**
 * Represents the icon of the bottom nav item
 * selectedIcon is used when the item is selected.
 * unSelectedIcon is used when the item is not selected (if null, selectedIcon is
 * iosSelectedIcon is used for both states on iOS).
 * iosIconUnselected is used when the item is not selected on iOS (if null, iosIconSelected is used for both states on iOS
 * platforms supporting it).
 * @param selectedIcon The icon to use when the item is selected.
 * @param unSelectedIcon The icon to use when the item is not selected. If null
 *  @param iconSize The size of the icon.
 * @param iosIconSelected The name of the SF Symbol to use when the item is selected
 * on iOS platforms supporting it.
 * @param iosIconUnselected The name of the SF Symbol to use when the item is not selected
 * on iOS platforms supporting it. If null, iosIconSelected is used for
 * @param selectedColor The color to use when the item is selected.
 * @param unselectedColor The color to use when the item is not selected.
 */
data class AdaptiveBottomNavItemIcon(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector? = null,
    val iconSize: Dp = 24.dp,
    val iosIconSelected: String,
    val iosIconUnselected: String? = null,
    val selectedColor: Color = Color.Black,
    val unselectedColor: Color = Color.Black,
) {
    fun getUnselectedIcon(): ImageVector {
        return unSelectedIcon ?: selectedIcon
    }

    fun getIosUnselectedIcon(): String {
        return iosIconUnselected ?: iosIconSelected
    }
}


/**
 * Represents the title/label of the bottom nav item.
 *
 * @param text The default text label to display.
 * @param selectedColor The text color when the item is selected (when using the default renderer).
 * @param unselectedColor The text color when the item is not selected (when using the
 * @param style The text style (font size, weight, etc.) when using the default renderer.
 */
data class AdaptiveBottomNavItemTitle(
    val text: String,
    val selectedColor: Color = Color.Black,
    val unselectedColor: Color = Color.Black,
    val style: TextStyle = TextStyle(fontSize = 14.sp),

    )
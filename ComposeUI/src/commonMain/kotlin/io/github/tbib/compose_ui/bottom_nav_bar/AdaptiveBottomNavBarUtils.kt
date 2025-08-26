package io.github.tbib.compose_ui.bottom_nav_bar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import io.github.tbib.compose_ui.icons.IosIcon

/**
 * Represents a single bottom navigation item
 */
data class AdaptiveBottomNavBarItem<Route>(
    val title: AdaptiveBottomNavItemTitle, // instead of raw Composable
    val icon: AdaptiveBottomNavItemIcon,
    val route: Route,
)
/**
 * Represents the icon of the bottom nav item.
 *
 * @param androidSelectedIcon The icon to display when the item is selected (for Android).
 * @param androidUnSelectedIcon The icon to display when the item is not selected (for Android). If null, the selected icon will be used.
 * @param iosSelectedIcon The icon to display when the item is selected (for iOS).
 * @param iosUnselectedIcon The icon to display when the item is not selected (for iOS). If null, the selected icon will be used.
 */
data class AdaptiveBottomNavItemIcon(
    val androidSelectedIcon: @Composable () -> Unit,
    val androidUnSelectedIcon: (@Composable () -> Unit)? = null,
    val iosSelectedIcon: IosIcon,
    val iosUnselectedIcon: IosIcon? = null,
) {
    fun getAndroidUnselectedIcon(): @Composable () -> Unit {
        return androidUnSelectedIcon ?: androidSelectedIcon
    }

    fun getIosUnselectedIcon(): IosIcon {
        return iosUnselectedIcon ?: iosSelectedIcon
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
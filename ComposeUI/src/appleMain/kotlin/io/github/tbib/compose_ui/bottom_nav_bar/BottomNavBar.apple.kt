package io.github.tbib.compose_ui.bottom_nav_bar

import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.UIKitView
import io.github.tbib.compose_ui.icons.toUIImage
import io.github.tbib.compose_ui.utils.toUiColor
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSDictionary
import platform.Foundation.dictionaryWithDictionary
import platform.QuartzCore.CAMediaTimingFunction
import platform.QuartzCore.CATransition
import platform.QuartzCore.kCAMediaTimingFunctionEaseInEaseOut
import platform.QuartzCore.kCATransitionFade
import platform.UIKit.NSForegroundColorAttributeName
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIColor
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIControlStateSelected
import platform.UIKit.UIImageRenderingMode
import platform.UIKit.UITabBar
import platform.UIKit.UITabBarDelegateProtocol
import platform.UIKit.UITabBarItem
import platform.UIKit.UIView
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun <Route> AdaptiveBottomNavBar(
    modifier: Modifier,
    elevation: Dp,
    items: List<AdaptiveBottomNavBarItem<Route>>,
    alwaysShowLabel: Boolean,
    selectedIndex: Int,
    onSelectedItemIndexChange: (Int) -> Unit,
    androidColors: NavigationBarItemColors,
) {
    // Delegate to handle tab selection
    val delegate = remember {
        object : NSObject(), UITabBarDelegateProtocol {
            override fun tabBar(tabBar: UITabBar, didSelectItem: UITabBarItem) {
                val index = tabBar.items?.indexOf(didSelectItem) ?: -1
                if (index != -1) onSelectedItemIndexChange(index)
            }
        }
    }

    UIKitView(
        factory = {
            // Create UITabBar
            val tabBar = UITabBar().apply {
                this.delegate = delegate
                this.backgroundImage = platform.UIKit.UIImage()
                this.shadowImage = platform.UIKit.UIImage()
                this.barTintColor = UIColor.whiteColor
                this.layer.masksToBounds = false

                val selectedColor = items[selectedIndex].title.selectedColor.toUiColor()
                val unselectedColor = items[selectedIndex].title.unselectedColor.toUiColor()
                this.tintColor = selectedColor
                this.unselectedItemTintColor = unselectedColor

                // Create UITabBarItems from AdaptiveBottomNavBarItem
                val tabBarItems = items.map { item ->
                    val selectedImage = item.icon.iosSelectedIcon.toUIImage()
                        .imageWithRenderingMode(UIImageRenderingMode.UIImageRenderingModeAlwaysTemplate)
                    val unselectedImage = item.icon.getIosUnselectedIcon().toUIImage()
                        .imageWithRenderingMode(UIImageRenderingMode.UIImageRenderingModeAlwaysTemplate)

                    val tabBarItem = UITabBarItem(
                        title = if (alwaysShowLabel) item.title.text else null,
                        image = unselectedImage,
                        selectedImage = selectedImage
                    )

                    val selectedTextAttributes = NSDictionary.dictionaryWithDictionary(
                        mapOf(NSForegroundColorAttributeName to item.title.selectedColor.toUiColor())
                    )
                    val unselectedTextAttributes = NSDictionary.dictionaryWithDictionary(
                        mapOf(NSForegroundColorAttributeName to item.title.unselectedColor.toUiColor())
                    )

                    tabBarItem.setTitleTextAttributes(
                        selectedTextAttributes,
                        UIControlStateSelected
                    )
                    tabBarItem.setTitleTextAttributes(
                        unselectedTextAttributes,
                        UIControlStateNormal
                    )
                    tabBarItem
                }

                setItems(tabBarItems)

            }

            // Container view with shadow
            val container = UIView()
            container.addSubview(tabBar)
            container.layer.shadowOpacity = 0.3f
            container.layer.shadowColor = UIColor.blackColor.CGColor
            container.layer.shadowRadius = elevation.value.toDouble()
            container.layer.shadowOffset = CGSizeMake(0.0, -1.0)

            tabBar.translatesAutoresizingMaskIntoConstraints = false
            NSLayoutConstraint.activateConstraints(
                listOf(
                    tabBar.leadingAnchor.constraintEqualToAnchor(container.leadingAnchor),
                    tabBar.trailingAnchor.constraintEqualToAnchor(container.trailingAnchor),
                    tabBar.topAnchor.constraintEqualToAnchor(container.topAnchor),
                    tabBar.bottomAnchor.constraintEqualToAnchor(container.bottomAnchor)
                )
            )

            container
        },
        update = {
            val tabBar = (it.subviews.firstOrNull() as? UITabBar)
            if (tabBar != null && selectedIndex in (tabBar.items?.indices ?: 0..0)) {
                val currentSelectedItem = tabBar.selectedItem
                val newSelectedItem = tabBar.items?.get(selectedIndex) as? UITabBarItem
                if (currentSelectedItem != newSelectedItem && newSelectedItem != null) {
                    val transition = CATransition()
                    transition.duration = 0.2
                    transition.type = kCATransitionFade
                    transition.timingFunction = CAMediaTimingFunction.functionWithName(
                        kCAMediaTimingFunctionEaseInEaseOut
                    )
                    tabBar.layer.addAnimation(transition, forKey = "")
                    tabBar.selectedItem = newSelectedItem
                }
            }
        },
        onRelease = {
            (it.subviews.firstOrNull() as? UITabBar)?.delegate = null
        },
        modifier = modifier
    )
}

package io.github.tbib.compose_ui.bottom_nav_bar

import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.UIKitView
import io.github.tbib.compose_ui.utils.toUiColor
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDictionary
import platform.Foundation.dictionaryWithDictionary
import platform.QuartzCore.CAMediaTimingFunction
import platform.QuartzCore.CATransition
import platform.QuartzCore.kCAMediaTimingFunctionEaseInEaseOut
import platform.QuartzCore.kCATransitionFade
import platform.UIKit.NSForegroundColorAttributeName
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIColor
import platform.UIKit.UIImage
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
    // We need to remember the delegate so it's not garbage-collected.
    val delegate = remember {
        object : NSObject(), UITabBarDelegateProtocol {
            override fun tabBar(tabBar: UITabBar, didSelectItem: UITabBarItem) {
                val index = tabBar.items?.indexOf(didSelectItem) ?: -1
                if (index != -1) {
                    onSelectedItemIndexChange(index)
                }
            }
        }
    }

    UIKitView(
        factory = {
            // All UIKit view creation and configuration should happen here.
            val tabBar = UITabBar().apply {
                this.delegate = delegate

                // --- ICON COLORS ---
                if (selectedIndex < items.size) {
                    this.tintColor = items[selectedIndex].icon.selectedColor.toUiColor()
                    this.unselectedItemTintColor =
                        items[selectedIndex].icon.unselectedColor.toUiColor()
                }

                // --- ELEVATION (SHADOW) ---
                this.backgroundImage = UIImage()
                this.shadowImage = UIImage()
                this.barTintColor = UIColor.whiteColor

                // The shadow properties are on the container view, not the UITabBar itself,
                // because the UITabBar's own shadow is part of its internal structure
                // and hard to customize in this way. We will apply the shadow to a container
                // view that holds the UITabBar.

                // --- TAB ITEMS ---
                val tabBarItems = items.mapIndexed { _, item ->
//                    val iconSize = item.icon.iconSize.value.toDouble()
                    val unselectedImage = UIImage.systemImageNamed(item.icon.getIosUnselectedIcon())
//                        ?.resize(iconSize, iconSize)
                        ?.imageWithRenderingMode(UIImageRenderingMode.UIImageRenderingModeAlwaysTemplate)

                    val selectedImage = UIImage.systemImageNamed(item.icon.iosIconSelected)
//                        ?.resize(iconSize, iconSize)
                        ?.imageWithRenderingMode(UIImageRenderingMode.UIImageRenderingModeAlwaysTemplate)

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
                        platform.UIKit.UIControlStateSelected
                    )
                    tabBarItem.setTitleTextAttributes(
                        unselectedTextAttributes,
                        platform.UIKit.UIControlStateNormal
                    )

                    tabBarItem
                }

                setItems(tabBarItems)
            }


            // Create a container view to hold the tab bar.
            val container = UIView()
            container.addSubview(tabBar)

            // Apply shadow to the container view, not the tab bar.
            container.layer.shadowOpacity = 0.3f
            container.layer.shadowColor = UIColor.blackColor.CGColor
            container.layer.shadowRadius = elevation.value.toDouble()
            container.layer.shadowOffset =
                platform.CoreGraphics.CGSizeMake(0.0, -1.0) // iOS shadow typically is on top
            container.layer.masksToBounds = false

            // Use Auto Layout to make the tab bar fill the container.
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
            // This block is called when composable state changes.
            // We update the selected item and colors here.
            val tabBar = (it.subviews.firstOrNull() as? UITabBar)

            if (tabBar != null) {
                // --- UPDATE ICON COLORS ON STATE CHANGE ---
                if (selectedIndex < items.size) {
                    tabBar.tintColor = items[selectedIndex].icon.selectedColor.toUiColor()
                    tabBar.unselectedItemTintColor =
                        items[selectedIndex].icon.unselectedColor.toUiColor()
                }

                if (selectedIndex >= 0 && selectedIndex < (tabBar.items?.size ?: 0)) {
                    val currentSelectedItem = tabBar.selectedItem
                    val newSelectedItem = tabBar.items?.get(selectedIndex) as? UITabBarItem

                    // Check if the selected item is actually changing to avoid unnecessary animations.
                    if (currentSelectedItem != newSelectedItem) {
                        // Create a CATransition for the fade animation.
                        val transition = CATransition()
                        transition.duration = 0.2 // Adjust the duration as needed.
                        transition.type = kCATransitionFade // Use a fade transition type.
                        transition.timingFunction = CAMediaTimingFunction.functionWithName(
                            kCAMediaTimingFunctionEaseInEaseOut
                        )

                        // Add the transition to the layer of the tab bar.
                        tabBar.layer.addAnimation(forKey = null, anim = transition)

                        // Set the new selected item. This change will now be animated.
                        tabBar.selectedItem = newSelectedItem
                    }
                }
            }
        },
        onRelease = {
            // Clean up the delegate to prevent memory leaks.
            (it.subviews.firstOrNull() as? UITabBar)?.delegate = null
        },
        modifier = modifier
    )
}
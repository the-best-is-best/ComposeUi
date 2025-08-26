package io.github.tbib.compose_ui.bottom_nav_bar

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

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
    NavigationBar(
        tonalElevation = elevation,
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            NavigationBarItem(
                selected = isSelected,
                onClick = { onSelectedItemIndexChange(index) },
                icon = {
                    if (isSelected) item.icon.androidSelectedIcon()
                    else item.icon.getAndroidUnselectedIcon()
                },
                label = {
                    Text(
                        item.title.text,
                        color = if (isSelected) item.title.selectedColor else item.title.unselectedColor,
                        style = item.title.style
                    )
                },
                alwaysShowLabel = alwaysShowLabel,
                colors = androidColors
            )
        }
    }
}
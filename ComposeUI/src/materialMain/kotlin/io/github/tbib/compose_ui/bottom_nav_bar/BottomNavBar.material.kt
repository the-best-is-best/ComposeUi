package io.github.tbib.compose_ui.bottom_nav_bar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
actual fun <Route> BottomNavBar(
    modifier: Modifier,
    elevation: Dp,
    items: List<BottomNavItem<Route>>,
    alwaysShowLabel: Boolean,
    selectedIndex: Int,
    onSelectedItemIndexChange: (Int) -> Unit
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
                    androidx.compose.material3.Icon(
                        modifier = Modifier.size(item.icon.iconSize),
                        imageVector = if (isSelected) item.icon.selectedIcon else item.icon.getUnselectedIcon(),
                        contentDescription = null,
                        tint = if (isSelected) item.icon.selectedColor else item.icon.unselectedColor
                    )
                },
                label = {
                    Text(
                        item.title.text,
                        color = if (isSelected) item.title.selectedColor else item.title.unselectedColor,
                        style = item.title.style
                    )
                },
                alwaysShowLabel = alwaysShowLabel,
            )
        }
    }
}
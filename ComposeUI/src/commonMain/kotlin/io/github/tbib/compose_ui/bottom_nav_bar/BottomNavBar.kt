package io.github.tbib.compose_ui.bottom_nav_bar

import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
expect fun <Route> BottomNavBar(
    modifier: Modifier = Modifier,
    elevation: Dp = NavigationBarDefaults.Elevation,
    items: List<BottomNavItem<Route>>,
    alwaysShowLabel: Boolean = true,
    selectedIndex: Int = 0,
    onSelectedItemIndexChange: (Int) -> Unit,
)
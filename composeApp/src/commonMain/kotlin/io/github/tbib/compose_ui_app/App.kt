package io.github.tbib.compose_ui_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SyncLock
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.tbib.compose_ui.bottom_nav_bar.AdaptiveBottomNavBar
import io.github.tbib.compose_ui.bottom_nav_bar.AdaptiveBottomNavBarItem
import io.github.tbib.compose_ui.bottom_nav_bar.AdaptiveBottomNavItemIcon
import io.github.tbib.compose_ui.bottom_nav_bar.AdaptiveBottomNavItemTitle
import org.jetbrains.compose.ui.tooling.preview.Preview

val items = listOf(
    AdaptiveBottomNavBarItem<Routes>(
        title = AdaptiveBottomNavItemTitle(
            text = "Home",
            selectedColor = Color.Red,
            unselectedColor = Color.Gray
        ),
        icon = AdaptiveBottomNavItemIcon(
            selectedIcon = Icons.Filled.Home,
            iosIconSelected = "house.fill",
            selectedColor = Color.Blue,
            unselectedColor = Color.Gray
        ),
        route = Routes.Home
    ),
    AdaptiveBottomNavBarItem<Routes>(
        title = AdaptiveBottomNavItemTitle(
            text = "History",
            selectedColor = Color.Red,
            unselectedColor = Color.Gray

        ),
        icon = AdaptiveBottomNavItemIcon(
            selectedIcon = Icons.Filled.CalendarMonth,
            iosIconSelected = "calendar.circle.fill",
            selectedColor = Color.Blue,
            unselectedColor = Color.Gray
        ),
        route = Routes.History
    ),
    AdaptiveBottomNavBarItem<Routes>(
        title = AdaptiveBottomNavItemTitle(
            text = "Availability",
            selectedColor = Color.Red,
            unselectedColor = Color.Gray

        ),
        icon = AdaptiveBottomNavItemIcon(
            selectedIcon = Icons.Filled.SyncLock,
            iosIconSelected = "clock.fill",
            selectedColor = Color.Blue,
            unselectedColor = Color.Gray
        ),
        route = Routes.History
    ),
)

@Composable
@Preview
fun App() {
    var selectedIndex by remember { mutableStateOf(0) }
    MaterialTheme {
        Scaffold(
            bottomBar = {
                AdaptiveBottomNavBar(
                    items = items,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 30.dp,
                                topEnd = 30.dp
                            )
                        ),
                    alwaysShowLabel = true,
                    selectedIndex = selectedIndex,
                    onSelectedItemIndexChange = {
                        selectedIndex = it
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                when (selectedIndex) {
                    0 -> LazyColumn {
                        items(100) {
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                Text("Item #$it", color = Color.Black)
                            }
                        }
                    }

                    1 -> Text("History Screen", color = Color.Black)
                    2 -> Text("Availability Screen", color = Color.Black)
                }
            }
        }
    }
}

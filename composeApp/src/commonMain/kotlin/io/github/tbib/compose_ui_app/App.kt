package io.github.tbib.compose_ui_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.tbib.compose_ui.adaptive_circular_progressIndicator.AdaptiveCircularProgressIndicator
import io.github.tbib.compose_ui.bottom_nav_bar.AdaptiveBottomNavBar
import io.github.tbib.compose_ui.bottom_nav_bar.AdaptiveBottomNavBarItem
import io.github.tbib.compose_ui.bottom_nav_bar.AdaptiveBottomNavItemIcon
import io.github.tbib.compose_ui.bottom_nav_bar.AdaptiveBottomNavItemTitle
import io.github.tbib.compose_ui_app.dialogs.DeleteDialogQuestion
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
            selectedIcon = Icons.Filled.Details,
            iosIconSelected = "ellipsis.circle", // Details
            selectedColor = Color.Blue,
            unselectedColor = Color.Gray
        ),
        route = Routes.Details
    ),
)


@Composable
@Preview
fun App() {
    var selectedIndex by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteDialogQuestion(
            onConfirm = {
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            },
            args = "Some item to delete"
        )
    }

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
            Box(
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

                    1 -> LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            10.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        item {
                            AdaptiveCircularProgressIndicator()
                        }
                        item {
                            Button(onClick = {
                                showDialog = true
                            }) {
                                Text("Show Dialog", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

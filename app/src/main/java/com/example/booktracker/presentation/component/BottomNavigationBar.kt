package com.example.booktracker.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.booktracker.navigation.Screen
import com.example.booktracker.presentation.component.icon.LibraryBig
import com.example.booktracker.presentation.component.icon.Person
import com.example.booktracker.presentation.component.icon.Search
import com.example.ui.theme.AppTypography

sealed class ScreenItem(val screen: Screen, val imageVector: ImageVector, val label: String) {
    object Library : ScreenItem(Screen.Library, LibraryBig, "Library")
    object Discover : ScreenItem(Screen.Discover, Search, "Discover")
    object Profile : ScreenItem(Screen.Profile, Person, "Profile")
}

@Composable
fun BottomNavigationBar(navController: NavController, currentScreen: Screen?) {
    val items = listOf(
        ScreenItem.Library,
        ScreenItem.Discover,
        ScreenItem.Profile
    )

    NavigationBar {
        items.forEach { screenItem ->
            NavigationBarItem(
                selected = currentScreen == screenItem.screen,
                onClick = {
                    navController.navigate(screenItem.screen) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(imageVector = screenItem.imageVector, contentDescription = null) },
                label = { Text(text = screenItem.label, style = AppTypography.labelMedium) })
        }
    }
}
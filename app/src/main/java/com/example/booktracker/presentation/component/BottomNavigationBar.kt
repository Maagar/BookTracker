package com.example.booktracker.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.booktracker.R
import com.example.booktracker.navigation.Screen
import com.example.booktracker.presentation.component.icon.LibraryBig
import com.example.booktracker.presentation.component.icon.Person
import com.example.booktracker.presentation.component.icon.Search
import com.example.ui.theme.AppTypography

sealed class ScreenItem(val screen: Screen, val imageVector: ImageVector, val label: Int) {
    object Library : ScreenItem(Screen.Library, LibraryBig, R.string.library)
    object Discover : ScreenItem(Screen.Discover, Search,R.string.discover)
    object Profile : ScreenItem(Screen.Profile, Person, R.string.profile)
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
            val isSelected = currentScreen == screenItem.screen
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected){
                        navController.navigate(screenItem.screen) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(imageVector = screenItem.imageVector, contentDescription = null) },
                label = { Text(text = stringResource(screenItem.label), style = AppTypography.labelMedium) })
        }
    }
}
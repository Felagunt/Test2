package com.example.testtests.core.route

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController

@Composable
fun BottomAppBar(appState: AppState, navController: NavHostController) {
    androidx.compose.material3.BottomAppBar {
        BottomNavigation.entries.forEach { navigationItem ->
            val isSelected by remember {
                derivedStateOf { appState.currentRoute == navigationItem.route::class.qualifiedName }
            }

            NavigationBarItem(
                selected = isSelected,
                label = { Text(navigationItem.label) },
                icon = {
                    Icon(imageVector = navigationItem.icon, contentDescription = navigationItem.label)
                },
                onClick = {
                    navigationItem.route::class.qualifiedName?.let { appState.updateCurrentRoute(it) }
                    navController.navigate(navigationItem.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                )
            )
        }
    }
}

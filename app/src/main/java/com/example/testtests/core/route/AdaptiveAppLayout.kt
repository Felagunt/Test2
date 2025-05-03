package com.example.testtests.core.route

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

@Composable
fun AdaptiveAppLayout(appState: AppState, navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val isSmallScreen = configuration.screenWidthDp < 600

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val showBottomBar = appState.showBottomBar && isPortrait && isSmallScreen

    if (!showBottomBar) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    navController = navController,
                    closeDrawer = { scope.launch { drawerState.close() } }
                )
            }
        ) {
            ScaffoldContent(appState, navController, showBottomBar = false)
        }
    } else {
        ScaffoldContent(appState, navController, showBottomBar = true)
    }
}

@Composable
fun ScaffoldContent(
    appState: AppState,
    navController: NavHostController,
    showBottomBar: Boolean
) {
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(appState, navController)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            RootNavGraph(appState, navController)
        }
    }
}

@Composable
fun BottomNavBar(appState: AppState, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.orEmpty()

    val currentRouteTrimmed = currentRoute.substringBefore("?")

    BottomAppBar {
        BottomNavigation.entries.forEach { navigationItem ->
            val isSelected = currentRouteTrimmed == navigationItem.route::class.qualifiedName

            NavigationBarItem(
                selected = isSelected,
                label = { Text(navigationItem.label) },
                icon = {
                    Icon(
                        imageVector = navigationItem.icon,
                        contentDescription = navigationItem.label
                    )
                },
                onClick = {
                    val routeName = navigationItem.route::class.qualifiedName
                    if (routeName != null && routeName != currentRouteTrimmed) {
                        appState.updateCurrentRoute(routeName)
                        navController.navigate(navigationItem.route)
                    }
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

@Composable
fun DrawerContent(
    navController: NavHostController,
    closeDrawer: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        BottomNavigation.entries.forEach { item ->
            Text(
                text = item.label,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(item.route)
                        closeDrawer()
                    }
                    .padding(16.dp)
            )
        }
    }
}

package com.example.testtests.core.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.testtests.ui.theme.AppTheme
import com.example.ui.auth.LogInViewModel
import com.example.ui.auth_check.AuthCheckScreen
import kotlinx.coroutines.delay

@Composable
fun RootNavGraph(
    appState: AppState,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SubGraph.Auth,
        exitTransition = composable@{
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(700)
            )
        },
        popEnterTransition = composable@{
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )
        }
    ) {
        authGraph(navController)
        mainGraph(appState, navController)
    }
}
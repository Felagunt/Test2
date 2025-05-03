package com.example.testtests.core.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.common.data.AuthHandler

val LocalAuthHandler = compositionLocalOf<AuthHandler> {
    error("No auth handler")
}

@Composable
fun App() {
    val authHandler: AuthHandler = LocalAuthHandler.current
    //val authHandler: AuthHandler = remember { AuthHandler() }
    val appState = remember { AppState(authHandler = authHandler) }//TODO
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.orEmpty()

    val isInAuthFlow = when (currentRoute) {
        Auth.Onboarding::class.qualifiedName,
        Auth.LogIn::class.qualifiedName,
        Auth.AuthCheck::class.qualifiedName -> true

        else -> false
    }

    // Provide appState globally (via a CompositionLocal or through viewmodels)
    CompositionLocalProvider(LocalAuthHandler provides appState.authHandler) {
        if (isInAuthFlow) {
            NavHost(
                navController = navController,
                startDestination = SubGraph.Auth
            ) {
                authGraph(navController)
            }
        } else {
            AdaptiveAppLayout(appState, navController)
        }

    }
}
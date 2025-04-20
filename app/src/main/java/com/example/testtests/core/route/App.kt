package com.example.testtests.core.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.example.common.data.AuthHandler

val LocalAuthHandler = compositionLocalOf<AuthHandler> { error("No auth handler") }

@Composable
fun App() {
    val authHandler: AuthHandler = LocalAuthHandler.current
    //val authHandler: AuthHandler = remember { AuthHandler() }
    val appState = remember { AppState(authHandler = authHandler) }//TODO
    val navController = rememberNavController()


    // Provide appState globally (via a CompositionLocal or through viewmodels)
    CompositionLocalProvider(LocalAuthHandler provides appState.authHandler) {
        RootNavGraph(appState, navController)

    }
}
package com.example.testtests.core.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost

@Composable
fun App() {
    val appState = remember { AppState() }
    val navController = rememberNavController()

    // Provide appState globally (via a CompositionLocal or through viewmodels)
    RootNavGraph(appState, navController)
}
package com.example.testtests.core.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.testtests.ui.theme.AppTheme

@Composable
fun RootNavGraph() {
    AppTheme {


    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SubGraph.Auth
    ) {
        navigation<SubGraph.Auth>(
            startDestination = Auth.Onboarding
        ) {
            composable<Auth.Onboarding> {
                com.example.ui.dashboard.Onboarding(
                    onClick = {
                        navController.navigate(Auth.LogIn) {
                            popUpTo<Auth.Onboarding> {
                                saveState = false
                                inclusive = true
                            }
                        }

                    }
                )
            }

            composable<Auth.LogIn> {
                val viewModel = hiltViewModel<com.example.ui.auth.LogInViewModel>()
                val isLoggedIn = viewModel.isLoggedIn.collectAsStateWithLifecycle()
                LaunchedEffect(isLoggedIn) {
                    navController.navigate(SubGraph.DestGraph)
                }
                com.example.ui.auth.LogInScreenRoot(
                    viewModel = viewModel,
                    onLogInClick = {
                        navController.navigate(SubGraph.DestGraph) {
                            popUpTo<Auth.LogIn> {
                                saveState = false
                                inclusive = true
                            }
                        }
                    }
                )
            }

        }
        composable<SubGraph.DestGraph>{
            BottomBar()
        }
    }
    }
}
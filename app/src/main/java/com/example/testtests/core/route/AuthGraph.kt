package com.example.testtests.core.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ui.auth.LogInScreenRoot
import com.example.ui.auth.LogInViewModel
import com.example.ui.dashboard.Onboarding

@Composable
fun AuthGraph(navController: NavHostController) {

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

    }
}
package com.example.testtests.core.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.ui.auth.LogInScreenRoot
import com.example.ui.auth.LogInViewModel
import com.example.ui.auth_check.AuthCheckScreen
import com.example.ui.dashboard.Onboarding

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<SubGraph.Auth>(
        startDestination = Auth.AuthCheck,
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
        composable<Auth.AuthCheck> {
            val viewModel = hiltViewModel<LogInViewModel>()
            AuthCheckScreen(
                viewModel = viewModel,
                onLoggedIn = {
                    navController.navigate(SubGraph.DestGraph) {
                        popUpTo(SubGraph.Auth) { inclusive = true }
                    }
                },
                onNotLoggedIn = {
                    navController.navigate(Auth.Onboarding) {
                        popUpTo(SubGraph.Auth) { inclusive = true }
                    }
                }
            )
        }

        composable<Auth.Onboarding> {
            Onboarding(
                onClick = {
                    navController.navigate(Auth.LogIn) {
                        popUpTo(Auth.Onboarding) { inclusive = true }
                    }
                }
            )
        }

        composable<Auth.LogIn> {
            val viewModel = hiltViewModel<com.example.ui.auth.LogInViewModel>()
            LogInScreenRoot(
                viewModel = viewModel,
                onLogInClick = {
                    navController.navigate(SubGraph.DestGraph) {
                        popUpTo(Auth.LogIn) { inclusive = true }
                    }
                }
            )
        }
    }
}

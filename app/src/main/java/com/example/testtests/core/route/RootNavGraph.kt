package com.example.testtests.core.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.testtests.ui.theme.AppTheme
import com.example.ui.auth.LogInViewModel
import com.example.ui.auth_check.AuthCheckScreen
import kotlinx.coroutines.delay

@Composable
fun RootNavGraph() {
//    private companion object {
//        const val ANIMATION_DURATION = 700
//    }
    AppTheme {


        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = SubGraph.Auth
        ) {
            navigation<SubGraph.Auth>(
                startDestination = Auth.AuthCheck
            ) {

                composable<Auth.AuthCheck>(
                    exitTransition = {
                        return@composable slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start,
                            tween(700)
                        )
                    },
                    popEnterTransition = {
                        return@composable slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.End,
                            tween(700)
                        )
                    }
                ) {
                    val viewModel = hiltViewModel<LogInViewModel>()
                    AuthCheckScreen(
                        viewModel = viewModel,
                        onLoggedIn = {
                            navController.navigate(SubGraph.DestGraph) {
                                popUpTo<SubGraph.DestGraph> {
                                    inclusive = true
                                }
                            }
                        },
                        onNotLoggedIn = {
                            navController.navigate(Auth.Onboarding) {
                                popUpTo<SubGraph.DestGraph> {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

                composable<Auth.Onboarding>(
                    exitTransition = {
                        return@composable slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start,
                            tween(700)
                        )
                    },
                    popEnterTransition = {
                        return@composable slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.End,
                            tween(700)
                        )
                    }
                ) {
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

                composable<Auth.LogIn>(
                    exitTransition = {
                        return@composable slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start,
                            tween(700)
                        )
                    },
                    popEnterTransition = {
                        return@composable slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.End,
                            tween(700)
                        )
                    }
                ) {
                    val viewModel = hiltViewModel<com.example.ui.auth.LogInViewModel>()
//                    val isLoggedIn = viewModel.isLoggedIn.collectAsStateWithLifecycle()
//                    LaunchedEffect(isLoggedIn) {
//                        delay(2000)
//                        navController.navigate(SubGraph.DestGraph)
//                    }
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
            composable<SubGraph.DestGraph> {
                BottomBar()
            }
        }
    }

}
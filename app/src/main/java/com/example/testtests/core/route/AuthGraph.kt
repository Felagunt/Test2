package com.example.testtests.core.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ui.auth.LogInScreenRoot
import com.example.ui.auth.LogInViewModel
import com.example.ui.auth_check.AuthCheckScreen
import com.example.ui.dashboard.Onboarding
//
//@Composable
//fun AuthGraph(navController: NavHostController) {
//
//    NavHost(
//        navController = navController,
//        startDestination = SubGraph.Auth
//    ) {
//        navigation<SubGraph.Auth>(
//            startDestination = Auth.AuthCheck
//        ) {
//
//            composable<Auth.AuthCheck>(
//                exitTransition = {
//                    return@composable slideOutOfContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Start,
//                        tween(700)
//                    )
//                },
//                popEnterTransition = {
//                    return@composable slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.End,
//                        tween(700)
//                    )
//                }
//            ) {
//                AuthCheckScreen(
//                    onLoggedIn = {
//                        navController.navigate(SubGraph.DestGraph) {
//                            popUpTo<SubGraph.Auth> {
//                                inclusive = true
//                            }
//                        }
//                    },
//                    onNotLoggedIn = {
//                        navController.navigate(Auth.Onboarding) {
//                            popUpTo<SubGraph.Auth> {
//                                inclusive = true
//                            }
//                        }
//                    }
//                )
//            }
//
//            composable<Auth.Onboarding>(
//                exitTransition = {
//                    return@composable slideOutOfContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Start,
//                        tween(700)
//                    )
//                },
//                popEnterTransition = {
//                    return@composable slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.End,
//                        tween(700)
//                    )
//                }
//            ) {
//                Onboarding(
//                    onClick = {
//                        navController.navigate(Auth.LogIn) {
//                            popUpTo<Auth.Onboarding> {
//                                saveState = false
//                                inclusive = true
//                            }
//                        }
//
//                    }
//                )
//            }
//
//            composable<Auth.LogIn>(
//                exitTransition = {
//                    return@composable slideOutOfContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Start,
//                        tween(700)
//                    )
//                },
//                popEnterTransition = {
//                    return@composable slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.End,
//                        tween(700)
//                    )
//                }
//            ) {
//                val viewModel = hiltViewModel<LogInViewModel>()
//                LogInScreenRoot(
//                    viewModel = viewModel,
//                    onLogInClick = {
//                        navController.navigate(SubGraph.DestGraph) {
//                            popUpTo<Auth.LogIn> {
//                                saveState = false
//                                inclusive = true
//                            }
//                        }
//                    }
//                )
//            }
//        }
//
//    }
//}
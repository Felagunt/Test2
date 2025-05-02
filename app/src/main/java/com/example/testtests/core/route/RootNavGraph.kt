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
fun RootNavGraph(appState: AppState, navController: NavHostController) {

        //val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = SubGraph.Auth,
            exitTransition = {
                 slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            },
            popEnterTransition = {
                 slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700)
                )
            }
        ) {
            navigation<SubGraph.Auth>(
                startDestination = Auth.AuthCheck
            ) {

                composable<Auth.AuthCheck>(

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
                BottomBar(appState, navController)
            }
        }


}
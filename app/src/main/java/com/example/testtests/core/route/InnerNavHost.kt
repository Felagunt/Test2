package com.example.testtests.core.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.courses.ui.course_details.CourseDetailsScreenRoot
import com.example.courses.ui.course_details.CourseDetailsViewModel
import com.example.courses.ui.courses_list.AllCoursesScreenRoot
import com.example.courses.ui.courses_list.AllCoursesViewModel
import com.example.courses.ui.favorite.FavoriteScreenRoot
import com.example.courses.ui.favorite.FavoriteViewModel
import com.example.courses.ui.profile.ProfileScreen
import com.example.courses.ui.profile.ProfileViewModel

@Composable
fun InnerNavHost(
    appState: AppState,
    navController: NavHostController
) {

    NavHost(
        modifier = Modifier
            .fillMaxSize(),
        navController = navController,
        startDestination = SubGraph.DestGraph,
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


        navigation<SubGraph.DestGraph>(
            startDestination = Dest.Home,
        ) {
            composable<Dest.Home>(

            ) {
                val viewModel = hiltViewModel<AllCoursesViewModel>()
                AllCoursesScreenRoot(
                    viewModel = viewModel,
                    onClickCourse = { course ->
                        navController.navigate(
                            SubGraph.CourseDetails(course.id)
                        )
                    }
                )
            }

            composable<SubGraph.CourseDetails>(

            ) {
                val args = it.toRoute<SubGraph.CourseDetails>()
                val viewModel = hiltViewModel<CourseDetailsViewModel>()
                CourseDetailsScreenRoot(
                    id = args.id,
                    viewModel = viewModel,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )

            }
            composable<Dest.Favorite>(

            ) {
                val viewModel = hiltViewModel<FavoriteViewModel>()
                FavoriteScreenRoot(
                    viewModel = viewModel,
                    onCourseClick = { course ->
                        navController.navigate(
                            SubGraph.CourseDetails(course.id)
                        )
                    }
                )
            }

            composable<Dest.Profile>(

            ) {
                val viewModel = hiltViewModel<ProfileViewModel>()
                ProfileScreen(
                    viewModel = viewModel,
                    onLogOut = {
                        navController.navigate(Auth.LogIn) {
                            popUpTo<SubGraph.DestGraph> {//TODO
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
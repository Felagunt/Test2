package com.example.testtests.core.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navigation
import com.example.courses.ui.course_details.CourseDetailsScreenRoot
import com.example.courses.ui.course_details.CourseDetailsViewModel
import com.example.courses.ui.courses_list.AllCoursesScreenRoot
import com.example.courses.ui.courses_list.AllCoursesViewModel
import com.example.courses.ui.favorite.FavoriteScreenRoot
import com.example.courses.ui.favorite.FavoriteViewModel
import com.example.courses.ui.profile.ProfileScreen
import com.example.courses.ui.profile.ProfileViewModel

fun NavGraphBuilder.mainGraph(
    appState: AppState,
    navController: NavHostController
) {
    navigation<SubGraph.DestGraph>(
        startDestination = Dest.Home,
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
        composable<Dest.Home> {
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

        composable<SubGraph.CourseDetails> { backStackEntry ->
            val args = backStackEntry.arguments?.getInt("id") ?: return@composable
            val viewModel = hiltViewModel<CourseDetailsViewModel>()
            CourseDetailsScreenRoot(
                id = args,
                viewModel = viewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable<Dest.Favorite> {
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

        composable<Dest.Profile> {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                viewModel = viewModel,
                onLogOut = {
                    navController.navigate(Auth.LogIn) {
                        popUpTo<SubGraph.DestGraph> {
                            saveState = false
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

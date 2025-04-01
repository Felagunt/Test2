package com.example.testtests.core.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.ui.auth.LogInScreenRoot
import com.example.ui.auth.LogInViewModel
import com.example.courses.ui.courses_list.AllCoursesScreenRoot
import com.example.courses.ui.courses_list.AllCoursesViewModel
import com.example.ui.dashboard.Onboarding
import com.example.courses.ui.favorite.FavoriteScreenRoot
import com.example.courses.ui.favorite.FavoriteViewModel
import com.example.courses.ui.profile.ProfileScreen

@Composable
fun BottomBar() {


    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: BottomNavigation.HOME.route::class.qualifiedName.orEmpty()

    val currentRouteTrimmed by remember(currentRoute) {
        derivedStateOf { currentRoute.substringBefore("?") }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomNavigation.entries
                    .forEachIndexed { index, navigationItem ->


                        val isSelected by remember(currentRoute) {
                            derivedStateOf { currentRouteTrimmed == navigationItem.route::class.qualifiedName }
                        }


                        NavigationBarItem(
                            selected = isSelected,
                            label = { Text(navigationItem.label) },
                            icon = {
                                Icon(
                                    imageVector = navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            },
                            onClick = {
                                navController.navigate(navigationItem.route)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.secondary,
                                selectedTextColor = MaterialTheme.colorScheme.secondary,
                                indicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                            )
                        )
                    }
            }
        }
    ) {

        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            navController = navController,
            startDestination = SubGraph.DestGraph
        ) {


            navigation<SubGraph.DestGraph>(
                startDestination = Dest.Home,
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

                composable<SubGraph.CourseDetails> {

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
                    ProfileScreen()
                }
            }
        }
    }
}

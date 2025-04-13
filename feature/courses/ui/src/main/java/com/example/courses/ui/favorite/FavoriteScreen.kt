package com.example.courses.ui.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.presentation.components.ErrorScreen
import com.example.common.presentation.components.LoadingScreen
import com.example.courses.ui.courses_list.components.CourseListItem
import com.example.courses.domain.models.Course

@Composable
fun FavoriteScreenRoot(
    viewModel: FavoriteViewModel = hiltViewModel<FavoriteViewModel>(),
    onCourseClick: (Course) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FavoriteScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is FavoriteAction.OnCourseClick -> {
                    onCourseClick(action.course)
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun FavoriteScreen(
    state: FavoriteState,
    onAction: (FavoriteAction) -> Unit
) {


    Scaffold(

    ) { paddingValues ->
        state.favoriteList?.let { list ->

            Column(
                modifier = Modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (state.isLoading) {
                    LoadingScreen(
                        modifier = Modifier,
                        paddingValues = paddingValues
                    )
                }
//                if (state.error?.isNotEmpty() == true) {
//                    ErrorScreen(
//                        modifier = Modifier,
//                        error = state.error,
//                        paddingValues = paddingValues
//                    )
//                }
                if (state.favoriteList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(12.dp)
                    ) {
                        items(list) { course ->
                            CourseListItem(
                                course = course,
                                modifier = Modifier,
                                onFavoriteClick = {
                                    onAction(FavoriteAction.OnFavoriteClick(course))
                                }
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Add courses to show on favorite screen",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

        }
    }
}
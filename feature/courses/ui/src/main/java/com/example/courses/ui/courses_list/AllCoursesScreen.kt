package com.example.courses.ui.courses_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.courses.ui.courses_list.components.CourseListItem
import com.example.common.presentation.components.SearchCourseBar
import com.example.courses.domain.models.Course
import com.example.courses.ui.R

@Composable
fun AllCoursesScreenRoot(
    viewModel: AllCoursesViewModel = hiltViewModel(),
    onClickCourse: (Course) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AllCoursesScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AllCoursesAction.OnCourseClick -> {
                    onClickCourse(action.course)
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
private fun AllCoursesScreen(
    state: AllCoursesState,
    onAction: (AllCoursesAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            SearchCourseBar(
                searchQuery = state.searchQuery ?: "",
                onSearchQueryChange = {
                    onAction(AllCoursesAction.OnSearchQueryChange(it))//TODO
                },
                onImeSearch = {
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .padding(top = 4.dp)
            )
        }
    ) { paddingValues ->
        state.courseList?.let { list ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),

            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top
                ) {
                    Button(
                        onClick = {
                            onAction(AllCoursesAction.OnSortClick)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.on_date_of_update),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary,

                        )
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                if (state.errorMsg?.isNotEmpty() == true) {
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.errorMsg,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                if (state.courseList.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(list) { course ->
                            CourseListItem(
                                course = course,
                                onFavoriteClick = {
                                    onAction(AllCoursesAction.OnFavoriteClick(it))
                                }
                            )
                        }
                    }
                }
            }

        }
    }

}
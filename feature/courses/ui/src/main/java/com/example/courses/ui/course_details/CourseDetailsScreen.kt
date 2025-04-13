package com.example.courses.ui.course_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.common.presentation.components.ErrorScreen
import com.example.common.presentation.components.LoadingScreen

@Composable
fun CourseDetailsScreenRoot(
    id: Int?,
    viewModel: CourseDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = id) {
        id?.let {
            viewModel.onAction(CourseDetailsAction.FetchCourse(it))
        }
    }
    CourseDetailsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is CourseDetailsAction.OnNavigationBack -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CourseDetailsScreen(
    state: CourseDetailsState,
    onAction: (CourseDetailsAction) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.course?.title ?: "Details")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onAction(CourseDetailsAction.OnNavigationBack)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onAction(CourseDetailsAction.OnAddFavoriteTvShow)
                    }) {
                        Icon(
                            imageVector = //Icons.Default.FavoriteBorder,
                            if (state.course?.hasLike == true) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Outlined.FavoriteBorder
                            },
                            tint = MaterialTheme.colorScheme.surfaceTint,
                            contentDescription = //"Add to favorite"
                            if (state.course?.hasLike == true) {
                                "Add to favorite"
                            } else {
                                "Remove from favorite"
                            }
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            LoadingScreen(
                modifier = Modifier,
                paddingValues = paddingValues
            )
        }
        if (state.errorMsg?.isNotEmpty() == true) {
            ErrorScreen(
                modifier = Modifier,
                error = state.errorMsg,
                paddingValues = paddingValues
            )
        }
        state.course?.let { course ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()) // Добавляем скролл, если контент не помещается
            ) {
                AsyncImage(
                    model = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.IpJgQO7w8-XI4RcZ0iElWQHaE7%26pid%3DApi&f=1&ipt=d4497a5c287cd8d224fea126b015c09f7a332d6112853a9fd2e992c6659c0aa0&ipo=images",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(114.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = course.text,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Дата начала: ${course.startDate}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Цена: ${course.price}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Рейтинг: ${course.rate}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Дата публикации: ${course.publishDate}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
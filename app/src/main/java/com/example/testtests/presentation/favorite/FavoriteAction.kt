package com.example.testtests.presentation.favorite

import com.example.testtests.domain.models.Course

sealed interface FavoriteAction {
    data class OnFavoriteClick(val course: Course): FavoriteAction
    data class OnCourseClick(val course: Course): FavoriteAction
}
package com.example.courses.ui.favorite

import com.example.courses.domain.models.Course

sealed interface FavoriteAction {
    data class OnFavoriteClick(val course: Course): FavoriteAction
    data class OnCourseClick(val course: Course): FavoriteAction
}
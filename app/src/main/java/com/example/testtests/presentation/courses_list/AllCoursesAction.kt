package com.example.testtests.presentation.courses_list

import com.example.testtests.domain.models.Course

sealed interface AllCoursesAction {
    data class OnSearchQueryChange(val query: String): AllCoursesAction
    data class OnCourseClick(val course: Course): AllCoursesAction
    data class OnFavoriteClick(val course: Course): AllCoursesAction
    data object OnSortClick: AllCoursesAction
    data object OnResetClick: AllCoursesAction
}
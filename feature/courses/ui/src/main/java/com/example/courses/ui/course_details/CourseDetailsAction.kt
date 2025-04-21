package com.example.courses.ui.course_details

import com.example.courses.domain.models.Course

sealed interface CourseDetailsAction {
    data object OnNavigationBack: CourseDetailsAction
    data object OnAddFavoriteTvShow: CourseDetailsAction
    data class FetchCourse(val id: Int): CourseDetailsAction
}
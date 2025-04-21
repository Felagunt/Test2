package com.example.courses.ui.course_details

sealed interface CourseDetailsAction {
    data object OnNavigationBack: CourseDetailsAction
    data object OnAddFavorite: CourseDetailsAction
    data class FetchCourse(val id: Int): CourseDetailsAction
}
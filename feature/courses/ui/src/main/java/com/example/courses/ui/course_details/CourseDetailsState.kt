package com.example.courses.ui.course_details

import com.example.courses.domain.models.Course

data class CourseDetailsState(
    val course: Course? = null,
    val isLoading: Boolean = false,
    val errorMsg: String? = null
)
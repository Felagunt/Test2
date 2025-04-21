package com.example.courses.ui.courses_list

import com.example.courses.domain.models.Course

data class AllCoursesState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val searchQuery: String? = null,
    val courseList: List<Course>? = emptyList()
)

package com.example.courses.ui.favorite

import com.example.courses.domain.models.Course

data class FavoriteState(
    val favoriteList: List<Course>? = emptyList(),
    val isLoading: Boolean = false
)

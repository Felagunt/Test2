package com.example.testtests.presentation.favorite

import com.example.testtests.domain.models.Course

data class FavoriteState(
    val favoriteList: List<Course>? = emptyList(),
    val isLoading: Boolean = false
)

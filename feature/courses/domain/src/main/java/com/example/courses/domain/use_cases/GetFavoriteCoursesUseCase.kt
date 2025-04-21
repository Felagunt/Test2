package com.example.courses.domain.use_cases

import com.example.courses.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFavoriteCoursesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    operator fun invoke() = repository
        .getFavoriteCourses()
        .flowOn(Dispatchers.IO)
}
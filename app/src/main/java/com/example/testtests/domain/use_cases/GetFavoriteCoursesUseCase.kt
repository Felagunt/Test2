package com.example.testtests.domain.use_cases

import com.example.testtests.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteCoursesUseCase @Inject constructor(
    private val repository: CoursesRepository
) {

    operator fun invoke() = repository.getFavoriteCourses()
}
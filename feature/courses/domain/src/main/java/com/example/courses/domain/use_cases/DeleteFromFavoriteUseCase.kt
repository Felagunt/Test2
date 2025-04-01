package com.example.courses.domain.use_cases

import com.example.courses.domain.models.Course
import com.example.courses.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteFromFavoriteUseCase @Inject constructor(
    private val repository: CoursesRepository
) {

    operator fun invoke(course: Course) = flow<Unit> {
        repository.deleteFavoriteCourse(course)
    }.flowOn(Dispatchers.IO)
}
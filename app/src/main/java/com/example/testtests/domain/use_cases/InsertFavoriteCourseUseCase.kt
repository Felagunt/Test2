package com.example.testtests.domain.use_cases

import com.example.testtests.domain.models.Course
import com.example.testtests.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertFavoriteCourseUseCase @Inject constructor(
    private val repository: CoursesRepository
) {

    operator fun invoke(course: Course) = flow<Unit> {
        repository.insertFavoriteCourse(course)
    }.flowOn(Dispatchers.IO)
}
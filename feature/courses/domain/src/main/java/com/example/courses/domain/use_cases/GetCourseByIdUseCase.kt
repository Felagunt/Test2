package com.example.courses.domain.use_cases

import com.example.courses.domain.repository.CoursesRepository
import javax.inject.Inject

class GetCourseByIdUseCase @Inject constructor(
    private val repository: CoursesRepository
) {

    suspend fun invoke(id: Int) = repository.getCourseById(id)
}
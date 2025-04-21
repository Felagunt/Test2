package com.example.courses.domain.use_cases

import com.example.courses.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCourseByIdUseCase @Inject constructor(
    private val repository: CoursesRepository
) {

    suspend fun invoke(id: Int) = withContext(Dispatchers.IO) {
        repository.getCourseById(id)
    }
}
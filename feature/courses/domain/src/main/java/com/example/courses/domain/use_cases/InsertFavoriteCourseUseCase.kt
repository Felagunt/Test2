package com.example.courses.domain.use_cases

import android.util.Log
import com.example.courses.domain.models.Course
import com.example.courses.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertFavoriteCourseUseCase @Inject constructor(
    private val repository: CoursesRepository
) {

    operator fun invoke(course: Course) = flow<Unit> {
        repository.insertFavoriteCourse(course)
        println("insert " + "$course")
    }.flowOn(Dispatchers.IO)
}
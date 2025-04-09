package com.example.courses.domain.use_cases

import android.util.Log
import com.example.courses.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteCoursesUseCase @Inject constructor(
    private val repository: CoursesRepository
) {

    operator fun invoke() = repository.getFavoriteCourses().also {
        println("favorite "+ "$it")
    }
}
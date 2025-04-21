package com.example.courses.domain.use_cases

import android.util.Log
import com.example.courses.domain.repository.CoursesRepository
import com.example.courses.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteCoursesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    operator fun invoke() = repository.getFavoriteCourses().also {
        println("favorite "+ "$it")
    }
}
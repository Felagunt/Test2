package com.example.courses.domain.use_cases

import android.util.Log
import com.example.courses.domain.repository.CoursesRepository
import javax.inject.Inject

class IsCourseFavoriteUseCase @Inject constructor(
    private val repository: CoursesRepository
){
    operator fun invoke(id: Int) = repository.isCourseFavorite(id).also {
        println("isFav " +"${it.toString()}")
    }
}
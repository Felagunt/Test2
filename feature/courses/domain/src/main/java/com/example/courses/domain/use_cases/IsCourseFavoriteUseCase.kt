package com.example.courses.domain.use_cases

import android.util.Log
import com.example.courses.domain.repository.CoursesRepository
import com.example.courses.domain.repository.FavoriteRepository
import javax.inject.Inject

class IsCourseFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
){
    operator fun invoke(id: Int) = repository.isCourseFavorite(id).also {
        println("isFav " +"${it.toString()}")
    }
}
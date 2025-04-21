package com.example.courses.domain.use_cases

import android.util.Log
import com.example.courses.domain.models.Course
import com.example.courses.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteFromFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    suspend operator fun invoke(course: Course) {
        Log.d("deleteFavoriteCourse", "deleting course: $course")
        withContext(Dispatchers.IO) {
            repository.deleteFavoriteCourse(course)
        }
    }
}
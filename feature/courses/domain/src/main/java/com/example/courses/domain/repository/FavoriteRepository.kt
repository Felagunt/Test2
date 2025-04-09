package com.example.courses.domain.repository

import com.example.courses.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {


    fun isCourseFavorite(id: Int): Flow<Boolean>
    fun getFavoriteCourses(): Flow<List<Course>>
    suspend fun deleteFavoriteCourse(course: Course)
    suspend fun insertFavoriteCourse(course: Course)
}
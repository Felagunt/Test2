package com.example.testtests.domain.repository

import com.example.testtests.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    suspend fun getAllCourses(): Result<List<Course>>

    fun getFavoriteCourses(): Flow<List<Course>>
    suspend fun deleteFavoriteCourse(course: Course)
    suspend fun insertFavoriteCourse(course: Course)
}
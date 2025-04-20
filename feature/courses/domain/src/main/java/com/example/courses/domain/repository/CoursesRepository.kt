package com.example.courses.domain.repository

import com.example.common.utils.Either
import com.example.common.utils.Failure
import com.example.courses.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    //suspend fun getAllCourses(): Result<List<Course>>

    suspend fun getAllCourses(): Either<Failure, List<Course>>

    suspend fun getCourseById(id: Int): Either<Failure, Course>
}
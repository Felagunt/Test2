package com.example.courses.data.repository

import com.example.common.utils.Either
import com.example.common.utils.Failure
import com.example.courses.data.mappers.CourseDetailsMapper
import com.example.courses.data.mappers.CoursesListMapper
import com.example.courses.data.network.CoursesApi
import com.example.courses.data.network.handler.safeApiCall
import com.example.courses.domain.models.Course
import com.example.courses.domain.repository.CoursesRepository
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val api: CoursesApi,
    private val coursesListMapper: CoursesListMapper,
    private val courseDetailsMapper: CourseDetailsMapper,
) : CoursesRepository {

//    override suspend fun getCourseById(id: Int): Either<Failure,Course> =
//        safeApiCall(
//            apiCall = {
//                api.getCourses().takeIf {response ->
//                    response.body().courses.firstOrNull {dto ->
//                        dto.id == id
//                    }
//                }
//            },
//            mapper = {
//                coursesListMapper.map(it.courses)
//            }
//        )


    override suspend fun getAllCourses(): Either<Failure, List<Course>> =
        safeApiCall(
            apiCall = {
                api.getCourses()
            },
            mapper = {
                coursesListMapper.map(it.courses)
            }
        )

    override suspend fun getCourseById(id: Int): Either<Failure, Course> =
        safeApiCall(
            apiCall = {
                api.getCourses()
            },
            mapper = { response ->
                val courseDto = response.courses.firstOrNull { it.id == id }
                courseDetailsMapper.map(listOf(courseDto!!))//TODO
            }
        )
}
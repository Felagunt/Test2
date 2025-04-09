package com.example.courses.data.repository

import com.example.common.utils.Either
import com.example.common.utils.Failure
import com.example.courses.data.local.FavoriteDao
import com.example.courses.data.mappers.CoursesListMapper
import com.example.courses.data.mappers.toCourse
import com.example.courses.data.mappers.toEntity
import com.example.courses.data.network.CoursesApi
import com.example.courses.data.network.handler.safeApiCall
import com.example.courses.domain.models.Course
import com.example.courses.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val api: CoursesApi,
    private val dao: FavoriteDao,
    private val coursesListMapper: CoursesListMapper
) : CoursesRepository {

//    override suspend fun getAllCourses(): Result<List<Course>> {
//        return try {
//            val response = api.getCourses()
//            if(response.isSuccessful) {
//                response.body()?.courses?.let {list ->
//                    Result.success(
//                        list
//                            .map {
//                                it.toCourse()
//                            }
//                    )
//                } ?: run { Result.failure(Exception("Error")) }
//            } else {
//                Result.failure(Exception("Smth wrong:"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

    override suspend fun getAllCourses(): Either<Failure, List<Course>> =
        safeApiCall(
            apiCall = {
                api.getCourses()
            },
            mapper = {
                coursesListMapper.map(it.courses)
            }
        )


    override fun getFavoriteCourses(): Flow<List<Course>> {
        return dao.getFavoriteCourses()
            .map { list ->
                list.map {
                    it.toCourse()
                }
            }
    }

    override fun isCourseFavorite(id: Int): Flow<Boolean> {
        return dao.getFavoriteCourses()
            .map { entity ->
                entity.any{ it.id == id}
            }
    }

    override suspend fun deleteFavoriteCourse(course: Course) {
        dao.deleteCourse(course.toEntity())
    }

    override suspend fun insertFavoriteCourse(course: Course) {
        dao.insertCourse(course.toEntity())
    }
}
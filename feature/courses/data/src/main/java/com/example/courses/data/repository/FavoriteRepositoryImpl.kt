package com.example.courses.data.repository

import com.example.courses.data.local.FavoriteDao
import com.example.courses.data.mappers.toCourse
import com.example.courses.data.mappers.toEntity
import com.example.courses.domain.models.Course
import com.example.courses.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao
) : FavoriteRepository {

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
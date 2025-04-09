package com.example.courses.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.courses.data.local.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Upsert
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(courseEntity: CourseEntity)

    @Delete
    suspend fun deleteCourse(courseEntity: CourseEntity)

    @Query("SELECT * FROM courseentity")
    fun getFavoriteCourses(): Flow<List<CourseEntity>>
}
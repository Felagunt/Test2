package com.example.testtests.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.courses.data.local.FavoriteDao
import com.example.courses.data.local.entity.CourseEntity


@Database(
    version = 1,
    entities = [CourseEntity::class],
    exportSchema = false
)
abstract class FavoriteDataBase: RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        fun getInstance(context: Context) = Room
            .databaseBuilder(context, FavoriteDataBase::class.java,"favorite.db")
            .build()
    }
}
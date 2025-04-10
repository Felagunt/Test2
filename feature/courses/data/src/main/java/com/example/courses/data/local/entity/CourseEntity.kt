package com.example.courses.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val hasLike: Boolean,
    val price: String,
    val publishDate: String,
    val rate: String,
    val startDate: String,
    val text: String,
    val title: String
)
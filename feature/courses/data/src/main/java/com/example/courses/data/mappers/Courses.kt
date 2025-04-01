package com.example.courses.data.mappers

import com.example.courses.data.dto.CourseDto
import com.example.courses.data.local.entity.CourseEntity
import com.example.courses.domain.models.Course

fun CourseDto.toCourse(): Course {
    return Course(
        hasLike = hasLike,
        id = id,
        price = price,
        publishDate = publishDate,
        rate = rate,
        startDate = startDate,
        text = text,
        title = title
    )
}

fun Course.toEntity(): CourseEntity {
    return CourseEntity(
        hasLike = hasLike,
        id = id,
        price = price,
        publishDate = publishDate,
        rate = rate,
        startDate = startDate,
        text = text,
        title = title
    )
}

fun CourseEntity.toCourse(): Course {
    return Course(
        hasLike = hasLike,
        id = id,
        price = price,
        publishDate = publishDate,
        rate = rate,
        startDate = startDate,
        text = text,
        title = title
    )
}
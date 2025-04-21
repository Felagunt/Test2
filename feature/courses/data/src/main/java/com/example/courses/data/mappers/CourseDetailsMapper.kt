package com.example.courses.data.mappers

import com.example.common.data.mapper.ResultMapper
import com.example.courses.data.dto.CourseDto
import com.example.courses.domain.models.Course
import javax.inject.Inject

class CourseDetailsMapper @Inject constructor() :
    ResultMapper<List<CourseDto>, Course> {
    override fun map(input: List<CourseDto>): Course =
        input.firstOrNull()?.toCourse()
            ?: throw IllegalArgumentException("Course not found")
}
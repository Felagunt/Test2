package com.example.courses.data.network

import com.example.courses.data.dto.ResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface CoursesApi {

    @GET("courses")
    suspend fun getCourses(): Response<ResponseDto>
}
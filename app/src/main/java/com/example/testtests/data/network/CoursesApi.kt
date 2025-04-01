package com.example.testtests.data.network

import com.example.testtests.data.dto.ResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface CoursesApi {

    @GET("courses")
    suspend fun getCourses(): Response<ResponseDto>
}
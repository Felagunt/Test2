package com.example.courses.domain.use_cases

import android.util.Log
import com.example.common.utils.Resource
import com.example.courses.domain.models.Course
import com.example.courses.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllCoursesUseCase @Inject constructor(
    private val repository: CoursesRepository
) {

    suspend fun invoke() = repository.getAllCourses()

    //    operator fun invoke(): Flow<Resource<List<Course>>> = flow {
//        emit(Resource.Loading())
//        val response = repository.getAllCourses()
//        if (response.isSuccess) {
//            emit(Resource.Success(data = response.getOrThrow()))
//        } else {
//            emit(Resource.Error(message = response.exceptionOrNull()?.localizedMessage.toString()))
//        }
//    }.catch  {
//        emit(Resource.Error(it.message.toString()))
//    }.flowOn(Dispatchers.IO)
}


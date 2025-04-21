package com.example.courses.domain.use_cases

import com.example.common.utils.Either
import com.example.common.utils.Failure
import com.example.courses.domain.models.Course
import com.example.courses.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllCoursesUseCase @Inject constructor(
    private val repository: CoursesRepository
) {

    suspend fun invoke() = repository.getAllCourses()

}
//class GetAllCoursesUseCase @Inject constructor(
//    private val repository: CoursesRepository
//) {
//    suspend fun invoke(): Either<Failure, List<Course>> = withContext(Dispatchers.IO) {
//        repository.getAllCourses()
//    }
//}

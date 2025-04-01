package com.example.courses.ui.courses_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.Resource
import com.example.courses.domain.models.Course
import com.example.courses.domain.use_cases.DeleteFromFavoriteUseCase
import com.example.courses.domain.use_cases.GetAllCoursesUseCase
import com.example.courses.domain.use_cases.InsertFavoriteCourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCoursesViewModel @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
    private val insertFavoriteCourseUseCase: InsertFavoriteCourseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AllCoursesState())
    val state = _state
        .onStart {
            getCourses()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private var courseList = mutableListOf<Course>()

    fun onAction(action: AllCoursesAction) {
        when (action) {
            is AllCoursesAction.OnCourseClick -> {

            }

            is AllCoursesAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (action.course.hasLike) {
                        deleteFromFavoriteUseCase.invoke(action.course)
                    } else {
                        insertFavoriteCourseUseCase.invoke(action.course)
                    }
                }
            }

            is AllCoursesAction.OnSearchQueryChange -> {

            }

            is AllCoursesAction.OnSortClick -> {
                sortByDate()
            }

            is AllCoursesAction.OnResetClick -> {
                resetSort()
            }
        }
    }

//    private fun onFavorite(element: Course) = viewModelScope.launch {
//        val elem = _state.value.courseList?.find {course ->
//            course.id == element.id
//        }
//        if (element.hasLike) {
//            _state.update {
//                it.courseList?.find { course ->
//                    course.id == element.id
//                    course.copy(
//                        hasLike = false
//                    )
//                }
//
//            }
//            course.copy(
//                hasLike = false
//            )
//        } else {
//            course.copy(
//                hasLike = true
//            )
//        }
//    }

    private fun resetSort() {
        _state.update {
            it.copy(
                courseList = courseList
            )
        }
    }

    private fun sortByDate() = _state
        .update {
            it.copy(
                courseList = courseList
                    .sortedBy { it.publishDate }
            )
        }

    private fun getCourses() {
        getAllCoursesUseCase
            .invoke()
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                courseList = emptyList(),
                                errorMsg = result.message,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                errorMsg = null,
                                courseList = emptyList()
                            )
                        }
                    }

                    is Resource.Success -> {
                        courseList =
                            result.data?.toMutableList() ?: emptyList<Course>().toMutableList()
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMsg = null,
                                courseList = result.data
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }
}

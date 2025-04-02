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

                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMsg = null,
                                courseList = result.data
                            )
                        }
                        syncFavorite(result.data)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun syncFavorite(list: List<Course>?) =
        viewModelScope.launch {
            list?.onEach { curse ->
                if (curse.hasLike) {
                    insertFavoriteCourseUseCase.invoke(course = curse)
                } else {
                    deleteFromFavoriteUseCase.invoke(course = curse)
                }
            }
        }

}

package com.example.courses.ui.course_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.fold
import com.example.common.utils.getErrorMessage
import com.example.courses.domain.models.Course
import com.example.courses.domain.use_cases.DeleteFromFavoriteUseCase
import com.example.courses.domain.use_cases.GetAllCoursesUseCase
import com.example.courses.domain.use_cases.GetFavoriteCoursesUseCase
import com.example.courses.domain.use_cases.InsertFavoriteCourseUseCase
import com.example.courses.domain.use_cases.IsCourseFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailsViewModel @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
    private val insertFavoriteCourseUseCase: InsertFavoriteCourseUseCase,
    private val isCourseFavoriteUseCase: IsCourseFavoriteUseCase,
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CourseDetailsState())
    val state = _state
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: CourseDetailsAction) {
        when (action) {
            is CourseDetailsAction.OnAddFavoriteTvShow -> {
                viewModelScope.launch {
                    if (_state.value.course?.hasLike == true) {
                        deleteFromFavoriteUseCase.invoke(_state.value.course!!)
                    } else {
                        _state.value.course?.let { course ->
                            insertFavoriteCourseUseCase.invoke(course = course)
                        }
                    }
                }
            }

            is CourseDetailsAction.OnNavigationBack -> {

            }

            is CourseDetailsAction.FetchCourse -> {
                fetchCourse(action.id)
            }
        }
    }

    private fun fetchCourse(id: Int) {
        viewModelScope.launch {
            getAllCoursesUseCase.invoke().fold(
                { failure ->
                    _state.update {
                        it.copy(
                            error = failure.getErrorMessage().toString()
                        )
                    }
                },
                { list ->

                    val course = list.first {
                        it.id == id
                    }
                    _state.update {
                        it.copy(
                            course = course
                        )
                    }
                }
            )
        }
    }


    private fun observeFavoriteStatus() {
        isCourseFavoriteUseCase.invoke(_state.value.course!!.id)
            .onEach { isFavorite ->
                _state.update {
                    it.copy(
                        course = isFavorite
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}

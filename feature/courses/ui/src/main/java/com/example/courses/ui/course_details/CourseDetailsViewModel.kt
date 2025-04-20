package com.example.courses.ui.course_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.fold
import com.example.common.utils.getErrorMessage
import com.example.courses.domain.use_cases.DeleteFromFavoriteUseCase
import com.example.courses.domain.use_cases.GetAllCoursesUseCase
import com.example.courses.domain.use_cases.GetCourseByIdUseCase
import com.example.courses.domain.use_cases.GetFavoriteCoursesUseCase
import com.example.courses.domain.use_cases.InsertFavoriteCourseUseCase
import com.example.courses.domain.use_cases.IsCourseFavoriteUseCase
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
class CourseDetailsViewModel @Inject constructor(
    private val getCourseByIdUseCase: GetCourseByIdUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
    private val insertFavoriteCourseUseCase: InsertFavoriteCourseUseCase,
    private val isCourseFavoriteUseCase: IsCourseFavoriteUseCase,
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CourseDetailsState())
    val state = _state
        .onStart {
            observeFavoriteStatus()
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
            getCourseByIdUseCase.invoke(id).fold(
                { failure ->
                    _state.update {
                        it.copy(
                            errorMsg = failure.getErrorMessage().toString()
                        )
                    }
                },
                { course ->
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
                val curCourse = _state.value.course
                curCourse?.hasLike = isFavorite
                _state.update {curState ->
                    curState.copy(
                        course = curCourse//TODO
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}

package com.example.courses.ui.courses_list

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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCoursesViewModel @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
    private val insertFavoriteCourseUseCase: InsertFavoriteCourseUseCase,
    private val isCourseFavoriteUseCase: IsCourseFavoriteUseCase,
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AllCoursesState())
    val state = _state
        .onStart {
            //getCourses()
            loadCourses()
            //syncCourses()
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
                    val isCurrentlyFavorite =
                        isCourseFavoriteUseCase.invoke(action.course.id).first()
                    if (isCurrentlyFavorite) {
                        deleteFromFavoriteUseCase.invoke(action.course)
                    } else {
                        insertFavoriteCourseUseCase.invoke(action.course)
                    }
                    _state.update { currentState ->
                        val updatedCourseList = currentState.courseList?.map {
                            if (it.id == action.course.id) {
                                it.copy(hasLike = !isCurrentlyFavorite)
                            } else {
                                it
                            }
                        }
                        currentState.copy(courseList = updatedCourseList)
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

            else -> Unit
        }
    }

    private fun updateCourseListFavorite(course: Course) {
        //val isCurrentlyFavorite = repository.isCourseFavorite(course.id).first()
        _state.update { currentState ->
            val updatedCourseList = currentState.courseList?.map {
                if (it.id == course.id) {
                    it.copy(hasLike = !course.hasLike)
                } else {
                    it
                }
            }
            currentState.copy(courseList = updatedCourseList)
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


    private fun loadCourses() {
        viewModelScope.launch {
            getAllCoursesUseCase.invoke().fold(
                { failure ->
                    _state.update {
                        it.copy(
                            errorMsg = failure.getErrorMessage().toString()
                        )
                    }
                },
                { list ->
                    courseList = list.toMutableList()
                    val favoriteIds = getFavoriteCoursesUseCase.invoke().first().map { it.id }
                    val updatedList = list.map { course ->
                        course.copy(
                            hasLike = favoriteIds.contains(course.id)
                        )
                    }
                    _state.update {
                        it.copy(
                            courseList = updatedList
                        )
                    }
                }
            )
        }
    }
}

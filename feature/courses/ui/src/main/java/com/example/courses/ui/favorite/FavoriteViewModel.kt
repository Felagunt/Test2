package com.example.courses.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.domain.models.Course
import com.example.courses.domain.use_cases.DeleteFromFavoriteUseCase
import com.example.courses.domain.use_cases.GetFavoriteCoursesUseCase
import com.example.courses.domain.use_cases.InsertFavoriteCourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
    private val insertFavoriteCourseUseCase: InsertFavoriteCourseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state = _state
        .onStart {
            getFavoriteCourses()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: FavoriteAction) {
        when(action){
            is FavoriteAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if(action.course.hasLike) {
                        deleteFromFavoriteUseCase.invoke(action.course)
                    } else {
                        insertFavoriteCourseUseCase.invoke(action.course)
                    }
                    _state.update { currentState ->
                        val updatedCourseList = currentState.favoriteList?.map {
                            if (it.id == action.course.id) {
                                it.copy(hasLike = !action.course.hasLike)
                            } else {
                                it
                            }
                        }
                        currentState.copy(favoriteList = updatedCourseList)
                    }
                }
            }
            is FavoriteAction.OnCourseClick -> {

            }
            else -> Unit
        }
    }

    private fun updateCourseListFavorite(course: Course) {
        //val isCurrentlyFavorite = repository.isCourseFavorite(course.id).first()

    }

    private fun getFavoriteCourses() {
        getFavoriteCoursesUseCase
            .invoke()
            .onEach { list ->
                _state.update {
                    it.copy(
                        favoriteList = list
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun deleteFavoriteCourse(course: Course) = deleteFromFavoriteUseCase
        .invoke(course)
        .launchIn(viewModelScope)


}
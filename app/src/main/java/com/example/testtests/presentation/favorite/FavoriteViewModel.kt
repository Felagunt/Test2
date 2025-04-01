package com.example.testtests.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtests.domain.models.Course
import com.example.testtests.domain.use_cases.DeleteFromFavoriteUseCase
import com.example.testtests.domain.use_cases.GetFavoriteCoursesUseCase
import com.example.testtests.domain.use_cases.InsertFavoriteCourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
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
                }
            }
            is FavoriteAction.OnCourseClick -> {

            }
        }
    }


    private fun getFavoriteCourses() = viewModelScope.launch {
        getFavoriteCoursesUseCase
            .invoke()
            .collectLatest { list ->
                _state.update {
                    it.copy(
                        favoriteList = list
                    )
                }
            }
    }

    private fun deleteFavoriteCourse(course: Course) = deleteFromFavoriteUseCase
        .invoke(course)
        .launchIn(viewModelScope)


}
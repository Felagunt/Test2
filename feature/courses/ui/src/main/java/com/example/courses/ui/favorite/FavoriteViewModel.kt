package com.example.courses.ui.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.domain.models.Course
import com.example.courses.domain.use_cases.DeleteFromFavoriteUseCase
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
class FavoriteViewModel @Inject constructor(
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
    private val insertFavoriteCourseUseCase: InsertFavoriteCourseUseCase,
    private val isCourseFavoriteUseCase: IsCourseFavoriteUseCase
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
                    val isCurrentlyFavorite =
                        isCourseFavoriteUseCase.invoke(action.course.id).first()
                    Log.d("FavoriteClick", "Is course favorite: $isCurrentlyFavorite")
                    if(isCurrentlyFavorite) {
                        Log.d("FavoriteClick", "Deleting course from favorites")
                        deleteFromFavoriteUseCase.invoke(action.course)

                    } else {
                        Log.d("FavoriteClick", "Inserting course into favorites")
                        insertFavoriteCourseUseCase.invoke(action.course)
                    }
                    _state.update { currentState ->
                        val updatedCourseList = currentState.favoriteList?.map {
                            if (it.id == action.course.id) {
                                it.copy(hasLike = !isCurrentlyFavorite)
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

//    private fun observeFavoriteStatus() {
//        isCourseFavoriteUseCase.invoke()
//            .onEach { isFavorite->
//                _state.update {
//                    it.copy(
//                        favoriteList =
//                    )
//                }
//            }
//    }

    private fun getFavoriteCourses() {
        getFavoriteCoursesUseCase
            .invoke()
            .onEach { list ->
                _state.update {
                    it.copy(
                        favoriteList = list.map {course ->
                            course.copy(hasLike = true)//is it?
                        }
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun deleteFavoriteCourse(course: Course) {
        viewModelScope.launch {
            deleteFromFavoriteUseCase.invoke(course)
        }
    }

}
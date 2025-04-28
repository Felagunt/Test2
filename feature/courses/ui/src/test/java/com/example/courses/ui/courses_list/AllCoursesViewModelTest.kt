package com.example.courses.ui.courses_list

import com.example.common.utils.Either
import com.example.common.utils.Failure
import com.example.courses.domain.models.Course
import com.example.courses.domain.use_cases.DeleteFromFavoriteUseCase
import com.example.courses.domain.use_cases.GetAllCoursesUseCase
import com.example.courses.domain.use_cases.GetFavoriteCoursesUseCase
import com.example.courses.domain.use_cases.InsertFavoriteCourseUseCase
import com.example.courses.domain.use_cases.IsCourseFavoriteUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

@HiltAndroidTest
class AllCoursesViewModelTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @MockK
    lateinit var getAllCoursesUseCase: GetAllCoursesUseCase
    @MockK
    lateinit var deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase
    @MockK
    lateinit var insertFavoriteCourseUseCase: InsertFavoriteCourseUseCase
    @MockK
    lateinit var isCourseFavoriteUseCase: IsCourseFavoriteUseCase
    @MockK
    lateinit var getFavoriteCourseUseCase: GetFavoriteCoursesUseCase

    lateinit var viewModel: AllCoursesViewModel

    @Before
    fun setUp() {
        hiltRule.inject()

        viewModel = AllCoursesViewModel(
            getAllCoursesUseCase,
            deleteFromFavoriteUseCase,
            insertFavoriteCourseUseCase,
            isCourseFavoriteUseCase,
            getFavoriteCourseUseCase
        )
    }


    @Test
    fun `test loadCourses success`() = runTest {
        val courses = listOf(
            Course(
                id = 1,
                title = "Course 1",
                publishDate = "2025-01-01",
                hasLike = false,
                price = "99.99",
                rate = "4.5",
                startDate = "2023-11-01",
                text = "This is a fake course.",
            ),
            Course(
                id = 2,
                title = "Course 2",
                publishDate = "2025-02-01",
                hasLike = false,
                price = "99.99",
                rate = "4.5",
                startDate = "2023-11-01",
                text = "This is a fake course.",
            ),
            Course(
                hasLike = true,
                id = 123,
                price = "99.99",
                publishDate = "2023-10-01",
                rate = "4.5",
                startDate = "2023-11-01",
                text = "This is a fake course.",
                title = "Fake Course Title"
            )
        )

        coEvery { getAllCoursesUseCase.invoke() } returns Either.Right(courses)
        coEvery { getFavoriteCourseUseCase.invoke() } returns flowOf(listOf())

        viewModel.loadCourses()

        val state = viewModel.state.value
        assertTrue(state.courseList?.size == 3)

    }

    @Test
    fun `test failure`() = runTest {
        val error = Failure.ServerError(500, "server")
        coEvery { getAllCoursesUseCase.invoke() } returns Either.Left(error)

        viewModel.loadCourses()
        val state = viewModel.state.value
        assertEquals(state.errorMsg, error.message)
    }
}
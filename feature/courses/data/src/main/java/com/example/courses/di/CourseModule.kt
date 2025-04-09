package com.example.courses.di

import android.content.Context
import com.example.common.utils.BASE_URL
import com.example.courses.data.local.FavoriteDao
import com.example.courses.data.mappers.CoursesListMapper
import com.example.courses.data.network.CoursesApi
import com.example.courses.data.network.LocalJsonInterceptor
import com.example.courses.data.repository.CoursesRepositoryImpl
import com.example.courses.data.repository.FavoriteRepositoryImpl
import com.example.courses.domain.repository.CoursesRepository
import com.example.courses.domain.repository.FavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CourseModule {

    @Provides
    @Singleton
    fun provideCoursesApi(@ApplicationContext context: Context): CoursesApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(LocalJsonInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)//fake api
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoursesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoursesRepository(
        api: CoursesApi,
        dao: FavoriteDao,
        mapper: CoursesListMapper
    ): CoursesRepository {
        return CoursesRepositoryImpl(
            api = api,
            coursesListMapper = mapper
        )
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        dao: FavoriteDao,
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(
            dao = dao
        )
    }
}
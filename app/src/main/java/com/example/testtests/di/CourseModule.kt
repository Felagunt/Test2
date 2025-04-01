package com.example.testtests.di

import android.content.Context
import androidx.room.Room
import com.example.testtests.core.data.LocalJsonInterceptor
import com.example.testtests.data.local.FavoriteDao
import com.example.testtests.data.local.FavoriteDataBase
import com.example.testtests.data.network.CoursesApi
import com.example.testtests.data.repository.CoursesRepositoryImpl
import com.example.testtests.domain.repository.CoursesRepository
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
    fun provideVacanciesApi(@ApplicationContext context: Context): CoursesApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(LocalJsonInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl("https://mock.api/")//fake api
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoursesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFavoriteDatabase(@ApplicationContext context: Context)= FavoriteDataBase
        .getInstance(context)

    @Provides
    fun provideFavoriteDao(dataBase: FavoriteDataBase): FavoriteDao {
        return dataBase.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideVacanciesRepository(api: CoursesApi, dao: FavoriteDao): CoursesRepository {
        return CoursesRepositoryImpl(
            api = api,
            dao = dao
            )
    }
}
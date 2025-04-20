package com.example.testtests.di

import android.content.Context
import com.example.common.data.AuthHandler
import com.example.common.data.DataStoreManager
import com.example.courses.data.local.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule{


    @Provides
    @Singleton
    fun provideFavoriteDatabase(@ApplicationContext context: Context)= com.example.testtests.data.local.FavoriteDataBase
        .getInstance(context)

    @Provides
    fun provideFavoriteDao(dataBase: com.example.testtests.data.local.FavoriteDataBase): FavoriteDao {
        return dataBase.favoriteDao()
    }


    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthHandler(dataStoreManager: DataStoreManager): AuthHandler {
        return AuthHandler(dataStoreManager)
    }
}

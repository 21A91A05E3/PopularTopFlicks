package com.example.moviedbapplication.model.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieHiltModule {

    @Provides
    @Singleton
    fun provideApiService() : ApiService = RetrofitImplementation().retroObj()
}
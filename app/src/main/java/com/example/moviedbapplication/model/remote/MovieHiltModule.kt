package com.example.moviedbapplication.model.remote

import com.example.moviedbapplication.model.repository.MovieRepository
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

    @Provides
    @Singleton
    fun provideMovieRepository(apiService : ApiService) : MovieRepository = MovieRepository(apiService)

}
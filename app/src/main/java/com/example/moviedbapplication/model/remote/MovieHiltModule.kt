package com.example.moviedbapplication.model.remote

import android.content.Context
import com.example.moviedbapplication.model.local.MovieDao
import com.example.moviedbapplication.model.repository.MovieRepository
import com.example.moviedbapplication.model.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideMovieRepository(apiService : ApiService , movieDao: MovieDao) : MovieRepository = MovieRepository(apiService,movieDao)

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context) : MovieDatabase{
        return MovieDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase) : MovieDao{
        return movieDatabase.MovieDao()
    }

}
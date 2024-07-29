package com.example.moviedbapplication.model.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("popular")
    suspend fun getPopularMovieList(@Query("api_key") apiKey: String, @Query("page") page: Int): Response<MovieResponse>

    @GET("top_rated")
    suspend fun getTopRatedMovieList(@Query("api_key") apiKey: String, @Query("page") page: Int): Response<MovieResponse>
}

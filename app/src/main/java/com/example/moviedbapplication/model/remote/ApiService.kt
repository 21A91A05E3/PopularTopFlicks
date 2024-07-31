package com.example.moviedbapplication.model.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{category}")
    suspend fun getMovieList(@Path ("category") category: String ,@Query("api_key") apiKey : String, @Query("page") page : Int) : Response<MovieResponse>

    @GET("{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Response<MovieData>

    @GET("{movie_id}/videos")
    suspend fun getMovieTrailers(@Path("movie_id") movieId : Int, @Query("api_key") apiKey: String) : Response<TrailerResponse>

    @GET("{movie_id}/reviews")
    suspend fun getMovieReviews(@Path("movie_id") movieId : Int, @Query("api_key") apiKey: String) : Response<ReviewResponse>
}

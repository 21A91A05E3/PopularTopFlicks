package com.example.moviedbapplication.model.repository

import android.util.Log
import com.example.moviedbapplication.model.local.MovieDao
import com.example.moviedbapplication.model.remote.ApiService
import com.example.moviedbapplication.model.remote.Constants
import com.example.moviedbapplication.model.remote.MovieData
import com.example.moviedbapplication.model.remote.MovieResponse
import com.example.moviedbapplication.model.remote.Resource
import com.example.moviedbapplication.model.remote.ReviewResult
import com.example.moviedbapplication.model.remote.TrailerResult
import com.example.moviedbapplication.model.local.MovieLocalData
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService , private val movieDao: MovieDao) {
    suspend fun fetchMovies(category: String, page: Int): Resource<MovieResponse> {
        return try {
            val response = apiService.getMovieList(category = category, apiKey = Constants.API_KEY, page = page)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("API Error: ${response.code()} ${response.message()}")
            }
        } catch (e: HttpException) {
            Resource.Error("Network Error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("IO error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Failed to fetch data: ${e.message}")
        }
    }
    suspend fun fetchMovieDetails(movieId: Int): Resource<MovieData> {
        return try {
            Log.d("Details", "Received Movie Id in Repo: $movieId")
            val response = apiService.getMovieDetails(movieId = movieId, apiKey = Constants.API_KEY)
            if (response.isSuccessful) {
                Log.d("Details", "Success")
                Resource.Success(response.body())
            } else {
                Resource.Error("API Error: ${response.code()} ${response.message()}")
            }
        } catch (e: HttpException) {
            Resource.Error("Network Error: ${e.message()}")
        } catch (e: Exception) {
            Resource.Error("Failed to fetch data: ${e.message}")
        }
    }

    suspend fun fetchMovieTrailers(movieId: Int): Resource<List<TrailerResult?>> {
        return try {
            val response = apiService.getMovieTrailers(movieId = movieId, apiKey = Constants.API_KEY)
            if (response.isSuccessful) {
                Resource.Success(response.body()?.results ?: emptyList())
            } else {
                Resource.Error("API Error: ${response.code()} ${response.message()}")
            }
        } catch (e: HttpException) {
            Resource.Error("Network Error: ${e.message()}")
        } catch (e: Exception) {
            Resource.Error("Failed to fetch data: ${e.message}")
        }
    }

    suspend fun fetchMovieReviews(movieId: Int) : Resource<List<ReviewResult?>>{
        return try{
            val response = apiService.getMovieReviews(movieId = movieId , apiKey = Constants.API_KEY)
            if(response.isSuccessful){
                Resource.Success(response.body()?.results?: emptyList())
            }
            else {
                Resource.Error("API Error: ${response.code()} ${response.message()}")
            }
        } catch (e: HttpException) {
            Resource.Error("Network Error: ${e.message()}")
        } catch (e: Exception) {
            Resource.Error("Failed to fetch data: ${e.message}")
        }
    }
    suspend fun addMovieToFavourites(movie : MovieLocalData){
        movieDao.insertMovie(movie)
    }
    fun isFavourite(movieId: Int) = movieDao.getIsFavourite(movieId)
    suspend fun toggleIsFav(movieId: Int) = movieDao.toggleFav(movieId)
}


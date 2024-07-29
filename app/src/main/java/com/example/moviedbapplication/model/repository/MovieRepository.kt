package com.example.moviedbapplication.model.repository

import com.example.moviedbapplication.model.remote.ApiService
import com.example.moviedbapplication.model.remote.Constants
import com.example.moviedbapplication.model.remote.MovieResponse
import com.example.moviedbapplication.model.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchMovies(category: String, page: Int): Resource<MovieResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getMovieList(category = category, apiKey = Constants.API_KEY ,page = page)
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
    }
}

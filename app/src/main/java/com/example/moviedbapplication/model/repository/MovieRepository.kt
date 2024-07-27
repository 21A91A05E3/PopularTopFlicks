package com.example.moviedbapplication.model.repository

import com.example.moviedbapplication.model.remote.ApiService
import com.example.moviedbapplication.model.remote.MovieData
import com.example.moviedbapplication.model.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchMovies(category: String, page: Int): Resource<List<MovieData>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = when (category) {
                    "popular" -> apiService.getPopularMovieList(apiKey = "78485b82b46c3312b295e2d81f160230", page = page)
                    "top_rated" -> apiService.getTopRatedMovieList(apiKey = "78485b82b46c3312b295e2d81f160230", page = page)
                    else -> throw IllegalArgumentException("Unknown category")
                }
                Resource.Success(response.results)
            } catch (e: HttpException) {
                Resource.Error("Network Error")
            } catch (e: IOException) {
                Resource.Error("IO Error")
            } catch (e: IllegalArgumentException) {
                Resource.Error("Invalid Category")
            } catch (e: Exception) {
                Resource.Error("Failed to fetch data")
            }
        }
    }
}

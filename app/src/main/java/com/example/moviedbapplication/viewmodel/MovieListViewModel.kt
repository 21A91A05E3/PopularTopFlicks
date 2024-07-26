package com.example.moviedbapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapplication.model.remote.ApiService
import com.example.moviedbapplication.model.remote.MovieData
import com.example.moviedbapplication.model.remote.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _movies = MutableLiveData<Resource<List<MovieData>>>()
    val movies: LiveData<Resource<List<MovieData>>> get() = _movies
    fun fetchMovies(category: String) {
        _movies.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val response = when (category) {
                    "popular" -> apiService.getPopularMovieList(apiKey = "78485b82b46c3312b295e2d81f160230")
                    "top_rated" -> apiService.getTopRatedMovieList(apiKey = "78485b82b46c3312b295e2d81f160230")
                    else -> throw IllegalArgumentException("Unknown category")
                }
                _movies.value = Resource.Success(response.results)
            } catch (e: HttpException) {
                _movies.value = Resource.Error("Network Error")
            } catch (e: IOException) {
                _movies.value = Resource.Error("Error Occurred")
            } catch (e: IllegalArgumentException) {
                _movies.value = Resource.Error("Invalid Category")
            }
        }
    }
}

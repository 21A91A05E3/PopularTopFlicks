package com.example.moviedbapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapplication.model.remote.MovieData
import com.example.moviedbapplication.model.remote.Resource
import com.example.moviedbapplication.model.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<MovieData>>>()
    val movies: LiveData<Resource<List<MovieData>>> get() = _movies

    var currentPage = 1
    var totalPages = 1
    var isLoading = false

    fun fetchMovies(category: String) {
        if (isLoading || currentPage > totalPages) return
        isLoading = true
        _movies.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val response = movieRepository.fetchMovies(category, currentPage)
                Log.d("Check", "API Response: $response")
                when (response) {
                    is Resource.Success -> {
                        val movieResponse = response.data
                        movieResponse?.let {
                            val movieList = it.results?: emptyList()
                            val existingMovies = (_movies.value as? Resource.Success)?.data ?: emptyList()
                            _movies.value = Resource.Success(existingMovies+movieList)
                            totalPages = it.totalPages?:1
                            currentPage++
                        }
                    }
                    else -> {
                        _movies.value = Resource.Error("API Call Failed")
                    }
                }
            } catch (e: Exception) {
                _movies.value = Resource.Error("Failed to fetch data")
            } finally {
                isLoading = false
            }
        }
    }
    fun hasMorePages(): Boolean{
        return currentPage <= totalPages
    }
}

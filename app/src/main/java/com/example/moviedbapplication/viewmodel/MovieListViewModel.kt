package com.example.moviedbapplication.viewmodel

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
class MovieListViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {
    private val _movies = MutableLiveData<Resource<List<MovieData>>>()
    val movies: LiveData<Resource<List<MovieData>>> get() = _movies

    private var currentPage = 1
    var isLoading = false

    fun fetchMovies(category: String) {
        if (isLoading) return
        isLoading = true
        _movies.value = Resource.Loading()
        viewModelScope.launch {
            val result = repository.fetchMovies(category, currentPage)
            _movies.value = result
            if (result is Resource.Success) currentPage++
            isLoading = false
        }
    }
}

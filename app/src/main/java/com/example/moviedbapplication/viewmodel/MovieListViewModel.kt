package com.example.moviedbapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapplication.model.remote.moviedata.MovieData
import com.example.moviedbapplication.model.remote.Resource
import com.example.moviedbapplication.model.repository.MovieRepository
import com.example.moviedbapplication.view.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {
    private val _movies = MutableLiveData<Resource<List<MovieData>>>()
    val movies: LiveData<Resource<List<MovieData>>> get() = _movies
    private var currentPage = 1
    private var totalPages = 1
    private val _selectedCategory = MutableLiveData(MainActivity.POPULAR)
    val selectedCategory: LiveData<String> = _selectedCategory
    init {
        fetchMovies()
    }
    fun setSelectedCategory(category: String) {
        if (category != _selectedCategory.value) _selectedCategory.postValue(category)
    }
    fun fetchMovies(currPage: Int = 1) {
        currentPage = currPage
        if (currentPage == 1) {
            _movies.postValue(Resource.Loading())
        }
        if (currentPage > totalPages) return
        viewModelScope.launch {
            val response = movieRepository.fetchMovies(_selectedCategory.value ?: "popular", currentPage)
            when (response) {
                is Resource.Success -> {
                    val movieResponse = response.data
                    movieResponse?.let {
                        val movieList = it.results ?: emptyList()
                        if (currentPage == 1) {
                            _movies.postValue(Resource.Success(movieList))
                        } else {
                            val existingMovies =
                                (_movies.value as? Resource.Success)?.data ?: emptyList()
                            _movies.postValue(Resource.Success(existingMovies + movieList))
                        }
                        totalPages = it.totalPages ?: 1
                    }
                }
                is Resource.Error -> {
                    if (currentPage == 1) {
                        _movies.value = Resource.Error("API Call Failed")
                    }
                }
                else -> {
                    if (currentPage == 1) {
                        _movies.postValue(Resource.Loading())
                    }
                }
            }
        }
    }
    fun loadMore() {
        if (!hasMorePages()) return
        ++currentPage
        fetchMovies(currentPage)
    }
    private fun hasMorePages(): Boolean {
        return currentPage <= totalPages
    }
}

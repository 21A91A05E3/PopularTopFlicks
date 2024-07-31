package com.example.moviedbapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapplication.model.local.MovieLocalData
import com.example.moviedbapplication.model.remote.MovieData
import com.example.moviedbapplication.model.remote.Resource
import com.example.moviedbapplication.model.remote.ReviewResult
import com.example.moviedbapplication.model.remote.TrailerResult
import com.example.moviedbapplication.model.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _movieDetails = MutableLiveData<MovieData?>()
    val movieDetails: LiveData<MovieData?> get() = _movieDetails

    private val _trailerDetails = MutableLiveData<List<TrailerResult?>>()
    val trailerDetails: MutableLiveData<List<TrailerResult?>> get() = _trailerDetails

    private val _reviewDetails = MutableLiveData<List<ReviewResult?>>()
    val reviewDetails: MutableLiveData<List<ReviewResult?>> get() = _reviewDetails

    private suspend fun loadMovieDetails(movieId: Int) {
        Log.d("Details", "Received Movie Id in View Model: $movieId")
        when (val result = movieRepository.fetchMovieDetails(movieId)) {
            is Resource.Success -> {
                Log.d("Details", "API Call Success in viewModel")
                _movieDetails.value = result.data
            }
            is Resource.Loading -> Log.d("Details", "API Loading")
            else -> Log.d("Details", "API Call Fail")
        }
    }
    private suspend fun loadMovieTrailers(movieId: Int) {
        when (val result = movieRepository.fetchMovieTrailers(movieId)) {
            is Resource.Success -> _trailerDetails.postValue(result.data ?: emptyList<TrailerResult>())
            else -> Log.d("Details", "API Call Fail")
        }
    }
    private suspend fun loadMovieReviews(movieId: Int) {
        when (val result = movieRepository.fetchMovieReviews(movieId)) {
            is Resource.Success -> _reviewDetails.postValue(result.data ?: emptyList<ReviewResult>())
            else -> Log.d("Details", "API Call Fail")
        }
    }
    fun loadData(movieId: Int) = viewModelScope.launch {
        movieRepository.addMovieToFavourites(MovieLocalData(movieId, false))
        async { loadMovieDetails(movieId) }
        async { loadMovieTrailers(movieId) }
        async { loadMovieReviews(movieId) }
    }
    fun checkIfFavourite(movieId: Int) = movieRepository.isFavourite(movieId)
    fun toggleFavourite(id: Int)  = viewModelScope.launch{
       movieRepository.toggleIsFav(id)
    }
}

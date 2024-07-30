package com.example.moviedbapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapplication.model.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.moviedbapplication.model.remote.MovieData
import com.example.moviedbapplication.model.remote.Resource
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movieDetails = MutableLiveData<MovieData?>()
    val movieDetails: LiveData<MovieData?> get() = _movieDetails

    fun loadMovieDetails(movieId: Int) {
        Log.d("Details", "Received Movie Id in View Model: $movieId")

        viewModelScope.launch {
            when (val result = movieRepository.fetchMovieDetails(movieId)) {
                is Resource.Success -> {
                    Log.d("Details", "API Call Success in viewModel")
                    _movieDetails.value = result.data
                }

                is Resource.Loading -> {
                    Log.d("Details", "API Call Fail")

                }

                else -> {
                    Log.d("Details", "API Call Fail")

                    // Handle other states if needed
                }
            }
        }
    }
}

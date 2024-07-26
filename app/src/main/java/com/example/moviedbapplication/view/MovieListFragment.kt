package com.example.moviedbapplication.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapplication.R
import com.example.moviedbapplication.model.remote.Resource
import com.example.moviedbapplication.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieRecyclerView: RecyclerView
    private val movieViewModel: MovieListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        movieRecyclerView = view.findViewById(R.id.movieRecyclerView)
        val progressBar : ProgressBar = view.findViewById(R.id.progressBar)
        movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            movieAdapter = MovieAdapter(emptyList())
        }
        movieViewModel.movies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.d("Check","API Loading")
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("Check","API Calling")
                    progressBar.visibility = View.GONE
                    movieAdapter = MovieAdapter(resource.data ?: emptyList())
                    movieRecyclerView.adapter = movieAdapter
                }
                is Resource.Error -> {
                    Log.d("Check","API Error")
                    progressBar.visibility = View.GONE
                }
            }
        }
        movieViewModel.fetchMovies("popular")
    }
}


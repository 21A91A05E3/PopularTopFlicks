package com.example.moviedbapplication.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapplication.R
import com.example.moviedbapplication.model.remote.Resource
import com.example.moviedbapplication.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieRecyclerView: RecyclerView
    private val movieViewModel: MovieListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieRecyclerView = view.findViewById(R.id.movieRecyclerView)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        movieAdapter = MovieAdapter(onItemClick = { movie ->
            val bundle = Bundle().apply {
                putInt("MovieId", movie)
            }
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                MovieDetailFragment().apply { arguments = bundle }).addToBackStack(null).commit()
        }, loadMore = { movieViewModel.loadMore() })
        movieRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
        }
        movieViewModel.movies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.d("Check", "API Loading")
                    progressBar.isVisible = true
                    movieRecyclerView.isVisible = false
                }
                is Resource.Success -> {
                    Log.d("Check", "API Calling")
                    progressBar.isVisible = false
                    movieRecyclerView.isVisible = true
                    resource.data?.let { newMovies ->
                        Log.d("Check", "Movies: ${newMovies}")
                        movieAdapter.updateMovies(newMovies)
                        movieAdapter.notifyDataSetChanged()
                    }
                }
                else -> progressBar.isVisible = false
            }
        }
        movieViewModel.selectedCategory.observe(viewLifecycleOwner) {
            movieViewModel.fetchMovies()
        }
    }
    fun setCategory(category: String) {
        movieViewModel.setSelectedCategory(category)
    }
}




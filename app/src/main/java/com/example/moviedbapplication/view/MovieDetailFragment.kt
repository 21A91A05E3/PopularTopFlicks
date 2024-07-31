package com.example.moviedbapplication.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapplication.R
import com.example.moviedbapplication.model.remote.Constants
import com.example.moviedbapplication.model.remote.MovieData
import com.example.moviedbapplication.model.remote.ReviewResult
import com.example.moviedbapplication.model.remote.TrailerResult
import com.example.moviedbapplication.viewmodel.MovieDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    private lateinit var movieImage: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieRating: TextView
    private lateinit var movieReleaseDate: TextView
    private lateinit var movieOverview: TextView
    private lateinit var trailerAdapter: TrailerAdapter
    private lateinit var trailerRecyclerView: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var reviewRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getInt("MovieId")
        (activity as? AppCompatActivity)?.apply {
            val toolbar: Toolbar? = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
            toolbar?.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
        movieImage = view.findViewById(R.id.movieImage)
        movieTitle = view.findViewById(R.id.movieTitle)
        movieRating = view.findViewById(R.id.movieRating)
        movieReleaseDate = view.findViewById(R.id.movieReleaseDate)
        movieOverview = view.findViewById(R.id.movieOverview)
        trailerRecyclerView = view.findViewById(R.id.trailerRecyclerView)
        reviewRecyclerView = view.findViewById(R.id.reviewRecyclerView)

        trailerAdapter = TrailerAdapter { trailerKey ->
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TRAILER_BASE_URL + trailerKey)))
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Unable to play this trailer...!", Toast.LENGTH_SHORT).show()
            }
        }
        trailerRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        trailerRecyclerView.adapter = trailerAdapter

        reviewAdapter = ReviewAdapter()
        reviewRecyclerView.layoutManager = LinearLayoutManager(context)
        reviewRecyclerView.adapter = reviewAdapter

        movieId?.let { id ->
            movieDetailViewModel.loadData(id)
        }
        movieDetailViewModel.movieDetails.observe(viewLifecycleOwner, Observer { movie ->
            if (movie != null) {
                updateMovieDetails(movie)
            }
        })
        movieDetailViewModel.trailerDetails.observe(viewLifecycleOwner) { trailer ->
            trailer?.let {
                updateTrailerDetails(trailer)
            }
        }
        movieDetailViewModel.reviewDetails.observe(viewLifecycleOwner) { review ->
            review?.let {
                updateReviewDetails(review)
            }
        }
    }
    private fun updateMovieDetails(movie: MovieData) {
        val posterUrl = movie.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
        if (posterUrl != null) {
            Picasso.get().load(posterUrl).placeholder(R.drawable.placeholder_image).into(movieImage)
        } else {
            movieImage.setImageResource(R.drawable.placeholder_image)
        }
        movieReleaseDate.text = movie.releaseDate
        movieTitle.text = movie.title
        movieOverview.text = movie.overview
    }

    private fun updateTrailerDetails(trailer: List<TrailerResult?>) {
        Log.d("Details", "Got the Trailer in ViewModel")
        trailerAdapter.updateTrailers(trailer)
    }

    private fun updateReviewDetails(review : List<ReviewResult?>){
        reviewAdapter.updateReviews(review)

    }
}

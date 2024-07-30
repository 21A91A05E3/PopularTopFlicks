package com.example.moviedbapplication.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.moviedbapplication.R
import com.example.moviedbapplication.model.remote.MovieData
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

        movieImage = view.findViewById(R.id.movieImage)
        movieTitle = view.findViewById(R.id.movieTitle)
        movieRating = view.findViewById(R.id.movieRating)
        movieReleaseDate = view.findViewById(R.id.movieReleaseDate)
        Log.d("Details", "Received Movie Id in Detail Frag: $movieId")
        movieId.let {

            it?.let { it1 -> movieDetailViewModel.loadMovieDetails(it1) }
        }
        movieDetailViewModel.movieDetails.observe(viewLifecycleOwner, Observer { movie ->
            if (movie != null) {
                updateMovieDetails(movie)
            }
        })
    }
    private fun updateMovieDetails(movie: MovieData) {
        val posterUrl = movie.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
        Log.d("Details","Got the Poster")
        if (posterUrl != null) {
            Picasso.get().load(posterUrl).resize(600, 750).placeholder(R.drawable.placeholder_image)
                .into(movieImage)
        } else {
            movieImage.setImageResource(R.drawable.placeholder_image)
        }
        movieReleaseDate.text = movie.releaseDate
        movieTitle.text = movie.title

    }
}

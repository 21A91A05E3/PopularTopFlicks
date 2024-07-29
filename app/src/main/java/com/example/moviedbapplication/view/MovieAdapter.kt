package com.example.moviedbapplication.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapplication.R
import com.example.moviedbapplication.model.remote.MovieData
import com.squareup.picasso.Picasso

class MovieAdapter(private var movieList: MutableList<MovieData> , private val loadMore: () -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        fun loadMovie(movie: MovieData) {
            val posterUrl = movie.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
            if (posterUrl != null) {
                Picasso.get().load(posterUrl)
                    .resize(600,750)
                    .placeholder(R.drawable.placeholder_image)
                    .into(movieImage)
            } else {
                movieImage.setImageResource(R.drawable.placeholder_image)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.loadMovie(movie)
        if(position == movieList.size-4) loadMore()
    }
    override fun getItemCount(): Int {
        return movieList.size
    }
    fun updateMovies(newMovies: List<MovieData>) {
        val startPosition = movieList.size
        movieList.addAll(newMovies)
        notifyItemRangeInserted(startPosition, newMovies.size)
        Log.d("MovieAdapter", "Movies updated: $newMovies")

        notifyDataSetChanged()
    }
}
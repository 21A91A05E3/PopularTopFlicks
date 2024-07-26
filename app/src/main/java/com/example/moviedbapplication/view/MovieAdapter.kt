package com.example.moviedbapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedbapplication.R
import com.example.moviedbapplication.model.remote.MovieData

class MovieAdapter(private val movieList: List<MovieData>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        fun bind(movie: MovieData) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(movieImage)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }
    override fun getItemCount(): Int {
        return movieList.size
    }
}
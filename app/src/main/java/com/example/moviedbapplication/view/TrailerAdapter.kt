package com.example.moviedbapplication.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapplication.R
import com.example.moviedbapplication.model.remote.TrailerResult
import com.squareup.picasso.Picasso

class TrailerAdapter(
    private val onTrailerClick: (String) -> Unit,
) : RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    private var trailers = listOf<TrailerResult?>()

    class TrailerViewHolder(itemView: View, private val onTrailerClick: (String) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val trailerImage: ImageView = itemView.findViewById(R.id.trailerImage)
        fun bind(trailer: TrailerResult) {
            val thumbnailUrl = "https://img.youtube.com/vi/${trailer.key}/0.jpg"
            Picasso.get().load(thumbnailUrl)
                .into(trailerImage)

            itemView.setOnClickListener {
                if(trailer.key?.isNotBlank() == true) {
                    onTrailerClick(trailer.key)
                }

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_trailer_item, parent, false)
        return TrailerViewHolder(itemView, onTrailerClick)
    }
    override fun getItemCount(): Int {
        return trailers.size
    }
    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int)  {
        trailers.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    fun updateTrailers(newTrailers: List<TrailerResult?>){
        trailers = newTrailers
        notifyDataSetChanged()
    }
}

package com.example.moviedbapplication.view.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapplication.R
import com.example.moviedbapplication.model.remote.reviewdata.ReviewResult

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    private var reviews = listOf<ReviewResult?>()
    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reviewText : TextView = itemView.findViewById(R.id.reviewText)
        fun bind(review  : ReviewResult){
            reviewText.text = review.content
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_review_item ,parent,false)
        return ReviewViewHolder(itemView)
    }
    override fun getItemCount(): Int = reviews.size
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        reviews.getOrNull(position)?.let {
            holder.bind(it)
        }
    }
    fun updateReviews(newReviews: List<ReviewResult?>){
        reviews = newReviews
        notifyDataSetChanged()
    }
}
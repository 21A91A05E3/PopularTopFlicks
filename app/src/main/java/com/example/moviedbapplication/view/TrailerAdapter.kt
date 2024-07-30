//package com.example.moviedbapplication.view
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.moviedbapplication.R
//import com.example.moviedbapplication.model.remote.TrailerResult
//
//class TrailerAdapter(private val trailers: List<TrailerResult>, private val onTrailerClick: (String) -> Unit) :
//    RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {
//
//    class TrailerViewHolder(itemView: View, private val onTrailerClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
//        private val trailerTitle: TextView = itemView.findViewById(R.id.trailerTitle)
//
//        fun bind(trailer: Trailer) {
//            trailerTitle.text = trailer.name
//            itemView.setOnClickListener {
//                trailer.key?.let { onTrailerClick(it) }
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.trailer_list_item, parent, false)
//        return TrailerViewHolder(itemView, onTrailerClick)
//    }
//
//    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
//        val trailer = trailers[position]
//        holder.bind(trailer)
//    }
//
//    override fun getItemCount(): Int = trailers.size
//}

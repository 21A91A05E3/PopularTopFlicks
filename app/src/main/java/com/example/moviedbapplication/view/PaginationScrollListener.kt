package com.example.moviedbapplication.view

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if(!isLoading() && remainingPages()){
            if((visibleItemCount + firstVisibleItemPosition) >= totalItemCount-2) loadMoreItems()
        }
    }
    abstract fun isLoading() : Boolean
    abstract fun remainingPages() : Boolean
    abstract fun loadMoreItems()
}
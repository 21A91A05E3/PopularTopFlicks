package com.example.moviedbapplication.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieLocalData (
    @PrimaryKey
    val id: Int,
    val isFavourite : Boolean = false
)
package com.example.moviedbapplication.model.remote

import com.google.gson.annotations.SerializedName

data class TrailerResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<TrailerResult?>?
)
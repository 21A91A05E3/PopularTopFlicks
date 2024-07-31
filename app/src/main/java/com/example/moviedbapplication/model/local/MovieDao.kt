package com.example.moviedbapplication.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieData: MovieLocalData)

    @Query("UPDATE MovieLocalData SET isFavourite = NOT isFavourite WHERE id = :movieId")
    suspend fun toggleFav(movieId: Int)

    @Query("SELECT isFavourite FROM MovieLocalData where id = :movieId")
    fun getIsFavourite(movieId: Int): LiveData<Boolean>
}
package com.example.wheatherforcast.db.favouritedb

import androidx.room.*
import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(favLocation: FavModel?)

    @Delete
    fun deleteLocation(favLocation: FavModel?)

    @Query("SELECT * FROM favourites")
    fun getAllFavourites(): Flow<List<FavModel>?>?
}
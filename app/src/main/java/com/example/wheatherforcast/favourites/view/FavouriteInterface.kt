package com.example.wheatherforcast.favourites.view

import android.content.Context
import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.flow.Flow

interface FavouriteInterface {

    suspend fun insert(favLocation: FavModel)
    suspend fun delete(favLocation: FavModel)
    suspend fun getAllFavs():Flow<List<FavModel>?>?
}
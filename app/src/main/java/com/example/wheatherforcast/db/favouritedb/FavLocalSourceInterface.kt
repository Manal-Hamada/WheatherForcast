package com.example.wheatherforcast.db.favouritedb

import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.flow.Flow

interface FavLocalSourceInterface {

    suspend fun insertToFav(favLocation: FavModel)
    fun deleteFav(favLocation: FavModel)
    fun getAllFavs():  Flow<List<FavModel>?>?

}
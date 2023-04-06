package com.example.wheatherforcast.fakerepos

import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.favourites.view.FavouriteInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFavRepository():FavouriteInterface {

   private val mutableListOfFavourites= mutableListOf<FavModel>()

    override suspend fun insert(favLocation: FavModel) {
        mutableListOfFavourites.add(favLocation)
    }

    override suspend fun delete(favLocation: FavModel) {
        mutableListOfFavourites.remove(favLocation)
    }

    override suspend fun getAllFavs(): Flow<List<FavModel>?>? {
        return flowOf(mutableListOfFavourites)
    }


}
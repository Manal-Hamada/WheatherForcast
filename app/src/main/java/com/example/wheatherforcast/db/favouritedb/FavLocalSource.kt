package com.example.wheatherforcast.db.favouritedb

import android.content.Context
import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.flow.Flow

class FavLocalSource(var context: Context) : FavLocalSourceInterface {
    private fun getFavDao(): FavouriteDao? {
        val dao: FavouriteDao? by lazy {
            FavouriteDataBase.getInstance(context)?.favouriteDao()
        }
        return dao
    }

    private val favDao = getFavDao()


    override suspend fun insertToFav(favLocation: FavModel) {
        favDao?.insertLocation(favLocation)
    }

    override fun deleteFav(favLocation: FavModel) {
        favDao?.deleteLocation(favLocation)
    }

    override fun getAllFavs(): Flow<List<FavModel>?>? {
        return favDao?.getAllFavourites()
    }

}
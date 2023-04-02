package com.example.wheatherforcast.favourites.model

import android.content.Context
import com.example.wheatherforcast.db.FavouriteDao
import com.example.wheatherforcast.db.FavouriteDataBase
import com.example.wheatherforcast.favourites.view.FavouriteInterface


class FavouriteRepository : FavouriteInterface {


    companion object {
        @Volatile
        private var repo: FavouriteRepository? = null
        fun getInstance(): FavouriteRepository? {
            return repo ?: synchronized(this) {
                val temp = FavouriteRepository()
                repo = temp
                temp
            }
        }
    }

    fun getDao(context:Context): FavouriteDao? {
        val dao:  FavouriteDao? by lazy {
            FavouriteDataBase.getInstance(context)?.favouriteDao()
        }
        return dao
    }

    override suspend fun insert(favLocation: FavModel, context: Context) {
      getDao(context)?.insertLocation (favLocation)
    }

    override suspend fun delete(favLocation: FavModel, context: Context) {
        getDao(context)?.deleteLocation (favLocation)
    }


}
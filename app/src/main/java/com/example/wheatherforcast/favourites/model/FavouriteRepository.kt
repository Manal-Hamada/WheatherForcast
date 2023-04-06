package com.example.wheatherforcast.favourites.model

import com.example.wheatherforcast.db.favouritedb.FavLocalSource
import com.example.wheatherforcast.favourites.view.FavouriteInterface
import kotlinx.coroutines.flow.Flow


open class FavouriteRepository(var localSource: FavLocalSource) : FavouriteInterface {


    companion object {
        @Volatile
        private var repo: FavouriteRepository? = null
        fun getInstance(localSource: FavLocalSource): FavouriteRepository? {
            return repo ?: synchronized(this) {
                val temp = FavouriteRepository(localSource)
                repo = temp
                temp
            }
        }
    }



    override suspend fun insert(favLocation: FavModel) {
      localSource.insertToFav(favLocation)

    }

    override suspend fun delete(favLocation: FavModel) {
        localSource.deleteFav(favLocation)
    }

    override suspend fun getAllFavs():Flow<List<FavModel>?>? {
        return localSource.getAllFavs()
    }


}
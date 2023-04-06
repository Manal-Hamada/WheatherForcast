package com.example.wheatherforcast.fakerepos


import com.example.wheatherforcast.db.favouritedb.FavLocalSourceInterface
import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class FakeFavDataSourse(var favs:MutableList<FavModel> = mutableListOf(),
                     ) : FavLocalSourceInterface {
    override suspend fun insertToFav(favLocation: FavModel) {
        favs.add(favLocation)
    }

    override fun deleteFav(favLocation: FavModel) {
        favs.remove(favLocation)
    }

    override fun getAllFavs(): Flow<List<FavModel>?>? {
       return flowOf(favs)
    }


}
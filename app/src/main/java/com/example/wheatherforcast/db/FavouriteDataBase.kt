package com.example.wheatherforcast.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wheatherforcast.favourites.model.FavModel

@Database(entities = [FavModel::class], version = 2, exportSchema = false)
abstract class FavouriteDataBase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao?

    companion object {
        var favDataBase: FavouriteDataBase? = null
        fun getInstance(context: Context): FavouriteDataBase? {
            if (favDataBase == null) {
                favDataBase = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteDataBase::class.java, "favourites"
                ).build()
            }
            return favDataBase
        }
    }

}

package com.example.wheatherforcast.favourites.view

import android.content.Context
import com.example.wheatherforcast.favourites.model.FavModel

interface FavouriteInterface {

    suspend fun insert(favLocation: FavModel, context: Context)
    suspend fun delete(favLocation: FavModel, context: Context)
}
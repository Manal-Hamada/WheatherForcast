package com.example.wheatherforcast.favourites.view

import com.example.wheatherforcast.favourites.model.FavModel

interface onFavouriteClickListener {

    fun goToLcation(favLocation:FavModel)
    fun deletelocation(favLocation:FavModel)
}
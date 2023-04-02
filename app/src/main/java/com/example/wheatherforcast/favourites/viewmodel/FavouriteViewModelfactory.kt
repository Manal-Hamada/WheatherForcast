package com.example.wheatherforcast.favourites.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wheatherforcast.favourites.model.FavouriteRepository

class FavouriteViewModelfactory(private val _repo: FavouriteRepository?, var context: Context) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
           FavouriteViewModel(_repo, context) as T
        } else {
            throw java.lang.IllegalArgumentException("view model class not founded")
        }
    }
}
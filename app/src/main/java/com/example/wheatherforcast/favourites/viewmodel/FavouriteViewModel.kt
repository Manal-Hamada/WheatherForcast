package com.example.wheatherforcast.favourites.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.favourites.model.FavouriteRepository
import com.example.wheatherforcast.favourites.view.FavouriteInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavouriteViewModel (var repo: FavouriteInterface?) : ViewModel() {

      var _mutableList : Flow<List<FavModel>?>?=null

    init {
        _mutableList=getFavLocation()
    }

     fun getFavLocation(): Flow<List<FavModel>?>? {
        var list: Flow<List<FavModel>?>? = null
        var job = viewModelScope.launch(Dispatchers.IO) {
            list=repo?.getAllFavs()
            _mutableList=list

        }
      // job.join()

      return list
    }

    fun insertnewFavourite(favLocation: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repo?.insert(favLocation)
        }

    }

     suspend fun deleteLocation(favLocation: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            //repo?.delete(favLocation, context)
            repo?.delete(favLocation)
        }
        getFavLocation()
    }
}
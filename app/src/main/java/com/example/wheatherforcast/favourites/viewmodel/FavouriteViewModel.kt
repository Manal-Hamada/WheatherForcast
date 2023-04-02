package com.example.wheatherforcast.favourites.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.favourites.model.FavouriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavouriteViewModel constructor(var repo: FavouriteRepository?, var _context: Context) : ViewModel() {

    private var _mutableList = MutableLiveData<List<FavModel>>()
    var statList = _mutableList

    init {
        // getFavLocation(_context)
    }

    suspend fun getFavLocation(context: Context): Flow<List<FavModel>?>? {

        /* var job = viewModelScope.launch(Dispatchers.IO) {
            repo!!.getDao(context)?.getAllFavourites()
                ?.collect { list ->
                    _mutableList.value = list
                }

        }*/
        var list: Flow<List<FavModel>?>? = null
        var job = viewModelScope.launch(Dispatchers.IO) {
            list = repo!!.getDao(context)?.getAllFavourites()
        }
        job.join()
        return list
    }

    suspend fun insertnewFavourite(favLocation: FavModel, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            repo?.getDao(context)?.insertLocation(favLocation)
        }

    }

    suspend fun deleteLocation(favLocation: FavModel, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            repo?.delete(favLocation, context)
        }
        getFavLocation(context)
    }
}
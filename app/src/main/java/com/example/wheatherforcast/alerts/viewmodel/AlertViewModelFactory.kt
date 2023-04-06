package com.example.wheatherforcast.alerts.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wheatherforcast.alerts.model.AlertRepository
import com.example.wheatherforcast.favourites.model.FavouriteRepository
import com.example.wheatherforcast.favourites.viewmodel.FavouriteViewModel

class AlertViewModelFactory(private val _repo: AlertRepository?) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlertViewModel::class.java)) {
            AlertViewModel(_repo) as T
        } else {
            throw java.lang.IllegalArgumentException("view model class not founded")
        }
    }
}


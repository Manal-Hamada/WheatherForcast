package com.example.wheatherforcast.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wheatherforcast.home.model.Repository

class ViewModelFactory(
    val _repo: Repository?,
    var context: Context,
    var lat: String?,
    var lon: String?,
    var lang: String?,
    var units: String?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(_repo, context, lat, lon, lang,units) as T
        } else {
            throw java.lang.IllegalArgumentException("view model class not founded")
        }
    }
}
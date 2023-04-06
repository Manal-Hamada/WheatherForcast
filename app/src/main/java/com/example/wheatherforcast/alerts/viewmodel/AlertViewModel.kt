package com.example.wheatherforcast.alerts.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherforcast.alerts.model.AlertModel
import com.example.wheatherforcast.alerts.model.AlertRepository
import com.example.wheatherforcast.alerts.model.AlertsInterface
import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AlertViewModel(var repo: AlertsInterface?) : ViewModel() {

    var _mutableList : Flow<List<AlertModel>?>?=null

    init {
     getAlerts()
    }

     fun getAlerts(){

        var list: Flow<List<AlertModel>?>? = null
        var job = viewModelScope.launch(Dispatchers.IO) {
            list = repo!!.getAllAlerts()
             _mutableList=list
        }
       // job.join()
       // return list
    }

    suspend fun insertNewAlert(alert: AlertModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repo?.insertAlert(alert)
        }

    }

    suspend fun deleteAlert(alert: AlertModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repo?.deleteAlert(alert)
        }
        getAlerts()
    }

}

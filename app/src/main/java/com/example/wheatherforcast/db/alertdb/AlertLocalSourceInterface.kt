package com.example.wheatherforcast.db.alertdb

import com.example.wheatherforcast.alerts.model.AlertModel
import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.flow.Flow

interface AlertLocalSourceInterface {

    suspend fun insertAlert(alert: AlertModel)
    fun deleteAlert(alert: AlertModel)
    fun getAllAlerts():  Flow<List<AlertModel>?>?
}
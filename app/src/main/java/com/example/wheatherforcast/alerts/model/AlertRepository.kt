package com.example.wheatherforcast.alerts.model

import com.example.wheatherforcast.db.alertdb.AlertLocalSource
import kotlinx.coroutines.flow.Flow

class AlertRepository (var localsource: AlertLocalSource)  :AlertsInterface {

    companion object {
        @Volatile
        private var repo: AlertRepository? = null
        fun getInstance(localsource: AlertLocalSource): AlertRepository? {
            return repo ?: synchronized(this) {
                val temp = AlertRepository(localsource)
                repo = temp
                temp
            }
        }
    }



     override suspend fun insertAlert(alert: AlertModel) {
         localsource.insertAlert(alert)
     }

     override suspend fun deleteAlert(alert: AlertModel) {
         localsource.deleteAlert(alert)
     }

     override suspend fun getAllAlerts(): Flow<List<AlertModel>?>? {
        return localsource.getAllAlerts()
     }


 }
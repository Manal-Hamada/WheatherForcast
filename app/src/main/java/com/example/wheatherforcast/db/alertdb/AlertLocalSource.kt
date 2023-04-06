package com.example.wheatherforcast.db.alertdb

import android.content.Context
import com.example.wheatherforcast.alerts.model.AlertModel
import kotlinx.coroutines.flow.Flow

class AlertLocalSource (var context:Context):AlertLocalSourceInterface{

    fun getAlertDao(): AlertsDao? {
        val dao:  AlertsDao? by lazy {
            AlertsDataBase.getInstance(context)?.alertDao()
        }
        return dao
    }
    private val alertDao=getAlertDao()

    override suspend fun insertAlert(alert: AlertModel) {
        alertDao?.insertAlert(alert)
    }

    override fun deleteAlert(alert: AlertModel) {
      alertDao?.deleteAlert(alert)
    }

    override fun getAllAlerts(): Flow<List<AlertModel>?>? {
        return alertDao?.getAllAlerts()
    }


}
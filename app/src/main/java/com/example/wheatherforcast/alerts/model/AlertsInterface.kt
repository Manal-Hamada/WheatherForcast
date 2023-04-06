package com.example.wheatherforcast.alerts.model

import android.content.Context
import kotlinx.coroutines.flow.Flow


interface AlertsInterface {
    suspend fun insertAlert(alert: AlertModel)
    suspend fun deleteAlert(alert:AlertModel )
    suspend fun getAllAlerts(): Flow<List<AlertModel>?>?
}

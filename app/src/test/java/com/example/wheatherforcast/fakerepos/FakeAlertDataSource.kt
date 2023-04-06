package com.example.wheatherforcast.fakerepos

import com.example.wheatherforcast.alerts.model.AlertModel
import com.example.wheatherforcast.alerts.model.AlertsInterface
import com.example.wheatherforcast.db.alertdb.AlertLocalSource
import com.example.wheatherforcast.db.alertdb.AlertLocalSourceInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeAlertDataSource(var alerts:MutableList<AlertModel> = mutableListOf()):AlertLocalSourceInterface {

    override suspend fun insertAlert(alert: AlertModel) {
        alerts.add(alert)
    }

    override fun deleteAlert(alert: AlertModel) {
       alerts.remove(alert)
    }

    override fun getAllAlerts(): Flow<List<AlertModel>?>? {
        return flowOf(alerts)
    }


}
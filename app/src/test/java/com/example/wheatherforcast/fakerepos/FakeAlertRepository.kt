package com.example.wheatherforcast.fakerepos

import com.example.wheatherforcast.alerts.model.AlertModel
import com.example.wheatherforcast.alerts.model.AlertRepository
import com.example.wheatherforcast.alerts.model.AlertsInterface
import com.example.wheatherforcast.favourites.model.FavModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeAlertRepository:AlertsInterface {

    private val alerts= mutableListOf<AlertModel>()

    override suspend fun insertAlert(alert: AlertModel) {
        alerts.add(alert)
    }

    override suspend fun deleteAlert(alert: AlertModel) {
        alerts.remove(alert)
    }

    override suspend fun getAllAlerts(): Flow<List<AlertModel>?>? {
        return flowOf(alerts)
    }
}
package com.example.wheatherforcast.alerts.view

import com.example.wheatherforcast.alerts.model.AlertModel

interface onAlertClickListener {
    fun deleteAlert(alert: AlertModel)
}
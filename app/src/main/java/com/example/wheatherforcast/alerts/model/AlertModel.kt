package com.example.wheatherforcast.alerts.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class AlertModel (
    @PrimaryKey var id:String,
    var timeStart:String,
    val timeEnd:String,
    val dateStart:String,
    val dateEnd:String) {
}


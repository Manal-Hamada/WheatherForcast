package com.example.wheatherforcast.db.alertdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wheatherforcast.alerts.model.AlertModel


@Database(entities = [AlertModel::class], version = 1, exportSchema = false)
abstract class AlertsDataBase: RoomDatabase() {
    abstract fun alertDao(): AlertsDao?

    companion object {
        var alertDataBase: AlertsDataBase? = null
        fun getInstance(context: Context): AlertsDataBase? {
            if (alertDataBase == null) {
                alertDataBase = Room.databaseBuilder(
                    context.applicationContext,
                    AlertsDataBase::class.java, "alerts"
                ).build()

            }
            return alertDataBase
        }
    }

}
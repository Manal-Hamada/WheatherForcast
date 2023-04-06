package com.example.wheatherforcast.db.alertdb

import androidx.room.*
import com.example.wheatherforcast.alerts.model.AlertModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: AlertModel?)

    @Delete
    fun deleteAlert(alert: AlertModel?)

    @Query("SELECT * FROM alerts")
    fun getAllAlerts(): Flow<List<AlertModel>?>?
}
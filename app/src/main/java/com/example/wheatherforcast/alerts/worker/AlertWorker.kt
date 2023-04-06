package com.example.wheatherforcast.alerts.worker

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.wheatherforcast.alerts.view.AlertDialogActivity
import com.example.wheatherforcast.home.model.Repository
import com.example.wheatherforcast.home.model.WeatherResponse
import com.example.wheatherforcast.home.viewmodel.HomeViewModel
import com.example.wheatherforcast.home.viewmodel.ViewModelFactory
import com.example.wheatherforcast.utils.Constants
import com.example.wheatherforcast.utils.ReaadableTemp
import kotlinx.coroutines.*
import java.util.*


class AlertWorker(val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    var  sharedPreference =context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
    var approximateTemp= ReaadableTemp()

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doWork(): Result {

        Log.i("input data",inputData.getString("typeAlert").toString() )

        if (inputData.getString("typeAlert").toString().equals("Notification")) {

            var city= sharedPreference.getString(Constants.cityName,"").toString()

            var contentNotification=(Constants.response?.current?.weather?.get(0)?.description)+"  "+approximateTemp(
                (Constants.response?.current!!.temp)!!.minus(273.15))+"\u00B0"

            NotificationHelper(context).createNotification(
                title = city,
               content = contentNotification,
            )
            Log.i("fire noti", "notti ")
        } else
            fireAlarmDialog()


        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fireAlarmDialog() {
        if (Settings.canDrawOverlays(context)) {
            val intent = Intent(context, AlertDialogActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_INCLUDE_STOPPED_PACKAGES or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)

        }
    }

}
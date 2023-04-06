package com.example.wheatherforcast.db.homedb

import com.example.wheatherforcast.home.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface RemoteSourceInterface {

    suspend fun RemoteData(lat:String?, lon:String?, lang:String?, units:String?): Flow<WeatherResponse>
}
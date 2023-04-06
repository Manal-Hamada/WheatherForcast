package com.example.wheatherforcast.home.model

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface  {

 suspend fun getRemoteData(lat:String?,lon:String?,lang:String?,units:String?):Flow<WeatherResponse>
 suspend fun insertResponse(response:WeatherResponse)
 suspend fun getLocalDta():Flow<WeatherResponse>?
 suspend fun clearTable()


}
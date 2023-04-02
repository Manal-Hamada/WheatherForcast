package com.example.wheatherforcast.home.model

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {

 suspend fun getRemoteData( c:Context,lat:String?,lan:String?,lang:String?,units:String?):Flow<WeatherResponse>

}
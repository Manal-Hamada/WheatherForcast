package com.example.wheatherforcast.db.homedb

import android.content.Context
import com.example.wheatherforcast.home.model.WeatherResponse
import com.example.wheatherforcast.network.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RemoteSource() : RemoteSourceInterface {

    suspend fun getRemoteData( lat:String?, lon:String?, lang:String?, units:String?): Flow<WeatherResponse> {
        var weatherResponse = ApiClient.getInstance().getResponse(lat=lat, lon=lon,lang=lang,units=units)
        return flowOf(weatherResponse)
    }

    override suspend fun RemoteData(
        lat: String?,
        lon: String?,
        lang: String?,
        units: String?
    ): Flow<WeatherResponse> {
        return getRemoteData(lat,lon,lang,units)
    }

}
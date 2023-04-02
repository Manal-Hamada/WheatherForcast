package com.example.wheatherforcast.home.model

import android.content.Context
import com.example.wheatherforcast.network.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository : RepositoryInterface {


    companion object {
        @Volatile
        private var repo: Repository? = null
        fun getInstance(): Repository? {
            return repo ?: synchronized(this) {
                val temp = Repository()
                repo = temp
                temp
            }
        }
    }

    override suspend fun getRemoteData(c: Context,lat:String?,lon:String?,lang:String?,units:String?): Flow<WeatherResponse> {
        var weatherResponse = ApiClient.getInstance().getResponse(lat=lat, lon=lon,lang=lang,units=units)
        return flowOf(weatherResponse)
    }


}
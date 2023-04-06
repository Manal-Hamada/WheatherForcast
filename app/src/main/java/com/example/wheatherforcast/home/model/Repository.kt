package com.example.wheatherforcast.home.model

import android.content.Context
import com.example.wheatherforcast.db.homedb.HomeDao
import com.example.wheatherforcast.db.homedb.HomeDatabase
import com.example.wheatherforcast.db.homedb.HomeLocalSource
import com.example.wheatherforcast.db.homedb.RemoteSource


import com.example.wheatherforcast.network.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class Repository(var localdata: HomeLocalSource, var remotedata: RemoteSource) : RepositoryInterface {


    companion object {
        @Volatile
        private var repo: Repository? = null
        fun getInstance(localdata: HomeLocalSource, remotedata: RemoteSource): Repository? {
            return repo ?: synchronized(this) {
                val temp = Repository(localdata,remotedata)
                repo = temp
                temp
            }
        }
    }


    override suspend fun getRemoteData(lat:String?,lon:String?,lang:String?,units:String?): Flow<WeatherResponse> {
       return remotedata.getRemoteData(lat,lon,lang,units)
    }

    override suspend fun insertResponse(response: WeatherResponse) {
        localdata.insertWeatherResponse(response)
    }

    override suspend fun getLocalDta(): Flow<WeatherResponse>? {
      return  localdata.getWeatherResponse()
    }

    override suspend fun clearTable() {
        localdata.clear()
    }


}
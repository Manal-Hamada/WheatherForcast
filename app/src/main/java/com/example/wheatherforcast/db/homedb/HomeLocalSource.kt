package com.example.wheatherforcast.db.homedb

import android.content.Context
import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.home.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

class HomeLocalSource(var context: Context): HomeLocalSourceInterface {

    private fun getHomeDao(): HomeDao? {
        val dao: HomeDao? by lazy {
            HomeDatabase.getInstance(context)?.homeDao()
        }
        return dao
    }

    private val homeDao = getHomeDao()

    override fun getWeatherResponse(): Flow<WeatherResponse>? {
       return homeDao?.getWeatherResponse()
    }

    override suspend fun insertWeatherResponse(response:WeatherResponse) {
        homeDao?.insertWeatherResponse(response)
    }

    override suspend fun clear() {
        HomeDatabase.getInstance(context)?.clearAllTables()
    }
}
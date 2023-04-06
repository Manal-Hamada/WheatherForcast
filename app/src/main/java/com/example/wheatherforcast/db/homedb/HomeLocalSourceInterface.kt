package com.example.wheatherforcast.db.homedb

import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.home.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface HomeLocalSourceInterface {

    fun getWeatherResponse(): Flow<WeatherResponse>?
   suspend fun insertWeatherResponse(response:WeatherResponse)
    suspend fun clear()

}
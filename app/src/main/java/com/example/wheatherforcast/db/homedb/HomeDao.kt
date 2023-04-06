package com.example.wheatherforcast.db.homedb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.home.model.WeatherResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface HomeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherResponse(response: WeatherResponse?)

    @Query("SELECT * FROM home")
    fun getWeatherResponse(): Flow<WeatherResponse>
}
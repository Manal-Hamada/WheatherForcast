package com.example.wheatherforcast.db.homedb

import android.content.Context
import androidx.room.*
import com.example.wheatherforcast.home.model.WeatherResponse
import com.example.wheatherforcast.utils.WeatherConverters

@Database(entities = [WeatherResponse::class], version = 4, exportSchema = false)
@TypeConverters(WeatherConverters::class)
abstract class HomeDatabase :RoomDatabase(){
    abstract fun homeDao(): HomeDao?

    companion object {
        var homeDataBase: HomeDatabase? = null
        fun getInstance(context: Context): HomeDatabase? {
            if (homeDataBase == null) {
                homeDataBase = Room.databaseBuilder(
                    context.applicationContext,
                    HomeDatabase::class.java, "home"
                ).build()
            }
            return homeDataBase
        }
    }

}
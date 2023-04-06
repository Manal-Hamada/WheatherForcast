package com.example.wheatherforcast.utils

import androidx.room.TypeConverter
import com.example.wheatherforcast.home.model.Alert
import com.example.wheatherforcast.home.model.Current
import com.example.wheatherforcast.home.model.Daily
import com.example.wheatherforcast.home.model.Hourly
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class WeatherConverters {
    @TypeConverter
    fun fromString(value:List< Alert?>? ):String? {
        val gson = Gson()
        return gson.toJson(value)
    }


    @TypeConverter
    fun toString(someObject: String?):List< Alert?>?  {
        val gson = Gson()
        val listType: Type = object : TypeToken<List<Alert?>?>() {}.type
        return gson.fromJson(someObject, listType)
    }
    @TypeConverter
    fun fromCurrent(current: Current?): String {
        val gson = Gson()
        return gson.toJson(current)
    }

    @TypeConverter
    fun toCurrent(json: String?): Current {
        val gson = Gson()
        return gson.fromJson(json, Current::class.java)
    }



    @TypeConverter
    fun fromDaily(dailyList: List<Daily?>?): String? {
        val gson = Gson()
        return gson.toJson(dailyList)
    }

    @TypeConverter
    fun ToDailyList(data: String?): List<Daily?>? {
        val gson = Gson()
        val listType: Type = object : TypeToken<List<Daily?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun hourlyListToString(hourlyList: List<Hourly?>?): String? {
        val gson = Gson()
        return gson.toJson(hourlyList)
    }

    @TypeConverter
    fun stringToHourlyList(data: String?): List<Hourly?>? {
        val gson = Gson()
        val listType = object : TypeToken<List<Hourly?>?>() {}.type
        return gson.fromJson(data, listType)
    }
}
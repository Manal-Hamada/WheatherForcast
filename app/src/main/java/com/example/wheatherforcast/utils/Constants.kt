package com.example.wheatherforcast.utils

import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.home.model.Alert
import com.example.wheatherforcast.home.model.WeatherResponse

class Constants {

    companion object {
        var API_KEY = "f2f9ec409c67b8498f33c2bf4c7fb7e7"
        var favModel=false
        var model:FavModel=FavModel("","","","")
        var fromfav=false
        var mapChange=false
        var response:WeatherResponse?=null
        var latitude="latitude"
        var longitude="longitude"
        var language="language"
        var cityName="cityName"
        var checkedTempUnit="checkedTempUnit"
        var checkedLang="checkedLang"
        val Celsius="Celsius"
        val Fahrenheit="Fahrenheit"
        val Kelvin="Kelvin"
    }
}
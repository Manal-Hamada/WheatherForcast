package com.example.wheatherforcast.utils

import com.example.wheatherforcast.favourites.model.FavModel

class Constants {

    companion object {
        var API_KEY = "f2f9ec409c67b8498f33c2bf4c7fb7e7"
        var favModel=false
        var model:FavModel=FavModel("","","","")
        var favCity=""
        var latitude=""
        var longitude=""
        var language=""
        var arabic="ar"
        var english="en"
        val Temperature="Temperature"
        val Celsius="Celsius"
        val Fahrenheit="Fahrenheit"
        val Kelvin="Kelvin"
    }
}
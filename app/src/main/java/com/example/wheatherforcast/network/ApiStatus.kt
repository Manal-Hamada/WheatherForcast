package com.example.wheatherforcast.network

import com.example.wheatherforcast.home.model.WeatherResponse

sealed class ApiStatus {
    class Success(val weatherResponse: WeatherResponse) : ApiStatus()
    object Loading : ApiStatus()
    class Failed(val msg:Throwable) : ApiStatus()

}

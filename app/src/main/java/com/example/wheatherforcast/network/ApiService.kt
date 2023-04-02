package com.example.wheatherforcast.network



import com.example.wheatherforcast.home.model.WeatherResponse
import com.example.wheatherforcast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

  @GET("onecall")
  suspend fun getResponse(@Query("lat") lat: String?  ,
                          @Query("lon") lon: String? ,
                          @Query("lang") lang: String? ,
                          @Query("exclude") exclude: String?="minutely",
                          @Query("units") units: String?,
                          @Query("appid") appid: String?=Constants.API_KEY): WeatherResponse

}
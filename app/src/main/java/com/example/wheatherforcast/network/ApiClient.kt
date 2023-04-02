package com.example.wheatherforcast.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    /*val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val TAG = "API_Client"
    var api_client: ApiClient? = null
    var gson = GsonBuilder().create()
    var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build().create(ApiService::class.java)
    //  var api_service= retrofit.create(API_Service::class.java)*/
    private val Base_URL = "https://api.openweathermap.org/data/2.5/"
    fun getInstance(): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(Base_URL)
            .build().create(ApiService::class.java)
      }

}
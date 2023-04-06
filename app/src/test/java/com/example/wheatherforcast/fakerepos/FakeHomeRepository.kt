package com.example.wheatherforcast.fakerepos

import com.example.wheatherforcast.home.model.RepositoryInterface
import com.example.wheatherforcast.home.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

class FakeHomeRepository:RepositoryInterface {


    val response =WeatherResponse(null,null,null,null,1223456.0,123456.0,"",12)
    val list= listOf<WeatherResponse>(response)
    override suspend fun getRemoteData(
        lat: String?,
        lon: String?,
        lang: String?,
        units: String?
    ): Flow<WeatherResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun insertResponse(response: WeatherResponse) {
        TODO("Not yet implemented")
    }

    override suspend fun getLocalDta(): Flow<WeatherResponse>? {
        TODO("Not yet implemented")
    }
}
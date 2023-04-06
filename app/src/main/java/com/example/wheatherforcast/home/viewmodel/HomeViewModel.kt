package com.example.wheatherforcast.home.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherforcast.network.ApiStatus
import com.example.wheatherforcast.home.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel constructor(
    var repo: RepositoryInterface?,

) :ViewModel() {

    private val _mutablestateList = MutableStateFlow<ApiStatus>(ApiStatus.Loading)
    var statList = _mutablestateList.asStateFlow()

    init {
        // getWeatherResponse(context, lat, lon, lang, units)

    }

    fun getWeatherResponse( lat: String?, lon: String?, lang: String?, units: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repo?.getRemoteData( lat, lon, lang, units)?.catch { e ->
                _mutablestateList.value = ApiStatus.Failed(e)
            }?.collect { e ->
                _mutablestateList.value = ApiStatus.Success(e)
                repo!!.clearTable()
                repo!!.insertResponse(e)

            }
        }

    }

     fun getSavedWeather() {

        viewModelScope.launch(Dispatchers.IO) {
            repo?.getLocalDta()?.catch { e ->
                _mutablestateList.value = ApiStatus.Failed(e)
            }?.collect { e ->
                _mutablestateList.value = ApiStatus.Success(e)
            }

        }
    }
}









package com.example.wheatherforcast.home.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.wheatherforcast.network.ApiStatus
import com.example.wheatherforcast.home.model.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel constructor(
    var repo: Repository?,
    var _context: Context,
    lat: String?,
    lon: String?,
    lang: String?,
    units: String?
) : BaseViewModel(repo, _context) {

    private val _mutablestateList = MutableStateFlow<ApiStatus>(ApiStatus.Loading)
    var statList = _mutablestateList.asStateFlow()

    init {
        getWeatherResponse(context, lat, lon, lang, units)
    }

    fun getWeatherResponse(c: Context, lat: String?, lon: String?, lang: String?, units: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repo?.getRemoteData(c, lat, lon, lang, units)?.catch { e ->
                _mutablestateList.value = ApiStatus.Failed(e)
            }?.collect { e ->
                _mutablestateList.value = ApiStatus.Success(e)
            }
        }

    }
}


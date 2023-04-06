package com.example.wheatherforcast.utils

import android.content.Context
import android.location.Geocoder
import androidx.core.content.ContentProviderCompat.requireContext
import java.util.*

class CityName {

    companion object{
       fun getCityName(lat: Double,long: Double,context: Context): String? {
            //val cityName: String?
            val geoCoder = Geocoder(context, Locale.getDefault())
            val Adress = geoCoder.getFromLocation(lat,long,3)

           // cityName = Adress?.get(0)?.countryName
            //city=cityName
            return Adress?.get(0)?.adminArea
        }
    }
}
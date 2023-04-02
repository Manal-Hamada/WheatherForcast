package com.example.wheatherforcast.utils

import android.location.Address

interface CurrentLocationStatue {
    fun success(list :List<Address>?)

}